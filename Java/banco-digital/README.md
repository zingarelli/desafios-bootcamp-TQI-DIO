# Desafio Java e Orientação a Objetos

O instrutor neste desafio foi o [Venilton FalvoJr](https://www.linkedin.com/in/falvojr/), Tech Lead da DIO.

## Repositório
O código está disponível [neste repositório](https://github.com/zingarelli/desafios-bootcamp-TQI-DIO/tree/main/Java/banco-digital).

## Desafio
Neste primeiro desafio foi criada uma aplicação simulando um Banco Digital, com cadastro de clientes, contas bancárias e realização de transações bancárias, de modo a consolidar os conceitos de Orientação a Objetos (abstração, encapsulamento, herança e polimorfismo).

Há uma classe Conta e dela derivam duas subclasses: ContaCorrente e ContaPoupanca. A classe Conta guarda o número de agência e conta, o saldo e um objeto da classe Cliente. Nela foram definidos os métodos para saque, depósito, transferência e extrato. Na classe ContaPoupança, fiz a sobrescrita dos métodos de saque e transferência, adicionando a regra de que não é possível deixar o saldo negativo (algo que é possível para a classe ContaCorrente). Melhorias futuras: impedir operações quando o valor informado é negativo e talvez lançar exceção para este caso e para o caso de saldo negativo em conta poupança.

A classe Cliente guarda os atributos nome, cpf, profissão e telefone. Ela possui somente métodos de get e set. Criei dois construtores diferentes, um que recebe todos os atributos e outro que recebe nome e cpf, deixando assim que profissão e telefone ficassem opcionais. Uma melhoria futura é fazer classes derivadas para diferenciar, por exemplo, uma conta de pessoa física e de pessoa jurídica.

A classe Banco guarda o nome do banco e duas listas, uma para contas e outra para clientes. Defini a lista de clientes como sendo um HashSet, para impedir a duplicação de clientes quando estes possuem mais de uma conta. A classe possui um método addConta(), que recebe uma conta e, por meio dela, popula a lista de contas e clientes.

A aplicação roda na classe Main, que instancia um banco, algumas pessoas e contas, e faz diversos testes com operações bancárias. A classe também possui duas funções para imprimir a lista de contas e de clientes, utilizando os métodos da classe Banco. Uma melhoria futura é fazer um menu interativo para a aplicação, possibilitando o cadastro de bancos, clientes e contas, bem como fazer as operações bancárias. Por hora, isso é simulado dentro da classe Main de forma hardcoded.

Criei também testes unitários para as classes Conta, Cliente e Banco, aproveitando o que foi ensinado em um curso posterior do bootcamp "Testes Unitário com JUnit". Por conta disso, o código foi dividido em dois packages: main e test, para separar o que é aplicação e o que é teste unitário. Os testes foram feitos na JUnit 5 e executados utilizando o Visual Studio Code.

## Ambiente de desenvolvimento
**Atenção:** Esta aplicação não foi feita com nenhum gerenciador de projetos, então ao baixar o repositório é necessário criar o projeto e adicionar as bibliotecas necessárias para poder executar a aplicação e os testes.

A aplicação foi inicialmente desenvolvida utilizando a IDE IntelliJ Community Edition 2022.1.3, com o JDK Azul Zulu versão 11.0.15.

Posteriormente, a aplicação foi refatorada utilizando a IDE Visual Studio Code 1.69.2, adicionando testes unitários.

A versão do JUnit utilizada para os testes unitários se encontra na [pasta lib](https://github.com/zingarelli/desafios-bootcamp-TQI-DIO/tree/main/lib).