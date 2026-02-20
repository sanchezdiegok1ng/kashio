package com.co.test.stepdefinitions;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;

public class UsersFakeStoreSteps {


    @Then("la respuesta debe contener un usuario con username {string}")
    public void verificarUsername(String username) {
        OnStage.theActorInTheSpotlight().should(
                seeThatResponse("Validar username en la lista",
                        res -> res.body("username", hasItem(username)))
        );
    }

    @Then("debería ver que los tipos de datos son correctos según el esquema")
    public void validarTiposDeDatos() {

    }
}

