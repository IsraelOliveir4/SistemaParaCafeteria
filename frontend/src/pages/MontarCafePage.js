import React, { useState, useEffect } from 'react';
import Etapas from '../components/Etapas';
import Ingredientes from '../components/Ingredientes';
import Resumo from '../components/Resumo';
import Footer from '../components/Footer';
import Toast from '../components/Toast'; 

export default function MontarCafe() {
  const [ingredientesDisponiveis, setIngredientesDisponiveis] = useState([]);
  const [ingredientesBase, setIngredientesBase] = useState([]);
  const [ingredientesExtras, setIngredientesExtras] = useState([]);
  const [saborClassico, setSaborClassico] = useState(null);
  const [precoFinal, setPrecoFinal] = useState(0);
  const [baseConfirmada, setBaseConfirmada] = useState(false);
  const [toastMsg, setToastMsg] = useState('');
  const [toastTipo, setToastTipo] = useState('sucesso');

  useEffect(() => {
    fetch('http://localhost:8080/ingredientes')
      .then(res => res.json())
      .then(data => setIngredientesDisponiveis(data))
      .catch(err => console.error('Erro ao buscar ingredientes:', err));
  }, []);

  useEffect(() => {
    if (baseConfirmada && ingredientesBase.length > 0) {
      fetch('http://localhost:8080/sabores/detectar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(ingredientesBase),
      })
        .then((res) => res.ok ? res.json() : null)
        .then((data) => {
          setSaborClassico(data || null)
        })
        .catch((err) => {
          console.error('Erro ao detectar sabor clássico:', err);
          setSaborClassico(null);
        });
    }
  }, [baseConfirmada, ingredientesBase]);

  useEffect(() => {
    if (!baseConfirmada) {
      setPrecoFinal(0);
      setIngredientesExtras([]);
      setSaborClassico(null);
      return;
    }

    const basePreco = ingredientesBase.reduce((acc, id) => {
      const ing = ingredientesDisponiveis.find(i => i.id === id);
      return acc + (ing?.preco || 0);
    }, 0);

    const extrasPreco = ingredientesExtras.reduce((acc, id) => {
      const ing = ingredientesDisponiveis.find(i => i.id === id);
      return acc + (ing?.preco || 0);
    }, 0);

    setPrecoFinal(saborClassico ? saborClassico.preco + extrasPreco : basePreco + extrasPreco);
  }, [baseConfirmada, ingredientesBase, ingredientesExtras, saborClassico, ingredientesDisponiveis]);

  const mostrarToast = (mensagem, tipo = 'sucesso') => {
    setToastMsg(mensagem);
    setToastTipo(tipo);
    };
  const confirmarPedido = async () => {
    const pedido = {
    ingredientesBaseIds: ingredientesBase, 
    ingredientesExtrasIds: ingredientesExtras, 
    saborClassicoId: saborClassico ? saborClassico.id : null, 
    preco: precoFinal,
    };

    try {
      const res = await fetch('http://localhost:8080/pedidos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedido),
      });

      if (res.ok) {
        mostrarToast('Pedido realizado com sucesso!', 'sucesso');
        limparTudo();
      } else {
      // Tenta ler o erro retornado pelo backend
      const errorData = await res.json();
      // Supondo que o backend retorne mensagem em errorData.message
      const errorMessage = errorData.message || errorData.error || 'Erro desconhecido ao enviar pedido';
        mostrarToast(`Erro ao enviar pedido: ${errorMessage}`, 'erro');
      }
    } catch (err) {
      console.error(err);
      mostrarToast('Erro ao comunicar com o servidor.', 'erro');
    }
  };

  const limparTudo = () => {
    setIngredientesBase([]);
    setIngredientesExtras([]);
    setSaborClassico(null);
    setPrecoFinal(0);
    setBaseConfirmada(false);
  };

  return (
    <div
      style={{
        padding: 20,
        fontFamily: 'Arial, sans-serif',
        maxWidth: 900,
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        margin: '0 auto',
      }}
    >
      {toastMsg && (
        <Toast mensagem={toastMsg} tipo={toastTipo} onClose={() => setToastMsg('')} />
      )}
      <Etapas />
      {ingredientesDisponiveis.length === 0 ? (
        <p>Carregando ingredientes...</p>
      ) : (
        <Ingredientes
          ingredientesBase={ingredientesBase}
          setIngredientesBase={setIngredientesBase}
          ingredientesExtras={ingredientesExtras}
          setIngredientesExtras={setIngredientesExtras}
          baseConfirmada={baseConfirmada}
          setBaseConfirmada={setBaseConfirmada}
          ingredientesDisponiveis={ingredientesDisponiveis}
        />
      )}
      <Resumo
        ingredientesBase={ingredientesBase}
        ingredientesExtras={ingredientesExtras}
        saborClassico={saborClassico}
        precoFinal={precoFinal}
        baseConfirmada={baseConfirmada}
        confirmarPedido={confirmarPedido}
        limparTudo={limparTudo}
        ingredientesDisponiveis={ingredientesDisponiveis}
      />
      <Footer />
    </div>
  );
}
