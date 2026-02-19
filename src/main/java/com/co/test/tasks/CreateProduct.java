package com.co.test.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateProduct implements Task {
    private final Map<String, Object> productData;

    public CreateProduct(Map<String, Object> productData) {
        this.productData = productData;
    }

    public static CreateProduct withData(Map<String, Object> data) {
        return instrumented(CreateProduct.class, data);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/products")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .body(productData)
                        )
        );
    }
}