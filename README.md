
Nos 2 primeiros microservices foram utilizados python e MySql, dado criticidade dos dados, no segundo microservice poderia ainda ser aplicado uma tecnologia de cache por conta da extração de dados dos algoritmos de aprendizagem.
No terceiro microservice foi adotado o MongoDB por conta da necessidade de ter uma acesso extremamente rápido, acabei usando java nesse caso por problemas que tive relacionado ao python com mongo.

# Microservice 1

## Tecnologias adotadas:
- Python
- Flask
- MySql

## Dados armazenados:
User
- id
- login
- password
- cpf
- name
- address

Debt
- id
- user_id
- value
- date

## Como rodar:
1 - Criar e executar o banco: docker-compose -f mysql.yaml up
2 - Executar aplicação: venv\Scripts\python.exe api.py

# Microservice 2

## Tecnologias adotadas:
- Python
- Flask
- MySql

## Dados armazenados:

Income
- id
- profile_id
- description
- value

Profile
- id
- birthday
- address

Property
- id
- profile_id
- description
- value

## Como rodar:
1 - Criar e executar o banco: docker-compose -f mysql.yaml up
2 - Executar aplicação: venv\Scripts\python.exe api.py

# Microservice 3

## Tecnologias adotadas:
- Java
- Spring
- MongoDB

## Dados armazenados:

FinancialMovement
- id
- value
- company

LastPurchase
- id
- date
- value
- description

LastQuery
- id
- cpf
- date


## Como rodar:
1 - Criar e executar o banco: docker-compose -f mongodb.yml up
2 - Executar aplicação: mvn spring-boot:run
