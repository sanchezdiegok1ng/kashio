# kashio
Repositorio para Prueba técnica QA Automation Lead 

Autor: Diego Fernando Sanchez Rios

Preguntas obligatorias para responder en README (Parte 1)
1. ¿Qué aspectos mejorarías en tu solución si tuvieras más tiempo?
    Mayor estructuracion del proyecto, Aumentar aprovechamiento de clases y reutilizacion de pasos

2. ¿Cómo parametrizarías el test para ejecutarlo en distintos ambientes?
     Desde el archivo Sernity.conf en la sesion de environments definiria las url de los ambientes.

3. Si manejaras datos sensibles para pruebas, ¿cómo lo gestionarías?
    Por medio de variables de entorno en un archivo .env y en Git Configurar los datos como Secrets
4. ¿Qué estrategia usarías para evitar flakiness y hacer los tests confiables?
    Inicialmente Trabajar con mooks, Limpieza de datos y esperas flexibles

5. ¿Cómo estructurarías la suite para separar smoke vs regression y acelerar feedback?
     Ejecucion en paralelo uso de tags @smokeTest @regression

Preguntas para incluir (evaluación CI/CD) en README
6. ¿Cómo harías para ejecutar solo Smoke en PR y Regression nightly?
    R//: Se debe crear dos yml 
            run: ./gradlew test aggregate -Dcucumber.filter.tags="@regression"
            run: ./gradlew test aggregate -Dcucumber.filter.tags="@smokeTest"

7. ¿Cómo manejarías variables sensibles (token GoRest) sin exponerlas en el repo?
    R//: la mejor práctica es utilizar GitHub Secrets en combinación con variables de entorno en Java para local

8. ¿Qué harías si el pipeline falla por intermitencia del API externo?
    R//: Configurar reintentos automáticos,Realiza mocks

9. ¿Cómo versionarías y almacenarías evidencias (reportes, logs) para auditoría?
    R//: Configuar git Actions para que almacene el reporte de la ejecucion hasta por 6 meses.

10. ¿Qué métricas incorporarías (tiempo, estabilidad, flakiness rate) y dónde las 
verías?
    R//: Las métricas esenciales son confiabilidad, eficiencia y el Pass/Fail  por funcionalidad. Estas se visualizan a nivel operativo en los reportes nativos de Serenity BDD y, a nivel estratégico, mediante la integración de archivos JSON/XML en GitHub Actions o plataformas como ReportPortal. Es crucial monitorear la duración de cada Task para identificar cuellos de botella en los servicios y categorizar errores (infraestructura vs. aplicación) para optimizar el mantenimiento del pipeline.

PARTE 3 — Liderazgo (Se deben responder en un README)

1. Calidad vs Velocidad
Producto quiere salir hoy, pero QA detecta riesgos. ¿Cómo negocias, qué información 
llevas y cómo decides el plan de salida?

R//: La informacion que llevaria seria los tipos de riegos, la severidad el impacto en la seguridad y el usuario final; Alternativas tecnicas que disminuyan el impacto
Despues de explicar claramente lo qué puede pasar si se libera con los riesgos actuales y se consideran:

    * Riesgos críticos: se recomienda retrasar la salida, aunque haya presión.

    * Riesgos moderados o bajos: se puede negociar salida con mitigaciones, siempre que haya un plan de contingencia claro.

    * Plan de accion

2. Mentoría técnica
Un QA automation mid implementa tests duplicados y frágiles. ¿Cómo lo guías para 
mejorar arquitectura, y cómo verificas que aprendió?

R//: Lo primero que se debe hacer es que el mismo identifique que posibles fallas se puedan tener a futuro, luego reforzar muy bien temas de principios SOLID, aprovechamiento de las herramientas que nos ofrece screenplay como Records y Sealed Classes, realizar ejercicios practicos donde se evididencie la reutilizacion de codigo y no duplicidad. finalmente se debe realizar analisis de codigo y revision mas implicita a los pull request 

3. Conflicto con Desarrollo Dev dice “es problema del test, no del código”. Tú crees que es bug real. ¿Cómo manejas el conflicto y llegas a una resolución objetiva?

R//: Para resolver el conflicto de forma objetiva, aplicaría un enfoque de triangulación de calidad. Primero, realizaría un aislamiento del fallo para descartar errores de entorno y documentar los pasos exactos de reproducción, asegurando que el reporte sea indiscutible. Segundo, contrastaría el comportamiento con los criterios de aceptación y la documentación de negocio para validar la expectativa funcional. Si la discrepancia persiste, propondría una sesión de debugging conjunto con el desarrollador; esto elimina la barrera defensiva y permite identificar si el origen es la lógica del código o el diseño del test, priorizando siempre la integridad del producto sobre la razón individual.

4. Diseño de estrategia de pruebas  Llegas a un equipo con poca automatización y releases frecuentes. ¿Qué plan propones para ayudarlos a ser más agiles?

R//:
propongo una estrategia basada en el modelo Shift-Left Testing, integrando la calidad desde las etapas tempranas del desarrollo. El plan inicia con la identificación de flujos críticos de negocio para implementar una Suite de Humo automatizada, generando valor inmediato. Es vital adoptar la Pirámide de Automatización, priorizando pruebas unitarias y de API por sobre las de interfaz, debido a su mayor estabilidad y rapidez. Finalmente, estas pruebas deben integrarse en el pipeline de CI/CD para ofrecer feedback instantáneo.

5. Gestión de equipo y performance Tienes un QA con bajo rendimiento y otro sobresaliente que tiene sobrecarga. ¿Cómo 
balanceas carga, motivación, expectativas y feedback.

R//:
Para balancear el equipo, aplicaria tres estrategias:
Proteger al sobresaliente: Reducir su carga operativa y conviértelo en mentor. Esto aprovecha su conocimiento sin agotarlo.
Exigir al de bajo rendimiento: Implementar un Plan de Mejora con metas medibles a corto plazo. Atacar brechas técnicas con capacitación o falta de actitud con consecuencias claras.
Sinceridad y expectativas: Brindar feedback directo. Ofrecer autonomía y reconocimiento al perfil alto, e inyectar un sentido de urgencia al perfil bajo.
El objetivo es que la eficiencia no sea "castigada" con más trabajo.