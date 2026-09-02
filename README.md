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

## Bibliotecas utilizadas

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

## Decisões de projeto
- Uso de TestContainers necessario para testar a query nativa, 
porque o DataJpaTest utiliza um banco de dados em memoria(H2), sendo assim necessario o uso de TestContainers para criar um container do postgresql para rodar a query.
__Importante notar que eh possivel utilizar o DataJpaTest em casos de uso de JPQL ou query derivada do JPA__.