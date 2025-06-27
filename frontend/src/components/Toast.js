import React, { useEffect } from 'react';

export default function Toast({ mensagem, tipo, onClose }) {
  useEffect(() => {
    const timer = setTimeout(onClose, 4000);
    return () => clearTimeout(timer);
  }, [onClose]);

  const background = tipo === 'erro' ? '#f44336' : '#4CAF50';

  return (
    <div style={{
      position: 'fixed',
      top: 20,
      right: 20,
      backgroundColor: background,
      color: '#fff',
      padding: '12px 24px',
      borderRadius: '6px',
      boxShadow: '0 2px 6px rgba(0,0,0,0.3)',
      zIndex: 9999,
    }}>
      {mensagem}
    </div>
  );
}
