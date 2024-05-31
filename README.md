#Anotaciones

(1) No son útiles las excepciones para manejar condiciones que pueden ocurrir durante la ejecución del código, debido a que se debe informar al jugador sobre cualquier problema a través de un mensaje de texto utilizando los métodos proporcionados por la API de Bukkit.
(2) No es posible aplicar el patrón observador a los objetivos de las misiones debido al flujo de datos desde la perspectiva del jugador. Esto implicaría una complejidad logarítmica de O(n^2) en el peor caso, lo cual no es viable debido a restricciones de arquitectura.
(3) Para añadir nuevas misiones al proyecto, simplemente es necesario agregarlas al archivo de configuración en formato YAML (.yml). 
