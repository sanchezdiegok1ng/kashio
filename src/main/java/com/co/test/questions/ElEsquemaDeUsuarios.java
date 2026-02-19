package com.co.test.questions;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

    public class ElEsquemaDeUsuarios {
        public static Question<Boolean> esValido() {
            return actor -> {
                SerenityRest.then().assertThat()
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user-schema.json"));
                return true;
            };
        }
    }



