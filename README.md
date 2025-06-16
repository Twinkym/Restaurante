# Proyecto Restaurante v1.0
Crear proyecto de gestión de restaurantes, con entidades.

## PASOS A SEGUIR

1. Crear un repositorio de GitHub por grupo
2. Crear entidades y atributos
3. Repositorios
4. Controladores
5. HTMLs: list, detalle, formulario
6. Asociaciones ManyToOne entre entidades (parte más propensa a problemas)
7. Estilización CSS con Bootstrap CSS
8. Crear logo con canva o un generator de logos

Plantear dividir estas tareas según metodología ágil en una herramienta de gestión de proyectos.

Repositorio:

* Lo crea un usuario del grupo: github.com/user1/grupo1_proyecto_java (público)
* El admin invita como colaboradores a su equipo
* El resto lo clona
* Opción 1: hacer pull y commit y push todos los días/semanas a la rama main
* Opción 2: cada usuario crea una rama para sus desarrollos
* Opción 3: fork

Recomendación: Opción 1 y eventualmente opción 2.

Consejo: todos los días hacer pull y si nuestros cambios están bien hacer un commit y push

Requisitos:

* Cada uno es responsable de hacer pull commit y push de su código funcional
* Actualizar el progreso de las tareas en la herramienta de gestión de proyectos (Trello o Jira o Notion o GitHub Issues)


## PRESENTACIÓN PROYECTOS

02/07/2025

Cada grupo presenta el proyecto en 15 minutos.

* Nombre y logo del proyecto
* Introducción y objetivos y problema que resuelve
* Equipo: presentarse cada uno brevemente pero dando una pincelada de la experiencia o cursos anteriores.
* Tecnologías empleadas: Java 24, IntelliJ IDEA, Spring Boot, HTML, Bootstrap CSS, Thymeleaf, H2 o MySQL, Git y GitHub, Reuniones diarias o semanales, gestión de proyectos con Trello-Jira-Notion
* Mostrar el repositorio de GitHub, no hace falta explicar el código
* Diseños Figma (opcional)
* Demo:
  * Home
  * Listado de Algo (Productos, Tareas, Proyectos, Libros...)
  * Detalle de Algo (Producto, Tarea, Libro...)
  * Formulario de Algo (formulario para crear un producto)
  * Login y Registro
* Conclusiones e ideas futuras
  * Trabajar en equipo de forma colaborativa
  * Aprender Java, Spring Boot, crear aplicación full stack web desde cero
  * Aprender a crear modelos de datos esquemas de base de datos
  * Ideas futuras: capa seguridad, distintos roles de usuario, agregar X funcionalidades, dashboard de compras realizadas
 
  ![captura pagina Home proyecto gestor restaurante](https://github.com/user-attachments/assets/e3af9dc9-aa34-4d6c-829e-be1e9e472edf)

  Aplicación de gestión integral para restaurantes desarrollada con Spring Boot.

  Muestra de la Home con carrusel funcional.
  ![Captura-Carrousel-y-fragments-100%](https://github.com/user-attachments/assets/9760c95d-0422-4693-8dbc-fd37c2f74258)

- La imagen me ha quedado algo rara al cambiar de monitor, si da tiempo la arreglaré.
  Acontinuación deben colocarse las capturas que muestren las diferentes pantallas y su contenido


---
## Descripción General

**RestauranteApp** 
  es un sistema web diseñado para facilitar la administración de un restaurante, 
  cubriendo áreas como la gestión de productos, categorías, pedidos, mesas y clientes. 
  La aplicación está construida siguiendo prácticas modernas de desarrollo de software 
  para garantizar un código limpio, mantenible y escalable.

---
## Tecnologías Utilizadas

* **Lenguaje:** Java 24
* **Framework Principal:** Spring Boot 3.5.0
* **Gestión de Dependencias:** Apache Maven
* **Base de Datos:** MySQL 8.0+
* **Capa de Persistencia:** Spring Data JPA / Hibernate 6
* **Motor de plantillas (Frontend):** Thymeleaf
* **Utilidades:** Lombok

---
## Configuración del Entorno de Desarrollo
Para ejecutar este proyecto en un entorno local, sigue estos pasos:

#### 1. Prerrequisitos
* **JDK 24** O SUPERIOR.
* **Apache Maven** 3.8 o superior.
* Un servidor de **MySQL 8** en ejecución.

#### 2. Configuración de la Base de Datos
El sistema está diseñado para funcionar con **perfiles** de Spring. El perfil por defecto es `dev`.

1. Abre el fichero `src/main/resources/application-dev.properties
2. Modifica las siguientes propiedades para que coincidan con tu configuración local de MySQL.
   * `spring.datasource.url`: Asegúrate de que el nombre de la base de datos y el puerto son correctos. 
   * La base de datos se creará si no existe (`?createDatabaseIfNotExist=true`).
   * `spring.datasource.username`: Tu usuario de MySQL (normalmente `root`).
   * `spring.datasource.password`: Tu contraseña de MySQL.

#### 3. Ejecutar la Aplicación
Puedes ejecutar la aplicación de dos maneras:
* **Desde tu IDE (Intellij/Eclipse):** Ejecuta la clase principal `RestauranteApplication.java`.
* **Desde La línea de comandos:** Navega a la raíz del proyecto y ejecuta el siguiente comando Maven: 
    ```bash
    mvn spring-boot:run
    ```
La aplicación estará disponible en `http://localhost:8080`.

---
## Arquitectura de la Base de Datos

La gestión del esquema y los datos da la base de datos varía según el entorno, controlado por los perfiles de spring.

### Modo de Desarrollo (`dev`)
* **`spring.jpa.hibernate.ddl-auto=create-drop`** Esta configuración asegura que la base de datos se **borra y se recrea completamente** en cada reinicio de la aplicación.
* **Poblado de Datos:** Tras la creación del esquema, el fichero `src/main/resources/data.sql` se ejecuta para poblar las tablas con un conjunto de datos de prueba limpios.
* **Propósito:** Garantizar un entorno predecible y libre de conflictos de datos para agilizar el desarrollo y las pruebas.

### Modo de producción (`prod`)
* **`spring.jpa.hibernate.ddl-auto=validate`**: esta es la configuración de seguridad para producción. 
  Spring **no modifica el esquema**. Simplemente, comprueba si el esquema de la BD es compatible con las entidades JPA. 
  Si no lo es, la aplicación fallará al arrancar para prevenir inconsistencias.
* **Gestión de Cambios:** En un entorno de producción, cualquier cambio de la base de datos (ej. añadir una tabla o columna) 
* debe gestionarse a traves de una herramienta de migración como **Flyway** o **Liquibase**. Esto asegura un control de versiones auditable y seguro de la base de datos.

---
## Estructura del Proyecto
El proyecto sigue la estructura estándar de Maven/Spring Boot:

* `src/main/java/com/grupo4/restaurante`: Código fuente principal de Java.
  * `controllers`: Controladores web (Spring MVC).
  * `entities`: Entidades de la base de datos (JPA).
  * `repositories`: Repositorios de datos (Spring Data JPA).
  * `seeder`: (Opcional) Lógica para el poblado de datos programático.
  * `services`: Lógica de negocio.
* `src/main/resources`: Ficheros de configuración y recursos estáticos.
  * `static`: CSS, JavaScript, imágenes.
  * `templates`: Plantillas HTML de thymeleaf.
  * `applicaiton.propeties`: Ficheros de configuración de Spring.
* `pom.xml`: Fichero de configuración del proyecto Maven. 

