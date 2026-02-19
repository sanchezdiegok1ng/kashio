package com.co.test.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteCart implements Task {

    private final int productId;

    public DeleteCart(int productId) {
        this.productId = productId;
    }

    public static DeleteCart withId(int productId) {
        return instrumented(DeleteCart.class, productId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from("/carts/{id}")
                        .with(request -> request.pathParam("id", productId))
        );
    }
}