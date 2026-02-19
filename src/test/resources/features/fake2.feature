Feature: Gestión de recursos en FakeStore API

  Background:
    Given que el analista accede a la URL base "https://fakestoreapi.com"
@tag
  Scenario Outline: Listar recursos y validar estructura
    When consulta el listado de "<recurso>"
    Then el status code debe ser 200
    And el listado debe ser un array no vacio con estructura correcta para "<recurso>"

    Examples:
      | recurso  |
      | products |
     # | carts    |
      #| users    |

  @tag2
  Scenario Outline: Consultar por id
    When consulta con el id "<recurso>" "<id>"
    Then el status code debe ser 200
    And el listado debe ser un array no vacio con estructura correcta para "<recurso>"
    Examples:
      | recurso  |
      | products |