# 🎓 uned-00886-tcg

> Sistema de anotación, documentación y gestión de grabaciones de audio para audiencias institucionales.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge\&logo=gradle\&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge\&logo=sqlite\&logoColor=white)

---

## 📖 Descripción

Este repositorio contiene el proyecto desarrollado como **Trabajo Final de Graduación Universitaria (TCG)** para el **Ministerio de Trabajo y Seguridad Social (MTSS) de Costa Rica**.

La solución fue concebida, diseñada e implementada para apoyar el proceso de **documentación y gestión de audiencias**, permitiendo trabajar con **grabaciones de audio de varias horas de duración**, facilitando la anotación de eventos relevantes y la organización de información asociada a cada audiencia.

El objetivo principal fue proporcionar una herramienta especializada para optimizar procesos administrativos que anteriormente dependían de procedimientos manuales.

---

## ✨ Características principales

* 🎙️ Gestión de grabaciones de audio de larga duración.
* 📝 Sistema de anotaciones y documentación de comparecencias.
* 📂 Organización estructurada de comparecencias.
* 💾 Persistencia de datos mediante SQLite.
* 🖥️ Aplicación de escritorio desarrollada en Java.
* 📦 Empaquetado para distribución en Windows.
* 📚 Manual de usuario incluido.
* 🔧 Instalacion automatizada mediante Inno Setup.

---

## 🏗️ Arquitectura del Proyecto

Las carpetas más importantes del repositorio son:

```text
uned-00886-tcg/
│
├── MTSS-Audiencias-AppCliente/app
│   └── Aplicación principal desarrollada en Java
│
├── MTSS-Audiencias-BaseDeDatos/SQLite
│   └── Scripts y recursos relacionados con SQLite
│
├── MTSS-Audiencias-AppCliente/resources/docs/Manual de Usuario.pdf
│   └── Manuales y recursos de apoyo
│
└── MTSS-Audiencias-AppCliente/resources/installer/installer-script.iss
    └── Configuración de Inno Setup
```

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Propósito                               |
| ---------- | --------------------------------------- |
| Java       | Desarrollo de la aplicación             |
| NetBeans   | Entorno de desarrollo (IDE)             |
| Gradle     | Gestión de dependencias y compilación   |
| SQLite     | Base de datos embebida                  |
| Inno Setup | Generación de instaladores para Windows |

---

## 🚀 Compilación del Proyecto

### Requisitos

* Java 17 JDK
* NetBeans
* Gradle

### Compilar desde línea de comandos

```bash
gradle build
```

o

```bash
./gradlew build
```

Los artefactos generados estarán disponibles dentro del directorio:

```text
build/
```

---

## 💽 Base de Datos

El proyecto utiliza **SQLite** como motor de almacenamiento local.

Las estructuras y recursos relacionados con la base de datos se encuentran en:

```text
MTSS-Audiencias-BaseDeDatos/
```

Puede utilizar el software 

---

## 📦 Generación del Instalador

El repositorio incluye un script de configuración para **Inno Setup**, permitiendo generar un instalador para Windows.

### Pasos generales

1. Compilar la aplicación.
2. Generar los artefactos necesarios.
3. Abrir el script `.iss` en Inno Setup.
4. Ejecutar la compilación del instalador.

El resultado será un instalador ejecutable listo para distribución.

---

## 🖥️ Generación del Ejecutable (.exe)

Para lograr este objetivo, se ha utlizado el software launch4j.

Proceso general:

```text
1. Compilar proyecto Java
2. Generar artefactos de distribución
3. Ejecutar el comando de Gradle launch4j.
```

---

## 📚 Documentación

El repositorio incluye:

* Manual de usuario

---

## 🎯 Logros del Proyecto

Este proyecto permitió aplicar conocimientos de:

* Ingeniería de Software
* Análisis y Diseño de Sistemas
* Arquitectura de Aplicaciones de Escritorio
* Bases de Datos Relacionales
* Gestión de Requerimientos
* Experiencia de Usuario (UX)
* Automatización de despliegues

Además, representó una colaboración con una institución pública de alcance nacional, generando una solución enfocada en necesidades reales de operación.

---

## 👨‍💻 Autores
Este proyecto fue desarrollado por Dayron Antony Chaves Sandoval y Robert Jesús Cascante Araya.
Desarrollado como parte del **Trabajo Final de Graduación Universitaria (TCG)** para el **Ministerio de Trabajo y Seguridad Social (MTSS)**.

---

## 📄 Licencia

Este repositorio se comparte con fines académicos y de portafolio profesional.
