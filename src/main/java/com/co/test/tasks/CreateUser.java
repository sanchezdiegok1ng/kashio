package com.co.test.tasks;

import com.co.test.models.UserRequest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateUser implements Task {
    private final UserRequest user;
    private EnvironmentVariables environmentVariables;
    public CreateUser(UserRequest user) { this.user = user; }


    public static CreateUser withData(UserRequest user) {
        return instrumented(CreateUser.class, user);
    }

    @Override
    public <T extends net.serenitybdd.screenplay.Actor> void performAs(T actor) {

        //String token = EnvironmentSpecificConfiguration.from(environmentVariables)                .getProperty("restapi.auth.token");


        String token = "64f0ece6412696d6e11a5c4f57eda9efc31b165d6e0e605fa22202d3aa9e848b";
                //System.getProperty("GOREST_TOKEN");
        actor.attemptsTo(
                Post.to("/public/v2/users")
                        .with(request -> request
                                .header("User-Agent", "PostmanRuntime/7.28.4")
                                .header("Authorization", "Bearer "+ token  )
                                .header("Content-Type", "application/json")
                                .body(user))
        );
    }
}
