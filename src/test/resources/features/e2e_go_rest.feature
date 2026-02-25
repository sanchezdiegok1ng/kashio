
Feature: Gestión de usuarios en GoRest

  @test
  Scenario: Crear un nuevo usuario exitosamente
    Given que el administrador tiene acceso a la API de GoRest
    When envía la información del nuevo usuario

      | name               | gender | email                       | status |
      | Tenali Ramakrishna | male   | correolead@15ce.com | active |
    Then el sistema debería responder con un código de estado 201
    And el cuerpo de la respuesta debería contener el correo
    And Actualiza el correo
    And el sistema debería responder con un código de estado 200
    And el cuerpo de la respuesta debería contener el nuevo correo
    And consulta el usuario por id
    And valida que el correo este actualizado
