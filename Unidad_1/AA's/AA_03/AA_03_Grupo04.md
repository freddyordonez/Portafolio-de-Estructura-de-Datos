# 📂 Portafolio - Taller Grupal 03 (AA-03)

Este directorio centraliza la documentación oficial y el análisis técnico del **Taller Grupal 03** del **Grupo 4**, enfocado en la diagramación de punteros y estados de memoria frente a la instanciación de objetos en JavaScript (motor V8).

---

## 📄 Documento del Taller Grupal

El reporte y taller completo en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_03_Grupo4.pdf)

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

## 🔍 Resumen General del Taller (AA-03)

### 1. Organización de la Memoria: Stack frente a Heap
* **El Stack (Pila de Ejecución):** Almacena registros de activación generados en cada llamada a funciones, conteniendo parámetros y variables locales de duración automática. Opera bajo un esquema LIFO (*Last-In, First-Out*) para datos estáticos con dimensiones conocidas en tiempo de compilación, garantizando un acceso extremadamente rápido.

* **El Heap (Montículo):** Región de memoria dinámica reservada para objetos y bloques de datos solicitados en tiempo de ejecución. Aloja tipos no primitivos como objetos, arreglos y funciones.

* **Recolector de Basura (*Garbage Collection*):** Gestiona y libera automáticamente la memoria del Heap mediante algoritmos de rastreo de objetos alcanzables.

### 2. Instanciación de Objetos y Punteros
* **Instanciación en el Heap:** Ocurre al crear estructuras complejas mediante objetos literales (`{}`), arreglos (`[]`), constructores con `new` o funciones.

* **Punteros y Referencias:** Una variable asignada a un objeto no almacena el objeto completo en el Stack, sino una referencia o puntero interno que apunta directamente a la ubicación física del objeto en el Heap.

* **Copia por Referencia y Aliasing:** Al asignar un objeto a una nueva variable (ej. `let b = a`), se copia la dirección de memoria en lugar del contenido, provocando que cualquier mutación se refleje inmediatamente en ambas variables.

### 3. Utilidad de los Diagramas de Estados de Memoria
* **Comprensión del Ciclo de Vida:** Visualizan de forma gráfica cómo se asignan y destruyen los marcos de activación en el Stack y los bloques dinámicos en el Heap.

* **Rastreo de Punteros:** Representan variables como cajas y referencias como flechas dirigidas para aclarar con precisión el comportamiento del *aliasing*.

* **Detección de Fugas de Memoria (*Memory Leaks*):** Permiten identificar referencias huérfanas o no deseadas que impiden la liberación de recursos por parte del recolector de basura.

## 4. Análisis de Casos de Estudio y Diagramación

Cada caso cuenta con su propia carpeta contenedora que incluye el código fuente en JavaScript y los diagramas correspondientes:

* **Caso 1: Shared Lexical Context Leak (Fuga por Contexto Léxico)**
  * Analiza cómo una función interna retiene una referencia al ámbito léxico de su función padre a través de un cierre (*closure*), impidiendo que el recolector de basura libere objetos masivos en el Heap.
  * 📂 **[Ir a la Carpeta del Caso 1](./Caso%201)**

* **Caso 2: Grafo Circular y Referencias Débiles (Map vs WeakMap)**
  * Examina islas de referencias circulares aisladas. Se demuestra cómo las estructuras tradicionales (`Map`) mantienen vínculos fuertes que impiden la liberación, haciendo indispensable el uso de `WeakMap` para permitir una recolección automática.
  * 📂 **[Ir a la Carpeta del Caso 2](./Caso%202)**

* **Caso 3: Event Loop Queue & Retención por Callbacks**
  * Estudia la retención de objetos en el Heap cuando se inscriben *callbacks* en emisores de eventos globales (*Event Emitters*), manteniendo vivos los objetos aunque la variable local salga del alcance del Stack.
  * 📂 **[Ir a la Carpeta del Caso 3](./Caso%203)**


### 5. Conclusiones del Equipo
* **C01 (Jerarquía y coexistencia de la memoria):** La gestión eficiente de un programa depende críticamente de diferenciar las responsabilidades del Stack (rápido y automático bajo LIFO) frente al Heap (dinámico y dependiente de control de referencias).

* **C02 (Impacto visual del modelado):** La diagramación de estados de memoria se establece como una herramienta indispensable en la ingeniería de software moderna para visualizar relaciones complejas y detectar fugas con precisión quirúrgica.

* **C03 (Optimización y robustez en entornos dinámicos):** Los casos de estudio evidencian que los errores de arquitectura como la retención en el Event Loop o el uso de estructuras rígidas en lugar de referencias débiles hacen indispensable el modelado gráfico para garantizar un software escalable.