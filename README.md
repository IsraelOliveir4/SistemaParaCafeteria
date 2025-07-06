# ☕ Sistema de Montagem de Café

Este projeto permite ao usuário montar cafés personalizados ou identificar sabores clássicos com base nos ingredientes selecionados. Backend em Java + Spring Boot, frontend em React, banco de dados PostgreSQL e orquestração via Docker.

---

## 📦 Instruções de Configuração

1. **Pré-requisitos**:
   - Docker e Docker Compose instalados

2. **Rodar o projeto**:

```bash
#1 Clone o repositório do projeto
git clone https://github.com/IsraelOliveir4/SistemaParaCafeteria.git

#2 Entre no diretorio do projeto
cd SistemaParaCafeteria

#3 Inicie os containers
docker-compose up --build
```

3. **Acessos**:
   - **Frontend**: http://localhost:3000  
   - **Backend (Swagger ou API)**: http://localhost:8080/ingredientes
   - **PostgreSQL**: localhost:5432 (user: `postgres`, password: `123456`)

---

## ✅ Funcionalidades Implementadas

- Seleção de ingredientes base e extras
- Identificação automática de sabores clássicos
- Cálculo dinâmico de preço
- Validação de pedido no backend
- Integração com banco PostgreSQL usando Liquibase
- Deploy completo via Docker

---

## 🔍 Melhorias observadas na revisão de código

- Inserir painel de administradores para cadastrar novos sabores
- Inserir tela de login
---

## 📘 Especificações de uso (Gherkin)

```gherkin
Feature: Montar café com ingredientes base e extras

  Scenario: Usuário monta café com ingredientes base
    Given o usuário acessa a tela principal
    When seleciona os ingredientes base
    And clica em "Confirmar base"
    Then o sistema identifica o sabor clássico, se existir

  Scenario: Usuário adiciona ingredientes extras
    Given o usuário confirmou a base
    When seleciona até dois ingredientes extras
    Then o sistema atualiza o preço final

  Scenario: Pedido personalizado
    Given o usuário seleciona ingredientes que não formam um sabor clássico
    Then o sistema exibe "Personalizado" como nome do café
```

---

## 📂 Localização da documentação versionada

- Este `README.md` está versionado no repositório na raiz do projeto.
- Scripts Liquibase: `backend/src/main/resources/db/changelog`
- Componentes React organizados em `frontend/src/components`

---

## 👨‍💻 Tecnologias Utilizadas

- **Java 17** + **Spring Boot**
- **React.js**
- **PostgreSQL**
- **Liquibase**
- **Docker + Docker Compose**

---

## 📞 Contato

Desenvolvido por [Israel Oliveira](https://github.com/IsraelOliveir4) – Israeloliver@email.com