### Lojinha API Automacao
Esse é um repositório que contém a automação de alguns testes de API Rest de um software denominado Lojinha. Os sub-tópicos abaixo descrevem algumas decisões tomadas na estruturação do projeto.

#### Tecnologias Utilizadas
- Java
  https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
- JUnit
  https://mvnrepository.com/artifact/junit/junit
- RestAssured
  https://mvnrepository.com/artifact/io.rest-assured
- Maven
  https://maven.apache.org/

#### Testes automatizados
Testes para validar as partições de equivalência relacionadas ao valor do produto na Lojinha, que estão vinculados diretamente à regra de negócio que diz que o valor do produto deve estar entre R$ 0,01 e R$ 7.000,00.
