import React from 'react';

export default function Ingredientes({
  ingredientesBase,
  setIngredientesBase,
  ingredientesExtras,
  setIngredientesExtras,
  baseConfirmada,
  setBaseConfirmada,
  ingredientesDisponiveis,
}) {
  const ingredientesBaseDisponiveis = ingredientesDisponiveis.filter(i => i.tipo === 'base');
  const ingredientesExtrasDisponiveis = ingredientesDisponiveis.filter(i => i.tipo === 'extra');

  function toggleBase(id) {
    if (baseConfirmada) return;
    setIngredientesBase((old) =>
      old.includes(id) ? old.filter((i) => i !== id) : [...old, id]
    );
  }

  function toggleExtra(id) {
    if (!baseConfirmada) return;
    setIngredientesExtras((old) => {
      if (old.includes(id)) {
        return old.filter((i) => i !== id);
      } else if (old.length < 2) {
        return [...old, id];
      }
      return old;
    });
  }

  function confirmarBase() {
    if (ingredientesBase.length > 0) {
      setBaseConfirmada(true);
    } else {
      alert('Selecione ao menos um ingrediente base antes de confirmar.');
    }
  }

  const botaoEstilo = (selecionado, disabled = false) => ({
    padding: '10px 15px',
    margin: '5px',
    borderRadius: 8,
    border: selecionado ? '2px solid #007bff' : '2px solid #ccc',
    backgroundColor: selecionado ? '#007bff' : 'white',
    color: selecionado ? 'white' : 'black',
    cursor: disabled ? 'not-allowed' : 'pointer',
    userSelect: 'none',
    opacity: disabled ? 0.5 : 1,
  });

  return (
    <div style={{ display: 'flex', gap: 20, marginTop: 40 }}>
      <div style={{ flex: 1 }}>
        <h2>Ingredientes Base</h2>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: 10 }}>
          {ingredientesBaseDisponiveis.map(({ id, nome }) => (
            <button
              key={id}
              style={botaoEstilo(ingredientesBase.includes(id), baseConfirmada)}
              onClick={() => toggleBase(id)}
              disabled={baseConfirmada}
            >
              {nome}
            </button>
          ))}
        </div>
        <button
          onClick={confirmarBase}
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
          }}
        >
          Confirmar base
        </button>
      </div>

      <div style={{ flex: 1 }}>
        <h2>Ingredientes Extras (máximo 2)</h2>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: 10 }}>
          {ingredientesExtrasDisponiveis.map(({ id, nome }) => (
            <button
              key={id}
              style={botaoEstilo(
                ingredientesExtras.includes(id),
                !baseConfirmada || (!ingredientesExtras.includes(id) && ingredientesExtras.length >= 2)
              )}
              onClick={() => toggleExtra(id)}
              disabled={!baseConfirmada || (!ingredientesExtras.includes(id) && ingredientesExtras.length >= 2)}
            >
              {nome}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
