package com.co.test.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateProduct implements Task {

    private final int id;
    private final Map<String, Object> datos;

    public UpdateProduct(int id, Map<String, Object> datos) {
        this.id = id;
        this.datos = datos;
    }

    public static UpdateProduct conId(int id, Map<String, Object> datos) {
        return instrumented(UpdateProduct.class, id, datos);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/products/" + id)
                        .with(request -> request
                                .header("Content-Type", "application/json")
                                .body(datos)
                        )
        );

    }
}
