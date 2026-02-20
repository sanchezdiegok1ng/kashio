package com.co.test.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.thucydides.model.util.EnvironmentVariables;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateUser implements Task {
    private final String userId;
    private final Object payload;
    private EnvironmentVariables environmentVariables; // Serenity la inyecta

    public UpdateUser(String userId, Object payload) {
        this.userId = userId;
        this.payload = payload;
    }

    public static UpdateUser withId(String userId, Object payload) {
        return instrumented(UpdateUser.class, userId, payload);
    }

    @Override
    public <T extends net.serenitybdd.screenplay.Actor> void performAs(T actor) {
        String token = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("restapi.auth.token");

        actor.attemptsTo(
                Patch.to("/public/v2/users/{id}")
                        .with(request -> request
                                .pathParam("id", userId)
                                .header("Authorization", "Bearer " + token)
                                .header("Content-Type", "application/json")
                                .body(payload) // Solo enviará lo que esté en el mapa
                        )
        );
    }
}
