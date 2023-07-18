package modulos.produto;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.UsuarioPojo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do módulo produtos")
public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach(){
        //Configurar os dados da API Rest da Lojinha
        baseURI = "http://165.227.93.41";
        //port = 8080; >> pode ser que seja necessário add a porta onde o app está rodando
        basePath = "/lojinha-bugada";

        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("admin");
        usuario.setUsuarioSenha("admin");

        //Obter o token do usuário
        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuario)
            .when()
                .post("v2/login")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Validação do limite inferior definido para o produto >> R$ 0.00")
    public void testLimit1() {

        //Inserir produto abaixo do limite inferior para validar erro HTTP 422
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "  \"produtoNome\": \"Teste Automação1\",\n" +
                        "  \"produtoValor\": 0.00,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"transparente\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"string\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Componente 1\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }

    @Test
    @DisplayName("Validação do limite superior definido para o produto >> R$ 7.000,01")
    public void testLimit2() {

        //Inserir produto acima do limite superior para validar erro HTTP 422
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "  \"produtoNome\": \"Teste Automação2\",\n" +
                        "  \"produtoValor\": 7000.01,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"transparente\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"string\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Componente 1\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
}
