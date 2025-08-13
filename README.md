# 📌 Gestor de Empresas - Spring Boot + MySQL + Thymeleaf

Aplicación web para la **gestión de empresas**, que permite registrar, buscar, editar y eliminar información empresarial, incluyendo datos de contacto, dirección y acceso directo a **Google Maps**.

Cuenta con funcionalidades de **búsqueda avanzada**, **exportación a PDF y Excel** y paginación.  
Construida bajo **principios SOLID** y arquitectura **MVC** con tecnologías modernas para asegurar escalabilidad y mantenibilidad.

---

## 🚀 Tecnologías utilizadas
- **Java 17**  
- **Spring Boot 3.5.4** (Web, Data JPA, Validation, Thymeleaf)  
- **MySQL 8**  
- **Flyway** (migraciones de base de datos)  
- **Lombok** (reducción de boilerplate code)  
- **Postman** (pruebas de API REST)  
- **Bootstrap** (estilo de interfaz)  

---

## ✨ Características principales
- CRUD de empresas.
- Integración con Google Maps para mostrar direcciones.
- Búsqueda avanzada por nombre o ciudad.
- Paginación de resultados.
- Exportación de datos a **PDF** y **Excel**.
- Validaciones de datos en backend.

---

## 📦 Instalación y ejecución
1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/gestor-empresas.git

  ## Configura la base de datos en el archivo application.properties:
    spring.datasource.url=jdbc:mysql://localhost:3306/gestor_empresas
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_password

## Ejecuta la aplicación:
    mvn spring-boot:run
    
## Accede desde el navegador:
    http://localhost:8080/empresas

## 🧪 Pruebas de API REST
 Todos los endpoints han sido probados y validados con Postman.
El archivo de colección está disponible en /postman/coleccion.json.
    



   
