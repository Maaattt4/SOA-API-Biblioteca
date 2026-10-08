# API REST - Sistema de Biblioteca

## Descripción de la API

El sistema está compuesto por tres APIs principales desarrolladas bajo el estándar REST, encargadas de gestionar la lógica de negocio de una biblioteca:

* **API de Libros (`/libros`):** Permite administrar el catálogo de la biblioteca, registrar nuevos títulos, actualizar sus detalles, eliminar libros y realizar búsquedas filtradas por el autor.
* **API de Usuarios (`/usuarios`):** Administra el padrón de socios. Facilita el registro de nuevas personas guardando sus datos personales (nombre, dirección, correo, teléfono, etc.) y expone endpoints para buscar usuarios por nombre o correo, así como actualizarlos o darlos de baja.
* **API de Préstamos (`/prestamos`):** Controla la relación entre libros y usuarios. Registra préstamos descontando ejemplares del inventario y registra devoluciones sumando de vuelta el ejemplar a los libros disponibles, generando automáticamente las fechas correspondientes.

## Tecnologías utilizadas

* **Lenguaje:** Java
* **Framework:** Spring Boot
* **Persistencia de Datos:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL

## Pasos de instalación y ejecución local

1. Asegúrate de tener instalado Java, un entorno de desarrollo (IDE como Eclipse, IntelliJ o VS Code) y MySQL Server ejecutándose localmente.
2. Crea una base de datos en MySQL llamada `libreria`
3. Clona este repositorio en tu máquina local.
4. Importa el proyecto como un proyecto Maven en tu IDE.
5. Modifica el archivo `src/main/resources/application.properties` con tus credenciales de MySQL local (usuario y contraseña).
6. Ejecuta la clase principal `LibreriaApplication.java`. El servidor iniciará en el puerto 1500 y creará las tablas automáticamente.

## Variables de entorno necesarias

No se requieren variables de entorno externas del sistema operativo, pero es obligatorio configurar el archivo `application.properties` con los siguientes parámetros antes de arrancar la aplicación:

```
spring.application.name=libreria
server.port=1500
spring.datasource.url=jdbc:mysql://localhost:3306/libreria?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

## Listado de endpoints:

**Usuarios (/usuarios)**
• GET /usuarios: Obtiene la lista de todos los usuarios.
• GET /usuarios/{id}: Obtiene los datos de un usuario específico por su ID.
• GET /usuarios/nombre/{nombre}: Busca usuarios por coincidencia de nombre.
• GET /usuarios/correo/{correo}: Busca usuarios por coincidencia de correo electrónico.
• POST /usuarios: Crea un nuevo usuario en el sistema.
• PUT /usuarios/{id}: Actualiza la información de un usuario existente.
• DELETE /usuarios/{id}: Elimina un usuario por su ID.

**Libros (/libros)**
• GET /libros: Obtiene el catálogo completo de libros.
• GET /libros/autor/{autor}: Busca libros filtrando por el nombre del autor.
• POST /libros: Registra un nuevo libro en el inventario.
• PUT /libros/{isbn}: Actualiza la información de un libro por su ISBN.
• DELETE /libros/{isbn}: Elimina un libro del catálogo por su ISBN.

**Préstamos (/prestamos)**
• GET /prestamos: Lista todos los registros de préstamos.
• GET /prestamos/{id}: Obtiene un préstamo específico por su ID.
• POST /prestamos: Registra un nuevo préstamo y descuenta un ejemplar del libro asociado.
• PUT /prestamos/{id}/devolver: Registra la devolución de un préstamo, genera la fecha de entrega y suma un ejemplar al libro.
• DELETE /prestamos/{id}: Elimina el registro de un préstamo del sistema.

## Ejemplos de Peticiones y Respuestas

**1. Registrar un Libro (POST /libros)**
Cuerpo de la Petición (JSON):

```json
{
  "titulo": "Estructuras de Datos en Java",
  "autor": "Mark Allen Weiss",
  "añoPublicacion": "2013",
  "numeroEjemplares": 5
}

```

Respuesta Esperada (JSON):

```json
{
  "isbn": 1,
  "titulo": "Estructuras de Datos en Java",
  "autor": "Mark Allen Weiss",
  "añoPublicacion": "2013",
  "numeroEjemplares": 5
}

```

**2. Registrar un Usuario (POST /usuarios)**
Cuerpo de la Petición (JSON):

```json
{
  "nombre": "Carlos",
  "apellidos": "López",
  "telefono": "2291234567",
  "direccion": "Av. Universidad 123",
  "fechaNacimiento": "2000-05-15",
  "estado": "Activo",
  "correo": "carlos@estudiante.edu",
  "contrasena": "secreta123",
  "rol": "Alumno",
  "fechaCreacion": "2026-10-07"
}

```

Respuesta Esperada (JSON):

```json
{
  "id": 1,
  "nombre": "Carlos",
  "apellidos": "López",
  "telefono": "2291234567",
  "direccion": "Av. Universidad 123",
  "fechaNacimiento": "2000-05-15",
  "estado": "Activo",
  "correo": "carlos@estudiante.edu",
  "contrasena": "secreta123",
  "rol": "Alumno",
  "fechaCreacion": "2026-10-07"
}

```

**3. Registrar un Préstamo (POST /prestamos)**
Cuerpo de la Petición (JSON):

```json
{
  "libro": {
    "isbn": 1
  },
  "socio": {
    "id": 1
  }
}

```

Respuesta Esperada (JSON):

```json
{
  "id": 1,
  "libro": {
    "isbn": 1,
    "titulo": "Estructuras de Datos en Java",
    "autor": "Mark Allen Weiss",
    "añoPublicacion": "2013",
    "numeroEjemplares": 4
  },
  "socio": {
    "id": 1,
    "nombre": "Carlos",
    "apellidos": "López",
    "telefono": "2291234567",
    "direccion": "Av. Universidad 123",
    "fechaNacimiento": "2000-05-15",
    "estado": "Activo",
    "correo": "carlos@estudiante.edu",
    "contrasena": "secreta123",
    "rol": "Alumno",
    "fechaCreacion": "2026-10-07"
  },
  "fechaPrestamo": "2026-10-07T22:30:00",
  "fechaDevolucion": null
}

```




