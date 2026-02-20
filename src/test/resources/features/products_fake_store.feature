
Feature: Gestión de recursos en FakeStore API

  Background:
    Given que el analista accede a la URL base "https://fakestoreapi.com"

  @get @regresion
  Scenario Outline: Listar recursos product y validar estructura
    When consulta el listado de "<recurso>"
    Then el status code debe ser 200
    And el listado debe ser un array no vacio con estructura correcta para "<recurso>"

    Examples:
      | recurso  |
      | products |


  @post @regresion
  Scenario: Crear un nuevo product exitosamente
    When el usuario intenta crear un producto con los siguientes datos:
      | title        | price | description      | image                 | category    |
      | Laptop Gamer | 1500  | High performance | https://i.pravatar.cc | electronics |
    Then el status code debe ser 201
    And la respuesta debe contener los datos enviados y un ID válido

  @get
  Scenario Outline: Consultar product por id
    When consulta con el id "<recurso>" "<id>"
    Then el status code debe ser 200
    And el listado debe ser un array no vacio con estructura correcta para "<recurso>"
    Examples:
      | recurso  | id |
      | products | 1  |


  @put
  Scenario: Actualizar un product existente exitosamente
    When el usuario actualiza el producto 1 con los siguientes datos:

      | title       | price | description         | image                 | category   |
      | Nuevo Reloj | 25.5  | Descripción premium | https://i.pravatar.cc | electronic |
    Then el status code debe ser 200
    And la respuesta debe contener los datos enviados
    Then la respuesta debe ser exitosa y coincidir exactamente con los datos enviados:

      | title       | price | description         | image                 | category   |
      | Nuevo Reloj | 25.5  | Descripción premium | https://i.pravatar.cc | electronic |


    @del
  Scenario: Eliminar un product existente exitosamente
    When el usuario intenta eliminar el producto con id 6
    Then el status code debe ser 200

