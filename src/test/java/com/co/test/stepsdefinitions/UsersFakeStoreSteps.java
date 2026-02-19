package com.co.test.stepsdefinitions;

import com.co.test.questions.ElEsquemaDeUsuarios;
import com.co.test.tasks.CreateProduct;
import com.co.test.tasks.DeleteCart;
import com.co.test.tasks.UpdateCart;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

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
        OnStage.theActorInTheSpotlight().should(
                seeThatResponse("El status code es 200", r -> r.statusCode(200)),
                seeThat("El esquema de respuesta es correcto", ElEsquemaDeUsuarios.esValido())
        );
    }
}

