# Desafio Java e Testes Unitários

O instrutor neste último desafio do bootcamp foi o [Rodrigo Peleias](https://www.linkedin.com/in/rodrigopeleias/).

## Repositório
O código está disponível [neste repositório](https://github.com/zingarelli/desafios-bootcamp-TQI-DIO/tree/main/Java/testes-unitarios-beer-api).

## Desafio
O último desafio consistiu em consolidar os conhecimentos de Testes Unitários (e também um pouco de TDD) em um projeto Java de uma API REST. Para isso, é utilizado o Postman para testar as chamadas à API, bem como JUnit e Hamcrest para os testes unitários. Foi utilizado também o Mockito, para mockar alguns comportamentos de resposta da API.

O instrutor forneceu o projeto já pronto, com toda a estrutura e códigos necessários, inclusive com o arquivo pom.xml contendo todas as dependências e starters para que a aplicação possa ser executada. O projeto consiste em uma API REST para o gerenciamento de estoques de cerveja. Durante o desafio, foram sendo codificados os testes unitários.

O projeto possui somente uma entidade (tabela) chamada Beer (cerveja), que guarda os campos name (nome), brand (marca), type (tipo), max (estoque máximo) e quantity (quantidade de cerveja em estoque).

A API é mapeada e desenvolvida para fornecer os seguintes serviços: criação de uma cerveja, procurar uma cerveja por seu nome, listar todas as cervejas, remover uma cerveja pelo id e incrementar o estoque de uma cerveja, passando o valor do incremento. Também seria implementado o serviço de decremento de estoque, porém não foi abordado pelo instrutor e *eu não pude verificar esta implementação devido ao pouco tempo que eu tinha disponível para finalizar o bootcamp*. O código para esta parte, no entanto, se encontra comentado no projeto, bem como outros testes que também não chegaram a ser implementados.

Os testes unitários se concentraram em duas partes do projeto: BeerController.java, responsável pelas chamadas ao serviço (utilizando GET, POST, PATCH e DELETE), e BeerService.java, responsável por de fato fazer a persistência ao banco (o projeto utiliza o H2, que é um banco de dados que roda em memória). Para tanto, foram cliadas as classes BeerControllerTest.java e BeerServiceTest.java.

### Testes na BeerControllerTest.java
- validar sucesso em uma chamada POST para criação de cerveja;
- validar erro quando é feita uma chamada POST e passado dados nulos em campos obrigatórios;
- validar sucesso em uma chamada GET para encontrar uma cerveja pelo nome;
- validar erro em uma chamada GET quando não encontra uma cerveja pelo nome;
- validar sucesso em uma chamada GET para listar todas as cervejas (tanto quando há cervejas cadastradas, quanto quando o banco está vazio);
- validar sucesso em uma chamada DELETE para apagar uma cerveja pelo seu id;
- validar erro quando é feita uma chamada DELETE enviando um id que não está cadastrado;
- validar sucesso em uma chamada PATCH para incrementar a quantidade de uma cerveja;
- validar erro em uma chamada PATCH ao passar um valor de quantidade de cerveja maior do que o máximo permitido para estoque da cerveja.

### Testes na BeerServiceTest.java
Os seguintes testes unitários foram criados:
- validar sucesso quando uma cerveja é criada e salva; 
- validar exceção quando se tenta inserir uma cerveja que já está cadastrada;
- validar o retorno de um objeto cerveja quando é feita a consulta pelo nome da cerveja;
- validar exceção quando é feita a consulta e não encontra a cerveja pelo nome;
- validar o retorno de uma lista de cervejas quando é feita a consulta para listar todas as cervejas cadastradas;
- validar o retorno de lista vazia quando não há cervejas cadastradas;
- validar sucesso em excluir uma cerveja pelo seu id;
- validar exceção quando da tentativa de excluir uma cerveja com um id não cadastrado;
- validar que a quantidade de uma cerveja é atualizada quando é solicitado o incremento de seu valor;
- validar as exceções quando é solicitado um incremento de quantidade de uma cerveja e este valor é maior ou sua inclusão ultrapassa o estoque máximo da cerveja.

## Ambiente de desenvolvimento
O projeto foi baixado [neste link](https://hermes.digitalinnovation.one/lab_projects/files/12c6add8-2135-48fd-a75b-ebcebf76329c.zip) e teve o pom.xml alterado para ser executado com a versão 11 do Java.

A execução e alteração dos códigos foram feitas utilizando a IDE Visual Studio Code 1.69.2 e o JDK Azul Zulu versão 11.0.15. 

Por ser um projeto baixado já pronto para execução, ela ocorreu sem problemas. No entanto, se desejar obter mais informações sobre o projeto original, é recomendável que leia o README feito pelo instrutor, que se encontra na seção abaixo.

<hr />

# README ORIGINAL

<h2>Digital Innovation: Expert class - Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de estoques de cerveja.</h2>

Nesta live coding, vamos aprender a testar, unitariamente, uma API REST para o gerenciamento de estoques de cerveja. Vamos desenvolver testes unitários para validar o nosso sistema de gerenciamento de estoques de cerveja, e também apresentar os principais conceitos e vantagens de criar testes unitários com JUnit e Mockito. Além disso, vamos também mostrar como desenvolver funcionalidades da nossa API através da prática do TDD.

Durante a sessão, serão abordados os seguintes tópicos:

* Baixar um projeto através do Git para desenolver nossos testes unitários. 
* Apresentação conceitual sobre testes: a pirâmide dos tipos de testes, e também a importância de cada tipo de teste durante o ciclo de desenvolvimento.
* Foco nos testes unitários: mostrar o porque é importante o desenvolvimento destes tipos de testes como parte do ciclo de desenvolvimento de software.
* Principais frameworks para testes unitários em Java: JUnit, Mockito e Hamcrest. 
* Desenvolvimento de testes unitários para validação de funcionalides básicas: criação, listagem, consulta por nome e exclusão de cervejas.
* TDD: apresentação e exemplo prático em 2 funcionaliades importantes: incremento e decremento do número de cervejas no estoque.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Para executar a suíte de testes desenvolvida durante a live coding, basta executar o seguinte comando:

```shell script
mvn clean test
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/beers
```

São necessários os seguintes pré-requisitos para a execução do projeto desenvolvido durante a aula:

* Java 14 ou versões superiores.
* Maven 3.6.3 ou versões superiores.
* Intellj IDEA Community Edition ou sua IDE favorita.
* Controle de versão GIT instalado na sua máquina.
* Muita vontade de aprender e compartilhar conhecimento :)

Abaixo, seguem links bem bacanas, sobre tópicos mencionados durante a aula:

* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)
* [Referência do Intellij IDEA Community, para download](https://www.jetbrains.com/idea/download)
* [Palheta de atalhos de comandos do Intellij](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
* [Site oficial do Spring](https://spring.io/)
* [Site oficial JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Site oficial Mockito](https://site.mockito.org/)
* [Site oficial Hamcrest](http://hamcrest.org/JavaHamcrest/)
* [Referências - testes em geral com o Spring Boot](https://www.baeldung.com/spring-boot-testing)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Referência pirâmide de testes - Martin Fowler](https://martinfowler.com/articles/practical-test-pyramid.html#TheImportanceOftestAutomation)

[Neste link](https://drive.google.com/file/d/1KPh19mvyKirorOI-UsEYHKkmZpet3Ks6/view?usp=sharing), seguem os slides apresentados como o roteiro utilizado para o desenvolvimento do projeto da nossa sessão.