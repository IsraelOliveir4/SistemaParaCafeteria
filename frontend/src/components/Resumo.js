import React from 'react';

export default function Resumo({
  ingredientesBase,
  ingredientesExtras,
  saborClassico,
  precoFinal,
  baseConfirmada,
  limparTudo,
  confirmarPedido,
  ingredientesDisponiveis,
}) {
  console.log('Resumo saborClassico:', saborClassico);
  return (
    <>
      <div style={{ display: 'flex', marginTop: 70, gap: 30 }}>
        <div style={{ flex: 1 }}>
          <h2>Identificação do sabor Clássico</h2>
          <p>Verifique se sua combinação forma um sabor clássico.</p>
        </div>
        <div style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: 20 }}>
          <div style={{ border: '1px solid #ccc', padding: 15, borderRadius: 8 }}>
            <div style={{ fontWeight: 'bold', fontSize: 18, marginBottom: 10 }}>
              Café Identificado: {saborClassico ? saborClassico.nome : 'Personalizado'}
            </div>
            <strong>Ingredientes Base:</strong>
            <p>
              {ingredientesBase
                .map((id) =>
                  ingredientesDisponiveis.find((i) => Number(i.id) === Number(id))?.nome
                )
                .filter(Boolean)
                .join(', ') || 'Nenhum'}
            </p>
          </div>
          <div style={{ border: '1px solid #ccc', padding: 15, borderRadius: 8 }}>
            <strong>Extras & Preço:</strong>
            <p>
              {ingredientesExtras
                .map((id) =>
                  ingredientesDisponiveis.find((i) => Number(i.id) === Number(id))?.nome
                )
                .filter(Boolean)
                .join(', ') || 'Nenhum'}
            </p>
            <p>Preço: R$ {precoFinal.toFixed(2)}</p>
          </div>
        </div>
      </div>

      <div style={{ display: 'flex', justifyContent: 'center', gap: 20, marginTop: 40 }}>
        <button
          onClick={limparTudo}
          style={{
            padding: '10px 25px',
            borderRadius: 8,
            backgroundColor: '#dc3545',
            color: 'white',
            border: 'none',
            fontSize: 16,
            cursor: 'pointer',
          }}
        >
          Limpar Tudo
        </button>
        <button
          disabled={!baseConfirmada}
          style={{
            padding: '10px 25px',
            borderRadius: 8,
            backgroundColor: baseConfirmada ? '#28a745' : '#aaa',
            color: 'white',
            border: 'none',
            fontSize: 16,
            cursor: baseConfirmada ? 'pointer' : 'not-allowed',
          }}
          onClick={confirmarPedido}
        >
          Confirmar Pedido
        </button>
      </div>
    </>
  );
}
