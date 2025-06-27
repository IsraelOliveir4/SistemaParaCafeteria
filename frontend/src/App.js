import React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MontarCafePage from './pages/MontarCafePage';
import Header from './components/Header';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<MontarCafePage />} />
      </Routes>
    </Router>


  );
}

export default App;
