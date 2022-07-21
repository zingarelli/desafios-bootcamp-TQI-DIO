# Desafio Java e Padrões de Projetos (Design Patterns)

O instrutor neste desafio foi novamente o [Venilton FalvoJr](https://www.linkedin.com/in/falvojr/), Tech Lead da DIO.

## Repositório
O código está disponível [neste repositório](https://github.com/zingarelli/desafios-bootcamp-TQI-DIO/tree/main/Java/padroes-de-projeto).

## Desafio
O desafio consistiu em implementar três design patterns: Singleton, Strategy e Facade. A implementação foi feita utilizando Java puro, codificando cada padrão, e também utilizando o Java Spring, para verificar como o Spring Framework facilita a vida do desenvolvedor com os conceitos de Inversão de Controle e Injeção de Dependência. 

## Implementação em Java
Cada pattern foi implementado em pastas diferentes, dentro do pacote one.digitalinnovation.gof:
- singleton: foram feitas três implementações diferentes: Lazy, Eager e LazyHolder, que se diferenciam pelo momento em que o construtor é chamado;

- strategy: foi criada a interface RobotBehavior com um método move(), que simularia a movimentação de um robô. Três estratégias diferentes implementaram esta interface: NormalBehavior, DefensiveBehavior e AggressiveBehavior, sobrescrevendo o método move(). A classe Robot foi criada para poder testar essas diferentes estratégias, por meio do método setBehavior();

- facade: para exemplificar o pattern simplificando a integração entre subsistemas, foram criadas as pastas subsystem1 e subsystem2, fora do pacote das patterns, simulando dois sistemas diferentes que irão se comunicar. Em subsystem1 há a classe CRMService, que simula a inclusão do nome e endereço de um cliente no cadastro de CRM. Em subsystem2 há a classe ZipCodeAPI, que simula uma API que devolve cidade e estado a partir de um dado ZipCode (CEP). O pattern se utiliza da ZipCodeAPI para obter dados de cidade e estado e utilizar estes dados para alimentar a CRMService. Desse modo, por meio da classe Facade é possível adicionar/alterar o endereço de um cliente sem saber como os subsistemas se comportam.

## Ambiente de desenvolvimento
A aplicação foi desenvolvida utilizando a IDE Visual Studio Code (VS Code) 1.69.2, com o JDK Azul Zulu versão 11.0.15.

Para rodar o projeto no VS Code, é necessário abrir a pasta em que se encontra o projeto, para que o VS Code consiga iniciar as configurações de caminho corretamente. Feito isso, é possível testar as implementações rodando o arquivo Test.java, disponível no pacote one.digitalinnovation.gof