# Anotaciones
**resources/database.properties**
   - Se ponen ejemplos de como debería ponerse un usuario la dirección donde suele ejecutarse mysql, y una constraseña aleatoria, son datos de ejemplo.
**1. Manejo de Excepciones**
   -  No son útiles las excepciones para manejar condiciones que pueden ocurrir durante la ejecución del código, debido a que se debe informar al jugador sobre cualquier problema a través de un mensaje de texto utilizando los métodos proporcionados por la API de Bukkit (es obligatorio que sean Strings).

**2. Patrón Observador en Objetivos de Misiones**
   - La aplicación del patrón observador a los objetivos de las misiones presenta limitaciones debido al flujo de datos desde la perspectiva del jugador. Implementarlo implicaría una complejidad logarítmica de O(n^2) en el peor caso, lo cual no es viable debido a restricciones de arquitectura.

**3. Añadir Nuevas Misiones**
   - Para ampliar el proyecto con nuevas misiones, simplemente es necesario agregarlas al archivo de configuración en formato YAML (.yml).

**4. Caché**
   - La implementación del guardado automático de isntancias en caché no es necesario en esta versión Alpha 1.0. Sin embargo, si se desea implementar solo es necesario llamar a los métodos de guardado cada x tiempo proporcionado por los servicios con estado. Las operaciones de guardado en BD no se exponen en el ApiGateway, a no ser que sea un guardado crítico.

**5. Test**
   - Se testea comenzando en la capa de Daos con JDBC, luego los módulos, luego los servicios y finalmente el Apigateway, que debido a ser un sistema embebido, actúa como proxy de peticiones.



# Arquitectura desarrollada.

![archFinal](https://github.com/ramiroserantes/Events/assets/74147489/8486ecb4-8d77-4236-936f-96079c6aaac1)

**CL**
   - Common Library de clases y datos compartidos entre microservicios.

**Factory** 
   - Modulo necesario para añadir funcionalidad escribiendo en ficheros .yml (yaml). Vuelcan el contenido de los ficheros a Mysql según sea necesario.
