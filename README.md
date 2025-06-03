# 🍷 La Tertulia - Sistema de Gestión de Licorera

Bienvenido al repositorio del **Sistema de Gestión de Licorera "La Tertulia"**.  
Este proyecto es una aplicación web desarrollada en Java con Spring Boot, que permite gestionar ventas, compras, productos, usuarios y facturación para una licorera.

## 📋 Tabla de Contenidos

- [Características](#✨-características)
- [Tecnologías](#🛠️-tecnologías)
- [Instalación](#🚀-instalación)
- [Estructura del Proyecto](#🗂️-estructura-del-proyecto)
- [Guía de Uso](#📖-guía-de-uso)
- [Autores](#👥-autores)
- [Licencia](#📝-licencia)

## ✨ Características

- Gestión de productos (CRUD)
- Registro y gestión de ventas y compras
- Facturación automática con cálculo de IVA
- Gestión de usuarios y roles (administrador, vendedor)
- Búsqueda de ventas por rango de fechas
- Interfaz intuitiva y responsiva
- Reportes de ventas y compras

## 🛠️ Tecnologías

- **Backend:** Java 17, Spring Boot, Spring Data JPA
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Base de datos:** PostgreSQL
- **Dependencias:** Lombok, Spring Web
- **Build:** Maven

## 🚀 Instalación

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/La_Tertulia.git
2. Configura la base de datos:
   - Crea una base de datos PostgreSQL llamada la_tertulia.
   - Actualiza las credenciales en src/main/resources/application.properties.
3. Compila y ejecuta el proyecto:
   ```bash
   mvn clean install
   ```
   ```bash
   mvn spring-boot:run
   ```
5. Accede a la aplicación:
   - Abre tu navegador en http://localhost:8080
## 🗂️ Estructura del Proyecto
![image](https://github.com/user-attachments/assets/89524696-c749-4d4f-b3a8-9f5fe6e088ba)

##📖 Guía de Uso
1. Inicia sesión como administrador o vendedor.
2. Gestiona productos: Agrega, edita o elimina productos.
3. Registra ventas y compras: Añade detalles, selecciona productos y cantidades.
4. Consulta ventas: Filtra por fechas y genera facturas.
5. Descarga o imprime facturas desde la sección correspondiente.

##👥 Autores

David Lotero - @mktraffic

Leonardo Avila - @LeonardoAvila18

Gabriel Niño - @GabrielNinoA

Luis Hernandez - @luisDeveloper2002

Oscar Rojas - @rojasoscar752

¡Gracias por usar La Tertulia!
Si tienes sugerencias o encuentras algún bug, no dudes en abrir un issue o contribuir al proyecto.
