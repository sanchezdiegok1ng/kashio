package com.co.test.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import java.util.Map;

public class LosDatosDelProducto implements Question<Boolean> {
    private final Map<String, Object> datosEsperados;

    public LosDatosDelProducto(Map<String, Object> datosEsperados) {
        this.datosEsperados = datosEsperados;
    }

    public static LosDatosDelProducto coincidenCon(Map<String, Object> datosEsperados) {
        return new LosDatosDelProducto(datosEsperados);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        var respuestaJson = SerenityRest.lastResponse().jsonPath();

        // Compara dinámicamente cada entrada del mapa enviado
        return datosEsperados.entrySet().stream().allMatch(entry -> {
            Object valorReal = respuestaJson.get(entry.getKey());
            // Manejo de conversión de tipos (ej: Float/Double/String)
            return String.valueOf(valorReal).equals(String.valueOf(entry.getValue()));
        });
    }
}