package com.co.test.stepdefinitions;

import com.co.test.models.UserRequest;
import com.co.test.tasks.CreateUser;
import com.co.test.tasks.UpdateUser;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.model.util.EnvironmentVariables;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


public class UserStepDefinitions {
    @Before
    public void setTheStage2() {
        OnStage.setTheStage(Cast.ofStandardActors());
        OnStage.theActorCalled("lead");
    }

    private EnvironmentVariables environmentVariables;



    @Given("que el administrador tiene acceso a la API de GoRest")
    public void setup() {
        OnStage.theActorCalled("Admin")
                .whoCan(CallAnApi.at("https://gorest.co.in"));
    }

    @When("envía la información del nuevo usuario")
    public void sendUserInfo(DataTable dataTable) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String fechaComoString = ahora.format(formato);
        var data = dataTable.asMaps().get(0);
        String correo = "correolead" + fechaComoString + "@google.com";

        OnStage.theActorInTheSpotlight().remember("correo", correo);
        UserRequest user = new UserRequest(
                data.get("name"), data.get("gender"),
                correo, data.get("status")
        );
        OnStage.theActorInTheSpotlight().attemptsTo(CreateUser.withData(user));
    }

    @Then("el sistema debería responder con un código de estado {int}")
    public void verifyStatusCode(int code) {
        lastResponse().then().statusCode(code);
    }

    @And("el cuerpo de la respuesta debería contener el correo")
    public void verifyEmail() {
        String email = OnStage.theActorInTheSpotlight().recall("correo");
        lastResponse().then().body("email", equalTo(email));
        lastResponse().then().body("id", notNullValue());
        String idGenerado = SerenityRest.lastResponse().path("id").toString();
        OnStage.theActorInTheSpotlight().remember("idUsuario", idGenerado);
    }

    @When("Actualiza el correo")
    public void updateOnlyEmail() {
        String id = OnStage.theActorInTheSpotlight().recall("idUsuario");
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String fechaComoString = ahora.format(formato);
        String correo = "nuevocorreolead" + fechaComoString + "@google.com";
        OnStage.theActorInTheSpotlight().remember("nuevoCorreo", correo);
        Map<String, String> partialBody = Map.of("email", correo);
        OnStage.theActorInTheSpotlight().attemptsTo(
                UpdateUser.withId(id, partialBody)
        );
    }

    @And("el cuerpo de la respuesta debería contener el nuevo correo")
    public void verifyNewEmail() {
        String email = OnStage.theActorInTheSpotlight().recall("nuevoCorreo");
        lastResponse().then().body("email", equalTo(email));
        lastResponse().then().body("id", notNullValue());
    }

    @And("consulta el usuario por id")
    public void getUser() {

        String id = OnStage.theActorInTheSpotlight().recall("idUsuario");
        String token = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("restapi.auth.token");
        theActorInTheSpotlight().attemptsTo(
                Get.resource("/public/v2/users/{id}")
                        .with(request -> request
                                .header("Authorization", "Bearer " + token)
                                .pathParam("id", id)
                        )

        );


    }

    @And("valida que el correo este actualizado")
    public void emailUpdate() {
        String email = OnStage.theActorInTheSpotlight().recall("nuevoCorreo");
        theActorInTheSpotlight().should(
                seeThatResponse("El status debe ser 200",
                        response -> response.statusCode(200)
                                .body("email", equalTo(email))));

    }
}

