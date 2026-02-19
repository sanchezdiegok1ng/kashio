Feature: Gestión de recursos en FakeStore API

  Background:
    Given que el analista accede a la URL base "https://fakestoreapi.com"

  @test
  Scenario Outline: Listar recursos  de users y validar estructura
    When consulta el listado de "<recurso>"
    Then el status code debe ser 200
    And  debería ver que los tipos de datos son correctos según el esquema
    Examples:
      | recurso |
      | users   |

  @post
  Scenario: Crear un nuevo carts exitosamente
    When el usuario intenta crear una carts con los siguientes datos:

      | userId | productId | title    | price | description | category    | image          |
      | 5      | 1         | Laptop G | 999.9 | High tech   | electronics | http://img.com |
    Then el status code debe ser 201



  @get
  Scenario Outline: Consultar carts por id
    When consulta con el id "<recurso>" "<id>"
    Then el status code debe ser 200
    And el listado debe ser un array no vacio con estructura correcta para carts "<id>"
    Examples:
      | recurso  | id |
      | carts | 1  |

@test
  Scenario: Actualización exitosa de un carrito existente
    When el usuario actualiza la cart 1 con los siguientes datos:

      | userId | productId | title         | price | description    | category   | image              |
      | 3      | 1         | Laptop Gamer | 1200.5| High endurance | electronics| http://img.com |

  Then el status code debe ser 200
  And la respuesta debe contener los datos enviados y valores numéricos válidos
  @del
  Scenario: Eliminar una carts existente exitosamente
    When el usuario intenta eliminar la carts con id 5
    Then el status code debe ser 200

