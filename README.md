# 🍰 API Sistema de Pedidos - Confeitaria

## 👥 Integrantes

* Giovanny dos Santos - 34612998
* Yorx Anthony Contreras Pacheco - 34577220
* Luiz Felipe Correa de Lima - 8826431297
* -
---

## 📌 Descrição do Projeto

Este projeto consiste no desenvolvimento de uma API para gerenciamento de pedidos de uma confeitaria.

A aplicação permite o cadastro de produtos, criação de pedidos com múltiplos itens, cálculo automático do valor total e controle de status de pagamento.

O sistema agora realiza o consumo de uma API externa para processamento de pagamentos (https://github.com/Felipelima2001/APIexterna.git) e integra mensageria assíncrona utilizando RabbitMQ com uma arquitetura de conexões centralizada.

O sistema foi desenvolvido em Java puro, sem uso de frameworks como Spring Boot, utilizando JDBC para integração com banco de dados MySQL.

---

## 🧠 Funcionalidades

### 🟢 Produtos

* Cadastrar produto
* Listar produtos
* Buscar produto por ID
* Atualizar produto
* Deletar produto

### 🟡 Pedidos

* Criar pedido com múltiplos produtos
* Buscar pedido por ID
* Deletar pedido
* Regra: pedidos pagos não podem ser deletados

### 🔵 Pagamento

* Atualizar status do pedido para "PAGO"
* Validação de status antes do pagamento
* Integração com API externa de pagamentos para validação das transações
* Mensageria assíncrona com publicação e consumo de mensagens de pagamento via RabbitMQ

---

## ⚙️ Tecnologias Utilizadas

* Java
* JDBC
* MySQL
* XAMPP (phpMyAdmin)
* Postman
* Jackson (JSON)
* RabbitMQ (Message Broker)
* HttpClient (Consumo da API Externa)

---

## 🗄️ Estrutura do Banco de Dados


```sql
CREATE DATABASE confeitaria_db;
USE confeitaria_db;

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL
);

CREATE TABLE pedido_item (
    pedido_id INT,
    produto_id INT,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);
🚀 Como Executar o Projeto

Iniciar o MySQL pelo XAMPP

Criar o banco de dados executando o script acima

Certificar-se de que o servidor do RabbitMQ está ativo e rodando localmente

Certificar-se de que a API Externa de pagamentos está em execução

Configurar usuário e senha do banco no ConnectionFactory e o host do RabbitMQ no RabbitMQConnection

Executar a classe Server.java (API HTTP Principal)

Executar a classe RabbitMQConsumer.java em background para escutar a fila

Testar as rotas utilizando o Postman

🌐 Rotas da API

🟢 Produtos

POST /produtos → Cadastrar produto

GET /produtos → Listar produtos

GET /produtos?id=1 → Buscar produto por ID

PUT /produtos → Atualizar produto

DELETE /produtos?id=1 → Deletar produto

🟡 Pedidos

POST /pedidos → Criar pedido

GET /pedidos/buscar?id=1 → Buscar pedido

DELETE /pedidos/deletar?id=1 → Deletar pedido

🔵 Pagamento

POST /pagamentos?id=1 → Confirmar pagamento (Consome a API externa de validação e despacha o evento via RabbitMQProducer para a fila)

🔄 Fluxo de Funcionamento

Cadastro de produtos

Criação de pedido com IDs dos produtos

Cálculo automático do valor total

Envio dos dados e validação do pagamento na API externa externa

Publicação da confirmação na fila do RabbitMQ através do Producer

Consumo da mensagem de forma assíncrona pelo Consumer para efetivar a baixa

Atualização do status para "PAGO"

Validação de regras de negócio

🧩 Regras de Negócio

Pedido não pode ser criado sem itens

Produto deve ter preço válido

Pedido pago não pode ser deletado

Valor total é calculado automaticamente

A confirmação final do pagamento depende do retorno positivo da API externa e do processamento correto da fila do RabbitMQ

📊 Considerações Finais

O projeto demonstra a aplicação de conceitos fundamentais de desenvolvimento backend, incluindo:

Arquitetura em camadas (Controller, Service, Repository)

Persistência de dados com JDBC

Manipulação de JSON

Implementação de regras de negócio

Integração com banco de dados relacional

Centralização e reaproveitamento de conexões de mensageria com RabbitMQConnection

Arquitetura baseada em eventos assíncronos (Producer/Consumer)

Comunicação síncrona entre microsserviços via API REST externa

✅ Status do Projeto
✔ Concluído
✔ Funcional
✔ Pronto para testes via Postman
