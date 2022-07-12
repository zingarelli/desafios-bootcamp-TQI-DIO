# Desafio React 

O instrutor neste desafio foi o [Matheus Benites](https://www.linkedin.com/in/omatheusbenites/).

## Página Criada
Você pode visualizar a página criada em: https://desafios-bootcamp-tqi-dio-ivory.vercel.app

## Desafio
O desafio consistiu em criar uma aplicação em React que consome dados da API do GitHub por meio da busca por um usuário. Com estes dados, é criada uma página personalizada do usuário, com algumas informações de perfil e duas abas mostrando os repositórios criados pelo usuário e os repositórios que ele/ela marcou com Star.

Além de seguir o passo a passo do instrutor, fiz pequenas alterações no layout e trouxe outros dados da API do GitHub. Uma das alterações foi criar um balão de texto com a biografia do usuário (se houver). Também adicionei algumas regras para que o conteúdo dos campos seja mostrado somente quando não estiverem vazios, bem como um texto informativo para o caso de o usuário não ser encontrado.

Segue print de parte da página criada, retornando os dados do instrutor (usuário: benits):

![print da página criada](https://user-images.githubusercontent.com/19349339/178586130-703956e2-e155-4fd6-927d-dd8d8ac675d4.png)

## Documentação e Bibliotecas
- [API do GitHub](https://docs.github.com/en/rest)
- Axios: biblioteca JavaScript para fazer e tratar requisições HTTP. Caso necessário, é possível instalá-la com o comando
`npm i axios`
- Styled Components: biblioteca JavaScript para utilizar CSS nos arquivos JSX. Caso necessário, é possível instalá-la com o comando `npm install --save styled-components`
- react-tabs: biblioteca JavaScript para criar abas (tabs); utilizada no projeto para navegar entre as abas de repositórios e Stars. Caso necessário, é possível instalá-la com o comando `npm install --save react-tabs`

## Boostrap

Este projeto foi criado com o [Create React App](https://github.com/facebook/create-react-app).

Para iniciar o servidor em desenvolvimento, use o comando:

`npm start`

A aplicação irá abrir localmente no endereço [http://localhost:3000](http://localhost:3000).