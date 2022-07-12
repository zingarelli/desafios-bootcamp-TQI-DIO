/* 
  Criação do serviço que irá consumir da api do GitHub, utilizando o Axios.
  As chamadas serão feitas pelos componentes onde for necessário, digitando apenas api.
*/

import axios from 'axios';

const api = axios.create({
    baseURL: 'https://api.github.com/'
});

export default api;