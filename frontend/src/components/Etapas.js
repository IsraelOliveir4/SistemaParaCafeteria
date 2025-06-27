import React from 'react';
import icon1 from '../assets/IMG/cocktail_882775.png';
import icon2 from '../assets/IMG/cup_14695078.png';
import icon3 from '../assets/IMG/tropical-drink_17076771.png';

export default function Etapas() {
  const etapas = [
    { img: icon1, titulo: 'Passo a passo para cria sua bebida perfeita', texto: 'Escolha seus ingredientes, identifique seu sabor e veja o resumo da sua bebida.' },
    { img: icon2, titulo: 'Como funciona a identificação do sabor clássico?', texto: 'Veja se sua combinação é um sabor clássico.' },
    { img: icon3, titulo: 'Resumo da sua bebida personalizada', texto: 'Veja o nome da sua bebida e todos os ingredientes escolhidos.' },
  ];

  return (
    <div style={{ marginTop: 20 }}>
      <p style={{ fontStyle: 'italic', color: '#444' }}>
        O café do seu jeito!
      </p>
      <h1>Monte seu Café</h1>
      <p>
        Descubra como criar a bebida perfeita para você! Escolha seus ingredientes e personalize seu café de forma simples e divertida
      </p>
      <p style={{ marginBottom: 30 }}>
        Descubra como personalizar seu café de forma simples e rápida
      </p>

      <div style={{ display: 'flex', justifyContent: 'center', gap: 20 }}>
        {etapas.map((etapa, index) => (
          <div
            key={index}
            style={{
              flex: '1 1 0',
              background: '#f9f9f9',
              padding: 20,
              borderRadius: 8,
              textAlign: 'center',
              boxShadow: '0 0 5px rgba(0,0,0,0.1)',
            }}
          >
            <img
              src={etapa.img}
              alt={`Ícone ${index + 1}`}
                style={{
                    width: 50,
                    height: 50,
                    objectFit: 'contain',
                    marginBottom: 10,
                    display: 'block',
                    marginLeft: 'auto',
                    marginRight: 'auto'
                }}
            />
            <strong>{etapa.titulo}</strong>
            <p>{etapa.texto}</p>
            <button
            style={{
                    marginTop: 10,
                    padding: '10px 20px',
                    backgroundColor: '#6f4e37', 
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
                    cursor: 'pointer',
                    fontWeight: 'bold',
                    transition: 'background 0.3s',
            }}
            >Confirmar &gt;</button>
          </div>
        ))}
      </div>
    </div>
  );
}
