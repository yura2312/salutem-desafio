# Desafio técnico

## Modelo relacional inicial

![Modelo](/modelagem.png)

A modelagem dos dados aborda as entidades e relacionamentos necessários para atender aos requisitos do desafio técnico,
há várias ocorrências de N:N, portanto tabelas compostas foram criadas para atender a essas relações.
(OBS): Na tabela de pedido, os campos do cliente podem ser extraídos para uma tabela própria, fazendo a linkagem por chave estrangeira
tendo um relacionamento 1:N.

Para realizar o requisito opcional de cálculo total, foi necessário adicionar uma coluna de preço nas tabelas intermediárias
e adicionar a coluna preço total na tabela de pedido, para que o cálculo seja feito no momento da criação do pedido
## Arquitetura

A aplicação web foi desenvolvida com arquitetura de monolito utilizando Spring Boot, Angular e PostgreSQL
![Arquitetura](/arquitetura.png)

## Bibliotecas/Tecnologias utilizadas

### Backend
- Java 25 
- Spring Boot 4.1.1
- Spring Data JPA
- Spring Validation
- Spring Docker Compose
- FLyway 
- MapStruct
- Lombok
- TestContainers

### Frontend
- Angular 22
- Angular Material

## Decisões de projeto
- Uso de TestContainers necessário para testar a query nativa,
porque o DataJpaTest utiliza um banco de dados em memória(H2), sendo assim necessário o uso de TestContainers para criar um container do postgresql para rodar a query.
__Importante notar que é possível utilizar o DataJpaTest em casos de uso de JPQL ou query derivada do JPA__.

- Uso do MapStruct para fazer o mapeamento de DTOs <-> entidades com nullchecks.

- Utilizar entidades para tabelas intermediárias para relacionamento N:N devido à necessidade de colunas extras.

- Utilizar Spring Validation para validar os dados de entrada.

- Angular material para prototipagem rápida.

## Endpoints 
- Postman com coleção de endpoints na pasta /api
- Swagger com documentação dos endpoints http://localhost:8080/swagger-ui.html.html

| Rota            | GET   | POST | PUT   | DELETE | GET |
|-----------------|-------|------|-------|--------|-----|
| api/bebidas     | /{id} | Body | /{id} | /{id}  | ?{descricao}|
| api/ingrediente | /{id} | Body | /{id} | /{id}  |?{descricao}|
 | api/hamburguers| /{id} | Body | /{id} | /{id}  |?{descricao}|
| api/pedidos     | /{id} | Body | /{id} | /{id}  |

### Requests 

#### Request Post Bebida
```js
{
  "descricao": "Coca",
  "precoUnitario": 6.25,
  "contemAcucar": true
}
```

### Request Post Ingrediente
```js
{
 "descricao": "Pao frances",
 "precoUnitario": 5.99,
 "adicional": true
}
```
### Request Post Hamburguer
```js
{
  "descricao": "X-Calabacon",
  "valor": 16.99,
  "idIngredientes": [1,2,8,9]
}
```
### Request Post Pedido
```js
{
  "descricao": "Combo completo com lanche e bebida",
  "clienteNome": "Gabriel da Silva",
  "clienteEndereco": "Rua das Flores, 123",
  "clienteTelefone": "11999999999",
  "idHamburguerQuantidade": {
    "3": 3,
    "2": 1
  },
  "idBebidaQuantidade": {
      "4": 2
  },
  "observacoes": "x-tudo sem picles"
}
```


## Como rodar a aplicacao

1. Clonar repositorio: git clone https://github.com/yura2312/salutem-desafio.git
2. Rodar o docker compose: docker-compose up -d
3. Acessar a aplicacao em http://localhost:8080