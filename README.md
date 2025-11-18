# 🎯 **Sistema de Gestión de Empleados & Legajos**
# 🏫 *UTN — Tecnicatura Universitaria en Programación*
# Programación II — Trabajo Práctico Integrador (TPI)

**GRUPO 9**  
🧑‍💻 *Masseroni Ayelen*  
🧑‍💻 *Nicolas Demiryi*  
🧑‍💻 *German Dagatti*  
🧑‍💻 *Gabriel Torres*

---

# 🌐 **Descripción del Sistema**
Este proyecto implementa un Sistema de Gestión de Empleados y Legajos.  
El objetivo principal es administrar la información personal y administrativa de los empleados, aplicando:

✔ POO  
✔ DAO  
✔ Services  
✔ Transacciones  
✔ Validaciones  
✔ Persistencia en MySQL

---

# 🧱 **Modelo del Dominio**

## 🧑 **Empleado (A)**
Representa a un trabajador de la empresa. Contiene datos personales como nombre, apellido, DNI, email, área de trabajo, fecha de ingreso, etc.  
Cada empleado posee **un Legajo asociado**.

## 📁 **Legajo (B)**
Documentación administrativa del empleado: número de legajo, categoría, estado (ACTIVO/INACTIVO), fecha de alta y observaciones.  
Tiene una relación **1 → 1** con el empleado.

---

# 🗂 **Arquitectura del Proyecto**

```
src/
 ├── Dao/
 │     ├── EmpleadoDao.java
 │     ├── LegajoDao.java
 │     └── GenericDao.java
 │
 ├── Entities/
 │     ├── Empleado.java
 │     ├── Legajo.java
 │     └── EstadoLegajo.java
 │
 ├── Service/
 │     ├── EmpleadoService.java
 │     ├── LegajoService.java
 │     └── GenericService.java
 │
 ├── config/
 │     ├── DatabaseConnection.java
 │     └── db.properties
 │
 ├── main/
 │     ├── Main.java
 │     └── AppMenu.java
 │
 └── sql/
       ├── estructura.sql
       └── datos.sql (opcional)
```

---

# 🧰 **Requisitos Técnicos**

## ✔ Lenguaje
- Java 17+ (probado con JDK 24)

## ✔ Base de Datos
- MySQL 8+

## ✔ Dependencias
- JDBC Driver mysql-connector-j-8.x.x.jar

## ✔ IDE
- NetBeans 17 / 18 / 27  
  (Proyecto Java con Ant)

---

# 🗄 **Creación de la Base de Datos**

Este proyecto incluye un archivo SQL listo para ejecutar:

📄 `sql/estructura.sql`

### ▶ Para crearlo:
Abrir MySQL Workbench o consola y ejecutar:

```
SOURCE ruta/a/estructura.sql;
```

Esto creará:  
- **BD empresa**  
- Tablas `empleado` y `legajo`  
- PK, FK, UNIQUE, NOT NULL y restricciones varias

---

# ⚙ **Configuración de Conexión**

Archivo: `config/db.properties`

```
url=jdbc:mysql://localhost:3306/empresa?useSSL=false&serverTimezone=UTC
user=root
password=
```

📌 **Modificar user/password según tu entorno local.**

---

# ▶ **Cómo Ejecutar el Proyecto**

✔ **1) Importar en NetBeans**  
File → Open Project  

✔ **2) Añadir driver MySQL**  
Right click → Properties → Libraries → Add JAR  

✔ **3) Ejecutar programa**  
Abrir: `main/Main.java`  
Ejecutar con **Shift + F6**

---

# 🧪 **Flujo de Uso en la Aplicación**

### 🟦 MENÚ PRINCIPAL
1️⃣ Gestión de Empleados  
2️⃣ Gestión de Legajos  
3️⃣ Búsqueda por DNI  
0️⃣ Salir  

---

# 🟢 **Caso Principal: Crear Empleado + Legajo**
✔ Validación de DNI único  
✔ Email con formato válido  
✔ Categoría y estado del legajo  
✔ Si todo es válido → **transacción exitosa**  
✔ Si algo falla → **rollback automático**  

---

# 🟡 **Otros casos a probar**
- Crear empleado con DNI repetido → ❌ error + rollback  
- Listar empleados → incluye baja lógica  
- Actualizar datos  
- Baja lógica de empleado  
- Crear legajo independiente  
- Búsqueda por DNI  

---

# 🎥 **Video Explicativo**
👉 
