# 📂 Investigación documental y especificación de requerimientos. (AA-01)

Este directorio contiene la documentación oficial y el análisis técnico correspondiente al **Avance del Problema y Definición de Requerimientos del Sistema Enfocados en la Gestión de Memoria Estática y Dinámica en el Motor V8 de JavaScript y Node.js** del Grupo 4.

---

## 📄 Documento del Avance

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_01_Grupo4.pdf)

*Para mayor información, ingresar al PDF documentado y completo.*

</div>

---

## 👥 Integrantes del Grupo 4
* Freddy Alexander Ordoñez Gonzalez
* Yimmy Onner Angulo Torres
* Richar Santiago Guaman Sanchez
* Erick Jahirs Roguel Baque
* Joaquin Emilio Moscol Castillo

---

## 🔍 Resumen de Aspectos Claves del Documento

### 1. Investigación Teórica: Motor V8, Pila y Montón
Como equipo analizamos que el motor **V8 de Google** optimiza la ejecución mediante compilación JIT y divide su arquitectura de memoria en dos componentes esenciales:
* **La Pila (Call Stack):** Estructura LIFO monohila encargada de procesar de forma inmediata los tipos de datos primitivos y contextos de ejecución de tamaño fijo.

* **El Montón (Memory Heap):** Región de asignación dinámica no estructurada destinada a albergar objetos complejos, arreglos y clases, administrada por el Recolector de Basura (*Garbage Collector*).

### 2. Tabla Comparativa Resumida
| Característica | Pila (Call Stack) | Montón (Memory Heap) |
| :--- | :--- | :--- |
| **Tipo de datos** | Primitivos (`number`, `string`, `boolean`, etc.) | Objetos, arreglos, funciones y clases. |
| **Estructura** | Ordenada y secuencial (LIFO). | No estructurada / libre (*dynamic allocation*). |
| **Velocidad de acceso** | Sumamente rápida. | Más lenta (requiere seguir referencias o punteros). |
| **Gestión de memoria** | Automática al salir del ámbito de ejecución. | Gestionada y liberada por el Recolector de Basura. |

### 3. Planteamiento del Proyecto ABP: Autocompletado Turístico de Loja con Caché SIEVE
Para resolver las latencias elevadas en búsquedas de prefijos bajo alta concurrencia en Loja, definimos las bases de nuestro prototipo:
* **Objetivo General:** Diseñar un sistema de autocompletado combinando una estructura **Trie** y el algoritmo de reemplazo de caché **SIEVE**.

* **Tecnologías Seleccionadas:** 
  * *Backend:* Node.js y Express (ejecutando el motor V8).
  * *Frontend:* React y Tailwind CSS.
  * *Estructuras:* Árbol Trie, Listas Doblemente Enlazadas y Tablas Hash.

* **Clasificación de Datos en V8:** El catálogo estático y el árbol Trie residen permanentemente en el Montón, mientras que las consultas en tiempo real de los usuarios fluyen de manera secuencial por la Pila.

### 4. Conclusiones Grupales
* Comprendimos que la gestión automática de memoria en V8 equilibra la velocidad y abstracción sin intervención manual.

* La Pila garantiza la fluidez de las instrucciones secuenciales, mientras que el Montón soporta la flexibilidad de los datos dinámicos.

* La integración del Trie con la caché SIEVE optimiza de forma directa el uso de la memoria dinámica para resolver problemas reales de rendimiento en nuestra ciudad.
