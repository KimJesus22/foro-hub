# ForoHub API

ForoHub es una API RESTful construida con Spring Boot que simula la funcionalidad de un foro de discusión. Permite a los usuarios crear, leer, actualizar y eliminar tópicos, gestionando la autenticación y autorización mediante JSON Web Tokens (JWT).

## ✨ Características

- **Autenticación JWT**: La API utiliza un sistema de autenticación stateless basado en tokens JWT.
- **Operaciones CRUD**: Implementa todas las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los tópicos del foro.
- **Validaciones**: Utiliza `jakarta.validation` para validar los datos de entrada y asegurar la integridad de la información.
- **Seguridad**: Configuración de seguridad robusta con Spring Security para proteger los endpoints.
- **Base de Datos**: Preparado para funcionar con una base de datos relacional (como MySQL o PostgreSQL) a través de Spring Data JPA.

---

## 🚀 Cómo Ejecutar Localmente

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

- **Java Development Kit (JDK)**: Versión 17 o superior.
- **Apache Maven**: Para la gestión de dependencias y la construcción del proyecto.
- **Base de Datos**: Una instancia de una base de datos como MySQL, PostgreSQL, o H2.
- **IDE (Opcional)**: Un IDE como IntelliJ IDEA o Eclipse para facilitar el desarrollo.

### Pasos de Instalación

1.  **Clona el Repositorio**
    ```sh
    git clone https://github.com/tu-usuario/foro-hub.git
    cd foro-hub
    ```

2.  **Configura la Base de Datos**
    - Abre el archivo `src/main/resources/application.properties`.
    - Modifica las siguientes propiedades para apuntar a tu base de datos:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/forohub_db
      spring.datasource.username=tu_usuario_db
      spring.datasource.password=tu_contraseña_db
      ```

3.  **Configura el Secreto del JWT**
    - En el mismo archivo `application.properties`, define un secreto seguro para la firma de los tokens JWT:
      ```properties
      jwt.secret=tu_secreto_super_secreto_aqui
      ```

4.  **Construye y Ejecuta el Proyecto**
    - Utiliza Maven para compilar y empaquetar el proyecto:
      ```sh
      mvn clean package
      ```
    - Ejecuta la aplicación:
      ```sh
      java -jar target/foro-hub-0.0.1-SNAPSHOT.jar
      ```

5.  **¡Listo!**
    - La API estará corriendo en `http://localhost:8080`.

---

## Endpoints de la API

A continuación se detallan los endpoints disponibles en la API.

### Autenticación

#### `POST /auth/login`
- **Descripción**: Autentica a un usuario y devuelve un token JWT si las credenciales son correctas.
- **Autenticación**: No requerida.
- **Cuerpo (Request Body)**:
  ```json
  {
      "login": "usuario@ejemplo.com",
      "password": "tu_contraseña"
  }
  ```
- **Respuesta Exitosa (200 OK)**:
  ```json
  {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```

### Tópicos

#### `GET /topicos`
- **Descripción**: Devuelve una lista de todos los tópicos.
- **Autenticación**: No requerida.
- **Respuesta Exitosa (200 OK)**:
  ```json
  [
      {
          "id": 1,
          "titulo": "Dudas sobre Spring Boot",
          "mensaje": "¿Cómo configuro un proyecto desde cero?",
          "fechaCreacion": "2024-08-21T10:00:00",
          "status": "NO_RESPONDIDO",
          "autor": "Jane Doe",
          "curso": "Spring Boot"
      }
  ]
  ```

#### `GET /topicos/{id}`
- **Descripción**: Obtiene los detalles de un tópico específico por su ID.
- **Autenticación**: Requerida (token JWT).
- **Respuesta Exitosa (200 OK)**:
  ```json
  {
      "id": 1,
      "titulo": "Dudas sobre Spring Boot",
      "mensaje": "¿Cómo configuro un proyecto desde cero?",
      "fechaCreacion": "2024-08-21T10:00:00",
      "status": "NO_RESPONDIDO",
      "autor": "Jane Doe",
      "curso": "Spring Boot"
  }
  ```

#### `POST /topicos`
- **Descripción**: Crea un nuevo tópico.
- **Autenticación**: Requerida (token JWT y rol de `ADMIN`).
- **Cuerpo (Request Body)**:
  ```json
  {
      "titulo": "Nuevo Tópico sobre Java",
      "mensaje": "Este es el contenido del mensaje.",
      "autor": "John Doe",
      "curso": "Java Avanzado"
  }
  ```
- **Respuesta Exitosa (201 Created)**: Devuelve el tópico recién creado.

#### `PUT /topicos/{id}`
- **Descripción**: Actualiza un tópico existente.
- **Autenticación**: Requerida (token JWT).
- **Cuerpo (Request Body)**:
  ```json
  {
      "titulo": "Título Actualizado",
      "mensaje": "Mensaje actualizado."
  }
  ```
- **Respuesta Exitosa (200 OK)**: Devuelve el tópico actualizado.

#### `DELETE /topicos/{id}`
- **Descripción**: Elimina un tópico por su ID.
- **Autenticación**: Requerida (token JWT).
- **Respuesta Exitosa (204 No Content)**.

---

## Páginas Estáticas

- **Landing Page**: Accede a la página de bienvenida en la [raíz (/)](http://localhost:8080/).
- **Visualizador de Tópicos**: Una página estática que consume la API para mostrar los tópicos en [ /topicos.html](http://localhost:8080/topicos.html).
