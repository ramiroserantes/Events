# Anotaciones

**1. Manejo de Excepciones**
   -  No son útiles las excepciones para manejar condiciones que pueden ocurrir durante la ejecución del código, debido a que se debe informar al jugador sobre cualquier problema a través de un mensaje de texto utilizando los métodos proporcionados por la API de Bukkit.

**2. Patrón Observador en Objetivos de Misiones**
   - La aplicación del patrón observador a los objetivos de las misiones presenta limitaciones debido al flujo de datos desde la perspectiva del jugador. Implementarlo implicaría una complejidad logarítmica de O(n^2) en el peor caso, lo cual no es viable debido a restricciones de arquitectura.

**3. Añadir Nuevas Misiones**
   - Para ampliar el proyecto con nuevas misiones, simplemente es necesario agregarlas al archivo de configuración en formato YAML (.yml).

**4. Logs**
   - Se usa la cadena de texto "DB Error" en caso de que suceda porque el logger indica la clase y línea de código donde sucede en tiempo de ejecución. Se planea cambiar estas cadenas para añadir más información del error.

**5. Test**
   - Se testea comenzando en la capa de Daos con JDBC, luego los módulos, luego los servicios y finalmente el Apigateway, que debido a ser un sistema embebido, actúa como proxy de peticiones.

# Arquitectura desarrollada.

![archFinal](https://github.com/ramiroserantes/Events/assets/74147489/8486ecb4-8d77-4236-936f-96079c6aaac1)

**CL**: Common Library de clases y datos compartidos entre microservicios.
**Factory**: Son necesarias para añadir funcionalidad escribiendo en ficheros .yml (yaml).
