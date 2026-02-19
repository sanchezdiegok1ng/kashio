package com.co.test.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateCarts implements Task {
    private final Map<String, Object> productData;

    public CreateCarts(Map<String, Object> productData) {
        this.productData = productData;
    }

    public static CreateCarts withData(Map<String, Object> data) {
        return instrumented(CreateCarts.class, data);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/carts")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .body(productData)
                        )
        );
    }
}