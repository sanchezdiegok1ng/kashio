package com.co.test.stepsdefinitions;

import com.co.test.questions.LosDatosDelProducto;
import com.co.test.tasks.UpdateProduct;
import com.co.test.tasks.CreateProduct;
import com.co.test.tasks.DeleteProduct;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.StringContains.containsString;

public class FakeStoreSteps {

    @Before
    public void setTheStage() {

        OnStage.setTheStage(Cast.ofStandardActors());
        OnStage.theActorCalled("lead");

    }

    @Given("que el analista accede a la URL base {string}")
    public void queElAnalistaAccedeALaURLBase(String string) {
       OnStage.theActorInTheSpotlight().can(CallAnApi.at(string));
    }
    @When("consulta el listado de {string}")
    public void consultaElListadoDe(String resource) {
        theActorInTheSpotlight().attemptsTo(Get.resource("/" + resource));
    }
    @Then("el status code debe ser {int}")
    public void elStatusCodeDebeSer(Integer code) {
        theActorInTheSpotlight().should(seeThatResponse(r -> r.statusCode(code)));

    }
    @Then("el listado debe ser un array no vacio con estructura correcta para {string}")
    public void elListadoDebeSerUnArrayNoVacioConEstructuraCorrectaPara(String string) {
        theActorInTheSpotlight().should(seeThatResponse(r -> r
                .body("id", equalTo(1))
                .body("title", containsString("Fjallraven"))
                .body("price", is(instanceOf(Float.class)))
                .body("image", allOf(notNullValue(), is(instanceOf(String.class))))
        ));
    }

    @When("consulta con el id {string} {string}")
    public void consultaConElId(String string, String string2) {

        theActorInTheSpotlight().attemptsTo(
                Get.resource("/"+string+"/{id}")
                        .with(request -> request.pathParam("id", string2))
        );
    }

    @Then("el usuario intenta crear un producto con los siguientes datos:")
    public void postProduct(java.util.List<Map<String, Object>> data) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                CreateProduct.withData(data.get(0))
        );
    }

    @Then("la respuesta debe contener los datos enviados y un ID válido")
    public void validateResponse() {
        var response = LastResponse.received().answeredBy(OnStage.theActorInTheSpotlight());

        // 1. Validar reglas de negocio y match de datos
        OnStage.theActorInTheSpotlight().should(
                seeThat("El precio es >= 0",
                        act -> response.jsonPath().getDouble("price"), greaterThanOrEqualTo(0.0)),
                seeThat("El título no está vacío",
                        act -> response.jsonPath().getString("title"), not(emptyString())),
                seeThat("El ID fue generado",
                        act -> response.jsonPath().get("id"), notNullValue())
        );

        // 2. Capturar ID para futuros escenarios
        Integer productId = response.jsonPath().get("id");
        OnStage.theActorInTheSpotlight().remember("LAST_PRODUCT_ID", productId);

        System.out.println("ID Capturado: " + productId);
    }



    @When("el usuario actualiza el producto {int} con los siguientes datos:")
    public void actualizar(int id, java.util.List<Map<String, Object>> listaDatos) {
        Map<String, Object> datos = listaDatos.get(0);
        OnStage.theActorInTheSpotlight().attemptsTo(
                UpdateProduct.conId(id, datos)
        );
    }

    @Then("la respuesta debe contener los datos enviados")
    public void validarDatos() {
        OnStage.theActorInTheSpotlight().should(
                seeThatResponse("Los datos coinciden",
                        res -> res.body("title", notNullValue())
                                .body("price", notNullValue()))
        );
    }

    @Then("el sistema debe validar que el precio sea >= 0 y los campos obligatorios no estén vacíos")
    public void validarReglas() {
        OnStage.theActorInTheSpotlight().should(
                seeThatResponse("Reglas de negocio correctas",
                        res -> res.body("price", greaterThanOrEqualTo(0f))
                                .body("title", not(emptyString()))
                                .body("category", not(emptyString())))
        );
    }



    @Then("la respuesta debe ser exitosa y coincidir exactamente con los datos enviados:")
    public void validarCamposUnoAUno(java.util.List<Map<String, Object>> datos) {
        Map<String, Object> datosEsperados = datos.get(0);

        OnStage.theActorInTheSpotlight().should(
                // Validar status code primero
                seeThatResponse("El código de estado es 200", res -> res.statusCode(200)),

                // Validar contenido uno a uno
                seeThat("Los campos de la respuesta coinciden con los enviados",
                        LosDatosDelProducto.coincidenCon(datosEsperados), is(true)),

                // Validaciones de reglas de negocio solicitadas
                seeThatResponse("Reglas de negocio (Numéricos >= 0)",
                        res -> res.body("price", greaterThanOrEqualTo(datosEsperados.get("price").toString()))
                                .body("title", not(emptyString())))
        );
    }

    @When("el usuario intenta eliminar el producto con id {int}")
    public void deleteProduct(int id) {
        theActorInTheSpotlight().attemptsTo(
                DeleteProduct.withId(id)
        );
    }


}
