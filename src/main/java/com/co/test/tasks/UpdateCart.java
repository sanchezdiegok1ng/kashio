package com.co.test.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import java.util.Map;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateCart implements Task {

    private final int cartId;
    private final Map<String, Object> cartData;

    public UpdateCart(int cartId, Map<String, Object> cartData) {
        this.cartId = cartId;
        this.cartData = cartData;
    }

    public static UpdateCart withId(int cartId, Map<String, Object> data) {
        return instrumented(UpdateCart.class, cartId, data);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Construcción del Body según el formato requerido
        Map<String, Object> body = Map.of(
                "id", cartId,
                "userId", Integer.parseInt(cartData.get("userId").toString()),
                "products", List.of(Map.of(
                        "id", Integer.parseInt(cartData.get("productId").toString()),
                        "title", cartData.get("title"),
                        "price", Double.parseDouble(cartData.get("price").toString()),
                        "description", cartData.get("description"),
                        "category", cartData.get("category"),
                        "image", cartData.get("image")
                ))
        );

        actor.attemptsTo(
                Put.to("/carts/" + cartId)
                        .with(request -> request
                                .header("Content-Type", "application/json")
                                .body(body)
                        )
        );
    }
}