Toda documentação da API pode ser encontrada no swagger acessando:

    http://localhost:8080/swagger-ui/index.html#/

No projeto encontra-se tambem o data-base usada no firebird versão 4

Mas caso não seja usado o spring esta configurado para criar um usuario com:

    login: admin
    password: admin

    Role: ADMIN

Endereços da API

Autorização e cadastro de Role:
    
    POST:   http://localhost:8080/api/auth/cadastrar-role
    POST:   http://localhost:8080/api/auth/login

Cliente:

    POST:   http://localhost:8080/api/cliente/adicionar
    PUT:    http://localhost:8080/api/cliente/editar
    DELETE: http://localhost:8080/api/cliente/excluir
    GET:    http://localhost:8080/api/cliente/listar

Endereço do cliente:

    POST:   http://localhost:8080/api/endereco/adicionar
    PUT:    http://localhost:8080/api/endereco/editar
    DELETE: http://localhost:8080/api/endereco/excluir
    GET:    http://localhost:8080/api/endereco/listar

Entrega:

    POST:   http://localhost:8080/api/entrega/adicionar
    PUT:    http://localhost:8080/api/entrega/editar
    DELETE: http://localhost:8080/api/entrega/excluir
    GET:    http://localhost:8080/api/entrega/listar

Pedido: 

    POST:   http://localhost:8080/api/pedido/adicionar
    PUT:    http://localhost:8080/api/pedido/editar
    DELETE: http://localhost:8080/api/pedido/excluir
    GET:    http://localhost:8080/api/pedido/listar

Produto:
    
    POST:   http://localhost:8080/api/produto/adicionar
    PUT:    http://localhost:8080/api/produto/editar
    DELETE: http://localhost:8080/api/produto/excluir
    GET:    http://localhost:8080/api/produto/listar

Usuario:
 
    POST:   http://localhost:8080/api/cadastro/user


