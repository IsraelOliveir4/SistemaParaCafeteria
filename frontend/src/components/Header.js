import React from 'react';
import '../styles/Header.css';
import MeuIcone from '../assets/IMG/cafe_1420185.png';

export default function Header() {
  return (
    <header className="header">
      <div className="left">
        <img src={MeuIcone} alt="Logo" className="logo" />
        <span className="title">Cafeteria CodeQual</span>
      </div>
      <div className="right">
        <a href="#contato" className="contato">Contato</a>
      </div>
    </header>
  );
}
