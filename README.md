# Desafio tecnico

## Modelo relacional

![Modelo](/modelagem.png)

A modelagem dos dados aborda as entidades e relacionamentos necessários para atender aos requisitos do desafio técnico,
ha varias ocorrencias de N:N, portanto tabelas compostas foram criadas para atender a essas relações.
(OBS): Na tabela de pedido, os campos do cliente podem ser extraidos para uma tablea propria, fazendo a linkagem por chave estrangeira
tendo um relacionamento 1:N.
## Arquitetura

A aplicacao web foi desenvolvida com arquitetura de monolito utilizando Spring Boot, Angular e PostgreSQL
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
- Uso de TestContainers necessario para testar a query nativa, 
porque o DataJpaTest utiliza um banco de dados em memoria(H2), sendo assim necessario o uso de TestContainers para criar um container do postgresql para rodar a query.
__Importante notar que eh possivel utilizar o DataJpaTest em casos de uso de JPQL ou query derivada do JPA__.
- Uso do MapStruct para fazer o mapeamento de DTOs <-> entidades com nullchecks.
- Utilizar entidades para tabelas intermediaria para relacionamento N:N devido a necessidade de colunas extras.
- Utilizar Spring Validation para validar os dados de entrada.
- Angular material para protipagem rapida.
## Endpoints 

- Todas as rotas seguem o mesmo padrao de nomenclatuar.
- Swagger com documentacao mais extensa disponivel em http://localhost:8080/swagger-ui.html

| Rota            | GET   | POST | PUT   | DELETE | GET |
|-----------------|-------|------|-------|--------|-----|
| api/bebidas     | /{id} | Body | /{id} | /{id}  | ?{descricao}|
| api/ingrediente | /{id} | Body | /{id} | /{id}  |?{descricao}|
 | api/hamburguers| /{id} | Body | /{id} | /{id}  |?{descricao}|


## Como rodar a aplicacao

1. Clonar repositorio: git clone https://github.com/yura2312/salutem-desafio.git
2. Rodar 