# Processo seletivo - QA

Bem vindo, candidato. 

Estamos felizes que você esteja participando do processo seletivo para a vaga de QA do Senai - Soluções Digitais.

A prova deverá utilizar as seguintes tecnologias: 
- Linguagem de programação orientada a objeto
- Banco de dados PostgreSQL
- GIT

Na etapa da entrevista deverá ser apresentado a aplicação em funcionamento.

## Instruções para a execução da prova

***O documento com o estudo de caso do que precisa ser desenvolvido será enviado por e-mail no horário previsto em edital.***

A prova será uma aplicação web dividida em backend e frontend. O sistema deve ser desenvolvido utilizando uma das seguintes linguagens orientadas a objeto: Java, PHP ou Javascript. O backend e frontend podem ser de linguagens diferentes. O banco de dados deverá ser o PostgreSQL.

Fica a escolha do candidato quais frameworks e servidores serão utilizados, desde que seja uma aplicação web. 

***O Banco utilizado na prova deverá ser PostgreSQL.***

Esse repositório possui apenas esse Readme com as instruções da prova. No entanto, **todo desenvolvimento deve ser commitado nesse repositório** até a data citada no edital.

Por fim, altere esse arquivo com as instruções de como poderemos testar o seu código (quais libs usar, qual servidor, etc) abaixo.

## Informações extras

- Descreva ao final deste documento (Readme.md) o detalhamento de funcionalidades implementadas, sejam elas já descritas na modelagem e / ou extras.
- Detalhar também as funcionalidades que não conseguiu implementar e o motivo.
- Caso tenha adicionado novas libs ou frameworks, descreva quais foram e porque dessa agregação.

(Escreva aqui as instruções para que possamos corrigir sua prova, bem como qualquer outra observação sobre a prova que achar pertinente compartilhar)

# ☕ Sistema de Montagem de Café

Este projeto permite ao usuário montar cafés personalizados ou identificar sabores clássicos com base nos ingredientes selecionados. Backend em Java + Spring Boot, frontend em React, banco de dados PostgreSQL e orquestração via Docker.

---

## 📦 Instruções de Configuração

1. **Pré-requisitos**:
   - Docker e Docker Compose instalados

2. **Rodar o projeto**:

```bash
#1 Clone o repositório do projeto
git clone https://github.com/SENAI-SD/qa-junior-00925-2025-089.039.185-84.git

#2 Entre no diretorio do projeto
cd qa-junior-01452-2025-089.039.185-84

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

- Substituição de IDs fixos por dados dinâmicos do backend
- Padronização de nomes e componentes React
- Tratamento de erros em chamadas de API
- Separação de responsabilidades entre frontend e backend

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

## ❌ Requisitos não atendidos e justificativas

- Integração com SonarQube não foi incluída devido à limitação de tempo e ambiente
- Login/autenticação de usuário não foi implementado por não ser requisito central da funcionalidade de montagem de café

---

## 🐞 Bug funcional identificado

- Ao atualizar ingredientes diretamente no banco, sem reiniciar o backend, sabores clássicos podem não ser detectados corretamente.  
  **Justificativa**: a lógica depende de dados consistentes sincronizados entre banco e cache (caso implementado futuramente).

---

## 🛠️ (Diferencial) SonarQube - Relatório de Qualidade

**[Não incluído neste projeto]**  
Caso desejado, pode-se configurar SonarQube localmente e integrar ao pipeline de build com plugins Maven/Gradle.

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