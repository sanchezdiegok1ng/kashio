package com.co.test.stepsdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Ensure;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.StringContains.containsString;

public class FakeStoreSteps {

    /**
    @When("el usuario solicita la lista de {string}")
    public void listarRecursos(String recurso) {
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/" + recurso)
        );
    }
    @Given("que el {string} accede a la URL base {string}")
    public void setupActor(Actor actor, String baseUrl) {
        actor.can(CallAnApi.at(baseUrl));
    }

    @When("consulta el listado de {string}")
    public void getResourceList(String resource) {
        theActorInTheSpotlight().attemptsTo(Get.resource("/" + resource));
    }


    @Then("el status code debe ser {int}")
    public void verifyStatusCode(int code) {
        theActorInTheSpotlight().should(ResponseConsequence.seeThatResponse(r -> r.statusCode(code)));
    }


    @And("el listado debe ser un array no vacio con estructura correcta para {string}")
    public void validateListStructure(String resource) {
        theActorInTheSpotlight().should(ResponseConsequence.seeThatResponse(r -> r
                .body("$", is(instanceOf(java.util.List.class)))
                .body("$.size()", greaterThan(0))
        ));
    }
**/

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
        theActorInTheSpotlight().should(ResponseConsequence.seeThatResponse(r -> r.statusCode(code)));

    }
    @Then("el listado debe ser un array no vacio con estructura correcta para {string}")
    public void elListadoDebeSerUnArrayNoVacioConEstructuraCorrectaPara(String string) {
        theActorInTheSpotlight().should(ResponseConsequence.seeThatResponse(r -> r
                .body("$", is(instanceOf(java.util.List.class)))
                .body("$.size()", greaterThan(0))
        ));
    }

    @When("consulta con el id {string} {string}")
    public void consultaConElId(String string, String string2) {

        theActorInTheSpotlight().attemptsTo(
                Get.resource("/products/{id}")
                        .with(request -> request.pathParam("id", 1))
        );

        theActorInTheSpotlight().attemptsTo(
                Ensure.that("Status es 200", response -> response.statusCode(200)),
                Ensure.that("El ID coincide", response -> response.body("id", equalTo(Integer.parseInt("1")))),
                // Validación de campos clave (Products)
                Ensure.that("Campos clave presentes", response -> response
                        .body("title", not(emptyString()))
                        .body("image", containsString("http")))
        );
    }
}