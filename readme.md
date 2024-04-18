# Recepção de Pedidos dos Clientes

Este é um projeto de recepção de pedidos dos clientes, desenvolvido em Java 17 com Spring Boot 3.x e utilizando o banco de dados MariaDB. O projeto oferece endpoints para gerenciar pedidos e clientes.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Banco de dados MariaDB

## Estrutura do Projeto

O projeto está organizado em pacotes, com os seguintes principais componentes:

- `com.pedidos.controle.model`: Contém as entidades do domínio, como Cliente e Pedido.
- `com.pedidos.controle.dto`: Contém os objetos de transferência de dados (DTO) utilizados nas requisições.
- `com.pedidos.controle.service`: Contém as interfaces e implementações dos serviços de negócio.
- `com.pedidos.controle.repository`: Contém as interfaces de repositório para acesso ao banco de dados.
- `com.pedidos.controle.restController`: Contém os controladores REST que definem os endpoints da aplicação.

## Endpoints

### PedidoController

- `GET /pedidos`: Retorna todos os pedidos.
- `GET /pedidos/buscar`: Retorna os pedidos filtrados de acordo com o critério especificado.
- `GET /pedidos/{id}`: Retorna um pedido específico pelo seu ID.
- `POST /pedidos`: Cria novos pedidos.
- `PUT /pedidos/{id}`: Atualiza um pedido existente.
- `DELETE /pedidos/{id}`: Deleta um pedido existente.

### ClienteController

- `GET /clientes`: Retorna todos os clientes.
- `GET /clientes/{id}`: Retorna um cliente específico pelo seu ID.
- `POST /clientes`: Cria um novo cliente.
- `PUT /clientes/{id}`: Atualiza um cliente existente.
- `DELETE /clientes/{id}`: Deleta um cliente existente.

## Executando o Projeto

Para executar o projeto localmente, siga os passos abaixo:

1. Clone o repositório para o seu ambiente local.
2. Certifique-se de ter o Java 17 e o Maven instalados.
3. Configure as propriedades do banco de dados no arquivo `application.properties`.
4. Execute o comando `mvn spring-boot:run` na raiz do projeto.

## Scripts SQL

Abaixo estão os scripts SQL para criar as tabelas no banco de dados e inserir dados de exemplo:

```sql
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(255),
    INDEX idx_cliente_email (email)
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(255) NOT NULL,
    data_cadastro DATE,
    nome_produto VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    quantidade INT,
    codigo_cliente BIGINT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(id)
);

INSERT INTO cliente (nome, email, telefone) VALUES
('João Silva', 'joao@example.com', '1234567890'),
('Maria Oliveira', 'maria@example.com', '9876543210'),
('Carlos Santos', 'carlos@example.com', '5555555555'),
('Ana Souza', 'ana@example.com', '1111111111'),
('Pedro Ferreira', 'pedro@example.com', '9999999999'),
('Mariana Costa', 'mariana@example.com', '8888888888'),
('Fernando Pereira', 'fernando@example.com', '7777777777'),
('Camila Almeida', 'camila@example.com', '6666666666'),
('Rafael Nunes', 'rafael@example.com', '4444444444'),
('Luciana Barbosa', 'luciana@example.com', '3333333333');
