# kashio
repositorio para Prueba técnica QA Automation Lead 

Autor: Diego Fernando Sanchez Rios

Preguntas obligatorias para responder en README (Parte 1)
1. ¿Qué aspectos mejorarías en tu solución si tuvieras más tiempo?
2. ¿Cómo parametrizarías el test para ejecutarlo en distintos ambientes?
3. Si manejaras datos sensibles para pruebas, ¿cómo lo gestionarías?
4. ¿Qué estrategia usarías para evitar flakiness y hacer los tests confiables?
5. ¿Cómo estructurarías la suite para separar smoke vs regression y acelerar feedback

Respuestas:

1. Mayor estructuracion del proyecto, Aumentar aprovechamiento de clases y reutilizacion de pasos
2. Desde el archivo Sernity.conf en la sesion de environments definiria las url de los ambientes.
3. Por medio de variables de entorno en un archivo .env y en Git Configurar los datos como Secrets
4. Inicialmente Trabajar con mooks, Limpieza de datos y esperas flexibles
5. Ejecucion en paralelo uso de tags @smokeTest @regression

