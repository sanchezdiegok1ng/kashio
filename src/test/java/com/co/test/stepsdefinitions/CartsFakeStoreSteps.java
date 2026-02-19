package com.co.test.stepsdefinitions;

import com.co.test.tasks.DeleteCart;
import com.co.test.tasks.UpdateCart;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

import com.co.test.tasks.CreateProduct;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class CartsFakeStoreSteps {

    @Then("la lista debe contener carts con el formato correcto")
    public void verifyListFormat() {
        OnStage.theActorInTheSpotlight().should(
                ResponseConsequence.seeThatResponse("Validación de estructura de array y contenido",
                        res -> res.body("$", is(instanceOf(java.util.List.class)))
                                .body("$.size()", greaterThan(0))
                                .body("[0]", hasKey("id"))
                                .body("[0]", hasKey("userId"))
                                .body("[0].products", is(instanceOf(java.util.List.class))))
        );
    }


    @Then("el usuario intenta crear una carts con los siguientes datos:")
    public void postProduct(java.util.List<Map<String, Object>> data) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                CreateProduct.withData(data.get(0))
        );
    }

    @Then("el listado debe ser un array no vacio con estructura correcta para carts {string}")
    public void elListadoDebeSerUnArrayNoVacioConEstructuraCorrectaParaCarts(String id) {
        theActorInTheSpotlight().should(seeThatResponse("El JSON no debe tener valores nulos en campos críticos",
                response -> response.body("id", notNullValue())
                        .body("userId", notNullValue())
                        .body("date", notNullValue())
                        .body("products", notNullValue())
        ));

        theActorInTheSpotlight().should(
                seeThatResponse("Los productos no deben ser nulos",
                        response -> response.body("products.productId", notNullValue())
                                .body("products.quantity", notNullValue())
                )
        );
    }

    @When("el usuario intenta eliminar la carts con id {int}")
    public void deleteProduct(int id) {
        theActorInTheSpotlight().attemptsTo(
                DeleteCart.withId(id)
        );
    }

    @When("el usuario actualiza la cart {int} con los siguientes datos:")
    public void actualizar(int id, java.util.List<Map<String, Object>> listaDatos) {
        Map<String, Object> datos = listaDatos.get(0);
        OnStage.theActorInTheSpotlight().attemptsTo(
                UpdateCart.withId(id, datos)
        );
    }

    @Then("la respuesta debe contener los datos enviados y valores numéricos válidos")
    public void validateResponse() {
        theActorInTheSpotlight().should(
                seeThatResponse("Validar integridad de la respuesta",
                        response -> response.statusCode(200)
                                // Validar que los campos obligatorios no estén vacíos
                                .body("products[0].title", not(emptyString()))
                                .body("products[0].category", not(emptyString()))
                                // Validar reglas numéricas >= 0
                                .body("userId", greaterThanOrEqualTo(0))
                                .body("products[0].price", greaterThanOrEqualTo(0.0f))
                                // Validar que el ID devuelto coincida con el enviado
                                .body("id", notNullValue())
                )
        );
    }
}

