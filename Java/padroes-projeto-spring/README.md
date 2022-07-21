# Desafio Java e Padrões de Projetos (Design Patterns)

O instrutor neste desafio foi novamente o [Venilton FalvoJr](https://www.linkedin.com/in/falvojr/), Tech Lead da DIO.

## Repositório
O código está disponível [neste repositório](https://github.com/zingarelli/desafios-bootcamp-TQI-DIO/tree/main/Java/padroes-projeto-spring).

## Desafio
O desafio consistiu em implementar três design patterns: Singleton, Strategy e Facade. A implementação foi feita utilizando Java puro, codificando cada padrão, e também utilizando o Java Spring, para verificar como o Spring Framework facilita a vida do desenvolvedor com os conceitos de Inversão de Controle e Injeção de Dependência. 

## Implementação utilizando Spring Framework
O projeto Maven Spring Boot foi gerado via [Spring Initializr](https://start.spring.io), versão 2.5.4 do Spring Boot, Java 11, e com as seguintes Starters (Dependencies):

 * Spring Data JPA
 * Spring Web
 * H2 Database
 * OpenFeign

Foi também adicionada manualmente a dependência para a OpenAPI, versão 1.5.10, que possui uma interface chamada Swagger que permite fazer as chamadas Rest via navegador de maneira mais intuitiva e prática.

Para verificar os patterns, foi criada uma aplicação que alimenta duas tabelas: Cliente (nome e endereço) e Endereço (cep, logradouro, cidade, etc.). É utilizada a API disponibilizada pelo [ViaCEP](https://viacep.com.br), em que é possível consultar um CEP e receber um JSON com os dados deste CEP.

Os códigos estão no pacote one.digitalinnovation.gof.controller, dentro da pasta main. É possível verificar os padrões na classe ClienteServiceImpl, no pacote one.digitalinnovation.gof.service.impl.

*Para ser sincero, a implementação de um projeto Java com o Spring Framework é ainda confuso para mim, portanto, somente segui os passos do instrutor. **Dada a proximidade do final do bootcamp e por eu não possuir conhecimentos suficientes nesse tema, não fiz nenhuma evolução no projeto**.* 

## Ambiente de desenvolvimento
A aplicação foi desenvolvida utilizando a IDE Visual Studio Code (VS Code) 1.69.2, com o JDK Azul Zulu versão 11.0.15.

Para rodar o projeto no VS Code, é necessário abrir a pasta em que se encontra o projeto, para que o VS Code consiga iniciar as configurações de caminho corretamente. 

Caso o VS Code mostre erros no arquivo pom.xml, clique com o botão direito neste arquivo e selecione "Reload Project". 

É possível iniciar o servidor rodando o arquivo PadroesProjetoSpringApplication.java. Feito isso, é possível utilizar o Swagger digitando o seguinte endereço no navegador: http://127.0.0.1:8080/swagger-ui.html

Segue print da tela do Swagger:

![Print da tela do Swagger](https://user-images.githubusercontent.com/19349339/180311808-990264a7-417a-4ffa-a38e-d946f8540dd6.png)