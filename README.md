# SISTEMA DE CATALOGO DE SUPERMERCADO
Um sistema desenvolvido para fim de estudo.

## Features
* Cadastro de produtos com categoria;
* Atualizar ou excluir produtos
* Visualizar produtoso cadastrados
* Buscar produtos 
* Filtrar produtos por categoria

## TECH STACK
- Spring boot
- Banco de dados: PostgreSQL
- Gerenciamento de dependência: Maven
- Wildfly
  
## Possível Problemas
- Se tiver o erro ``java.lang.reflect.InaccessibleObjectException``, coloque ``--add-opens java.base/java.lang=ALL-UNNAMED`` no VM OPTIONS da configuração do Wildfly 