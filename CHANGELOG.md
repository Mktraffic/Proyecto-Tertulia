# Changelog


## [1.0.0] - 2025-06-03



### ➕ Agregation


- Agregar readme.md (6f9d253)

- Agregar estructura básica/general del modulo de facturacion (22583a0)

- Agregar service de detalle venta (51b7f7a)

- Agregar modulo de factura venta (37a6e49)

- Agregar html factura venta y factura compra (7226e50)

- Agregar las pruebas unitarias a todos los services (41e0567)

- Agregar patron Strategy para loggeo (76b829f)


### 🐛 Bug Fixes


- Corregir Readme.md (b1d4905)

- Corregir base de datos del readme.md (26a3206)

- Corregir Estructura del proyecto Readme.md (fed9ed3)

- Corregir entidad de venta y detalle de venta (b2a1c11)

- Corregir entidad de venta eliminar detalle de venta (b5f641b)

- Corregir service de compra y demas clases relacionadas (18629d5)

- Corregir service de factura compra y demas clases relacionadas (16a8de0)

- Separar venta detalles venta y de finalizar venta (d6e03c3)

- Corregir cambios de leonardo (23f5dac)

- Corregir logica de controller venta (9c5ccfa)

- Corregir importaciones que no se usar (efea32f)

- Corregir postmapping de ventas (205bdc4)

- Registro venta, fallo al hacer una venta con el mismo vendedor (5f777df)

- Corregir postmapping y variable del controller (a18151f)

- Arreglar formurio de registro venta (f8b6bb2)

- Corregir la busqueda por fechas de ventas (81fcd6e)

- Arreglar modulo de compras (a2914d5)

- Correcion prueba unitaria en personaService (e1845af)

- Agregar modificar proveedor y buscar venta (f670c47)

- Corregir nombre de columnas para facturas (5846df3)


### 📚 Documentation


- Agregar Diagrama de clases modulo Compra (ccf11a0)

- Agregar Diagrama de clases Modulo Facturacion (de613d9)


## [0.5.0] - 2025-05-27



### ⚡ Performance


- Optimizar el codigo del update de persona (0df1d2c)

- Retirar Los patrones de diseño sin proposito (d613f19)


### ➕ Agregation


- Agregar tipo de documento, mensajes de exito y fracaso con 3s de duracion (6cac3b1)

- Agregar las clases de producto e inventario al modelo (758135e)

- Agregar las clases de transferencia de datos (7041bad)

- Agregar los mappers y repositories de producto e inventario (950277a)

- Agregar PersonController,ProductController, PersonManage,ProductManager y UserRegistration (b8bd117)

- Agregar clases del modelo, dto y mapper de venta (26a7561)

- Agregar repositorio de ventas (270ae5b)

- Agregar Modal a modificar persona (ca12c98)

- Agregar toast container (dede59b)

- Agregar pagina principal de gestio de productos, busqueda de productos y modal para modificar productos (c87c234)

- Agregar validaciones del backend (0228eca)

- Agregar metodos en service de product y user (7e3de1e)

- Agregar interfaz modulo de ventas (a3c8cd2)

- Agregar decorator para productos (4ebc501)

- Agregar logica del back de el modulo de compra (4988aa2)


### 🐛 Bug Fixes


- Corregir documentación de persona y clases relacionadas (bc5c6db)

- Corregir persona y producto atributos (015d707)

- Corregir id de la persona al hacer el update (b3fb6c3)

- Corregir la mayoria de edad en la persona (1aff363)

- Corregir el tipo de dato y el resgitro de persona (00cb873)

- Corregir el retorno del service de persona (ea55fc3)

- Arreglar registro de usuario persona (258adb3)

- Añadir estado a persona (d52820c)

- Corregir distribucion de ventas (9d87ce0)

- Corregir logica de los services de person y usuario (6f44b43)

- Arreglar el problema de las validaciones en el controlador del sistema y persona (d72de4d)

- Refactorización para userRegistration (767c27c)

- Registro nombre usuario (5e1820c)

- Arreglar Control de gestion de ventas por roles (6446d42)

- Corregir validaciones de usuario para permitir un proveedor (ff27342)

- Corregir update de la persona (5ad9e05)

- Arreglar updatePerson (4bd42a9)

- Arreglar updatePerson v2 (db495ed)

- Corregir update de usuario (8609a38)

- Agregar fragmentos a productos (e43d357)

- Eliminar fragmentos y agregar formulario por producto (42d417a)

- Corregir creacion de producto (a07e64d)

- Corregir el factory del producto (f031307)

- Quitar el patron factory (c2db87b)

- Eliminar formularios de registro de productos, utilizar un solo formulario, funciona modulo productos y personas (55b0c09)

- Arreglar loggeo de usuarios activos (3b96ddc)

- Arreglar salesRedistration (1064422)


### 📚 Documentation


- Agregar diagrama de clases modulo inventario (337c32b)

- Agregar diagrama clases modulo Facturacion (dff74b0)

- Agregar Diagrama de Clases Modulo Venta (8d04dac)


### 🚀 Features


- Agregar servicio productos (bd3d746)

- Agregar validaciones de persona y usuario en el back (642b8a8)

- Agregar funcionalidad de crear una persona (007912f)

- Agregar la implementacion del abstract factory para los productos en el back (0cc432f)


### 🚜 Refactor


- Eliminar el uso de la clae inventario en el modulo de gestion de productos (3866dfc)


## [0.2.0] - 2025-05-06



### ➕ Agregation


- Agregar la estructura basica del proyecto (a1f5f86)

- Agregar conexion a la base de datos (50b9340)

- Agregar la clase Persona del modelo (0339243)

- Agregar la clase Rol del modelo (31c627b)

- Agregar la clase Usuario del modelo (21afb6f)

- Agregar las clases del paquete dto (186e2f6)

- Agregar las clases del paquete mapper (30b3512)

- Agregar repositorios (d022345)

- Agregar service para login (0d2572e)

- Agregar test del login (1495fac)

- Agregar login y estilos (173b688)

- Agregar registro usuarios y estilos para registro usuarios (4c623c2)

- Agregar vista administrador y estilos (6249e26)

- Agregar systemController (a3472eb)


### 🐛 Bug Fixes


- Corregir conexion a la base de datos (1e853ee)

- Modificacion diagrama clases Modulo Login (00dbd83)

- Arreglar mensaje de exito/fracaso (a2e0e31)

- Arreglar navegación login (04c65ee)


### 📚 Documentation


- Diagrama Contexto General y Login (c100ac5)

- Agregar diagrama de Clases Modulo Login (2b5af07)

<!-- generated by git-cliff -->
