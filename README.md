# roosevelt-api

¡Bienvenido(a) a **roosevelt-api**! Este proyecto está construido con **Quarkus**, orientado a la gestión de usuarios (administradores, profesores, estudiantes), autenticación y módulos académicos (cursos, horarios, calificaciones). A continuación, encontrarás toda la información para **instalar**, **configurar** y **ejecutar** la aplicación, así como referencias a la **estructura de carpetas** y **endpoints** principales.


## Tabla de Contenidos
1. [Requisitos Previos](#requisitos-previos)
2. [Tecnologías Principales](#tecnologías-principales)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Configuración](#configuración)
5. [Ejecución](#ejecución)
6. [Endpoints Principales](#endpoints-principales)
7. [Importar Datos de Ejemplo](#importar-datos-de-ejemplo)
8. [Contribuir](#contribuir)
9. [Licencia](#licencia)


## Requisitos Previos

- **Java 11+** (Se recomienda Java 17)
- **Maven 3.8+**
- **PostgreSQL** (u otro gestor soportado por Quarkus)  
- **Git** para clonar el repositorio (opcional)


## Tecnologías Principales

- **Quarkus** (Framework principal para microservicios Java)
- **Hibernate ORM + Panache** (Capa de persistencia)
- **Hibernate Validator** (Validaciones de datos)
- **RESTEasy + Jackson** (Endpoints REST y serialización JSON)
- **SmallRye OpenAPI** (Documentación Swagger/OpenAPI)
- **PostgreSQL** (Base de datos)


## Estructura del Proyecto

La estructura recomendada sigue capas separadas para **entities**, **dto**, **services** y **resources**. A modo de ejemplo:

```
roosevelt-api/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── roosevelt
    │   │           ├── entities       # Clases @Entity (User, Curso, etc.)
    │   │           ├── dto            # Data Transfer Objects
    │   │           ├── services       # Lógica de negocio
    │   │           ├── resources      # Endpoints REST
    │   │           └── ...
    │   └── resources
    │       ├── application.properties
    │       └── import.sql
    └── test
        └── java
            └── ...
```


## Configuración

En el archivo **application.properties**, configura los accesos a tu base de datos PostgreSQL:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/roosevelt_db
quarkus.datasource.username=postgres
quarkus.datasource.password=postgres

# Manejo de esquema de DB (para ambiente dev)
quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.sql-load-script=import.sql

# OpenAPI/Swagger
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/swagger-ui
mp.openapi.info.title=Roosevelt API
mp.openapi.info.version=1.0.0
mp.openapi.info.description=Documentación generada automáticamente para la API de Quarkus.
```

**Nota**: Para producción, se recomienda usar herramientas de migración (Flyway o Liquibase) y no `update` en ambiente productivo.


## Ejecución

1. **Clonar el repositorio**:

```bash
git clone https://github.com/tu-usuario/roosevelt-api.git
```

2. **Ingresar al directorio**:

```bash
cd roosevelt-api
```

3. **Compilar y ejecutar en modo dev**:

```shell script
./mvnw quarkus:dev
```

> **_NOTA:_** Quarkus ahora se envía con una interfaz de usuario de desarrollo, que está disponible en modo de desarrollo sólo en <http://localhost:8080/q/dev/>.

4. **Probar la aplicación**:

- Accede a **http://localhost:8080** para ver la aplicación en ejecución.
- Revisa **http://localhost:8080/swagger-ui** para la documentación interactiva.


## Endpoints Principales

A continuación, un resumen de los endpoints más destacados. Para mayor detalle, visita **/swagger-ui** en tiempo de ejecución.

1. **Autenticación**  
   - **POST** `/auth/login`  
     - Request Body: `{ "email": "...", "password": "..." }`  
     - Response Body: `{ "nombre": "...", "rol": "..." }`

2. **Módulo Admin**  
   - **Usuarios**
     - **GET** `/users` → Lista todos los usuarios  
     - **GET** `/users/admins` → Lista solo administradores  
     - **GET** `/users/profesores` → Lista solo profesores
     - **GET** `/users/estudiantes` → Lista solo estudiantes
     - **POST** `/users` → Crea un usuario (Admin/Profesor/Estudiante)  
     - **PUT** `/users/{id}` → Actualiza un usuario  
     - **DELETE** `/users/{id}` → Elimina un usuario

   - **Cursos**
     - **GET** `/cursos` → Lista todos los cursos  
     - **POST** `/cursos` → Crea un curso (asignar grado-sección, horarios, profesores)  
     - **PUT** `/cursos/{id}` → Actualiza un curso  
     - **DELETE** `/cursos/{id}` → Elimina un curso  

   - **Grados y secciones**
     - **GET** `/grados-secciones` → Retorna la lista de grados-secciones en formato:  
     ```json
     [
       {
         "id": 1,
         "gradoSeccion": "1er Año A"
       },
       ...
     ]
     ```

4. **Módulo Profesor**  
   - **GET** `/profesor/{profesorId}/cursos` → Cursos asignados a un profesor  
   - **GET** `/profesor/{profesorId}/cursos/{cursoId}/estudiantes` → Estudiantes en ese curso  
   - **POST** `/profesor/{profesorId}/cursos/{cursoId}/estudiantes/{estudianteId}/nota` → Registrar/editar nota

5. **Módulo Estudiante**  
   - **GET** `/estudiante/{estudianteId}/cursos` → Cursos de un estudiante  
   - **GET** `/estudiante/{estudianteId}/horarios` → Horarios semanales agrupados por día


## Importar Datos de Ejemplo

El proyecto incluye un archivo **import.sql** con datos iniciales para tablas como `users`, `cursos`, `grado_seccion`, etc. Para usarlo:

1. Asegúrate de tener `quarkus.hibernate-orm.database.generation=update` (o `create`).  
2. Al iniciar la aplicación, Quarkus ejecutará automáticamente **import.sql**, insertando registros de ejemplo (usuarios, cursos, horarios, etc.).

Ejemplo de entradas en `import.sql`:

```sql
INSERT INTO grado_seccion (id, grado, seccion) VALUES (1, '1er Año', 'A');
INSERT INTO users (id, nombre, email, password, rol) VALUES (100, 'Administrador General', 'admin@demo.com', 'admin123', 'ADMIN');
-- etc.
```


## Contribuir

¡Las contribuciones son bienvenidas! Para proponer cambios:

1. Haz un **fork** del repositorio.  
2. Crea una rama (`feature/nueva-funcionalidad`).  
3. Envía un **Pull Request** con la descripción detallada de tu aporte.  


## Licencia

Este proyecto se distribuye bajo la [MIT License](https://opensource.org/licenses/MIT). Puedes usarlo libremente para fines personales o comerciales, siempre y cuando se respeten los términos de la licencia.  


**¡Gracias por usar roosevelt-api!** Si tienes dudas o sugerencias, no dudes en abrir un **issue** o contactarnos.  