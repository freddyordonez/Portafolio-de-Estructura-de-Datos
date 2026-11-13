# 📂 Pilas Transaccionales de Bajo Costo: Mecanismos de Backtracking/Undo para Evitar el Re-procesamiento en Conjuntos Ordenados - Práctica Experimental 06 (APE-06)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la Práctica Experimental 06 del Grupo 4, enfocada en el diseño e implementación de una Pila Transaccional de Deltas para gestionar operaciones de Backtracking/Undo en conjuntos de datos ordenados. Además, se realiza una comparación experimental entre la recuperación transaccional mediante estructuras LIFO y el reordenamiento completo de registros, considerando el tiempo de ejecución, la variabilidad estadística y el consumo de memoria Heap bajo los principios de Green Computing y sostenibilidad computacional.

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

Próximamente

*Para mayor información, ingresar al PDF documentado y completo.*

</div>

---

## 👥 Integrantes del Grupo 4

* Freddy Alexander Ordoñez Gonzalez
* Yimmy Onner Angulo Torres
* Richard Santiago Guaman Sanchez
* Erick Jahirs
* Joaquin Emilio Moscol Castillo

---

## 🔍 Resumen y Desarrollo de la Práctica (APE-06)

### 1. Enfoque del Problema y Tareas Realizadas

* **Contexto ABP/ABI:** Se planteó un escenario de monitoreo energético inteligente que mantiene 100.000 registros ordenados por prioridad de consumo. Ante la recepción de lotes de telemetría corruptos, es necesario revertir las modificaciones realizadas sin tener que clonar toda la colección o ejecutar nuevamente un ordenamiento completo. Para resolver este problema se implementó una Pila Transaccional de Deltas que permite almacenar únicamente las variaciones de estado y ejecutar operaciones de Backtracking/Undo.

* **Tarea 1 (Construcción de la Pila Transaccional de Deltas):** Creación de `PilaTransaccional.js`, implementando las clases `NodoDelta` y `PilaTransaccional`. Cada nodo almacena el índice afectado, el estado anterior, el estado nuevo y el identificador del lote. Se desarrollaron las operaciones `push()`, `pop()`, `peek()` y `estaVacia()`, utilizando nodos enlazados para garantizar operaciones en tiempo O(1).

* **Tarea 2 (Gestor de Ordenamiento Incremental):** Creación de `GestorOrdenamientoTransaccional.js` para administrar un conjunto de 100.000 registros energéticos ordenados ascendentemente por watts. Se implementó la actualización de registros mediante desplazamientos incrementales, almacenando cada modificación en la Pila Transaccional. También se desarrollaron dos estrategias de recuperación: Backtracking/Undo mediante la pila LIFO con complejidad O(m) y reordenamiento completo mediante `sort()` con complejidad O(N log N).

* **Tarea 3 (Benchmark Estadístico y Auditoría Energética):** Creación de `BenchmarkEstadistico.js` para ejecutar 30 réplicas experimentales independientes sobre un conjunto de 100.000 registros y 250 mutaciones por lote. Se compararon ambas estrategias mediante el cálculo de la media muestral, desviación estándar, coeficiente de variación e intervalos de confianza del 95 %. También se midió la variación neta de memoria Heap utilizando el recolector de basura de V8.

### 2. Resultados Experimentales y Tabulación

La comparación entre la **Pila Transaccional LIFO y el Reordenamiento Completo** permite observar diferencias importantes en el tiempo de recuperación de los registros. Los resultados obtenidos corresponden a 30 réplicas experimentales, utilizando 100.000 registros y 250 mutaciones.

| Estrategia | Operaciones | Tiempo promedio | Coeficiente de variación |
|---|---:|---:|---:|
| Pila Transaccional LIFO O(m) | 250 mutaciones | 214.8371 ms | 95.76 % |
| Reordenamiento Completo O(N log N) | 100.000 registros | 11.1387 ms | 200.99 % |

**Resultados adicionales:**

- **Tasa de ahorro computacional:** -1828.75 %.
- **Variación neta de memoria Heap:** -5.367 MB.
- **Número de réplicas experimentales:** 30.
- **Cantidad de registros:** 100.000.
- **Cantidad de mutaciones:** 250.

**Explicación de los resultados:**

**Pila Transaccional:** Presentó un tiempo promedio de 214.8371 milisegundos para revertir las 250 mutaciones registradas. Esta estrategia utiliza una estructura LIFO que permite recuperar los estados anteriores mediante los deltas almacenados en la pila, evitando tener que realizar nuevamente todas las modificaciones de la colección.

**Reordenamiento Completo:** Obtuvo un tiempo promedio de 11.1387 milisegundos, siendo la estrategia con menor tiempo de ejecución en este experimento. Mediante el método `sort()`, se realizó nuevamente el ordenamiento de los registros, consiguiendo un menor tiempo promedio bajo las condiciones evaluadas.

**Variabilidad de los resultados:** La Pila Transaccional presentó un coeficiente de variación del 95.76 %, mientras que el Reordenamiento Completo alcanzó el 200.99 %. Esto evidencia una mayor dispersión relativa de los tiempos de ejecución del segundo método.

**Consumo de memoria:** Se registró una variación neta de -5.367 MB en la memoria Heap al finalizar el experimento. Este resultado representa una disminución global de memoria y no permite atribuir el ahorro exclusivamente a una de las estrategias.

### 3. Análisis Técnico y de Arquitectura (ABI)

Los resultados experimentales muestran que la eficiencia teórica de una estructura de datos no siempre garantiza un menor tiempo de ejecución en todas las condiciones.

La Pila Transaccional utiliza una estrategia de recuperación incremental basada en operaciones LIFO, permitiendo deshacer las modificaciones registradas mediante los nodos delta. Su complejidad de recuperación es O(m), donde m representa la cantidad de mutaciones que deben revertirse.

Por otra parte, el Reordenamiento Completo utiliza el algoritmo `sort()` para organizar nuevamente los registros, con una complejidad temporal de O(N log N). Aunque teóricamente requiere procesar una mayor cantidad de elementos, en esta ejecución presentó un tiempo promedio inferior.

La evaluación estadística permitió observar que el Reordenamiento Completo tuvo una mayor variabilidad en sus tiempos de ejecución. Asimismo, el análisis de memoria Heap mostró una disminución global de 5.367 MB, sin que sea posible asociarla exclusivamente a una estrategia.

Estos resultados permiten comprender que el rendimiento de una estructura de datos debe evaluarse considerando tanto su complejidad algorítmica como su comportamiento experimental, las condiciones de ejecución y los recursos utilizados por el sistema.

### 4. Gráficos Comparativos



*Figura 7. Ejecución del benchmark estadístico en Visual Studio Code, mostrando los tiempos promedio y los indicadores de rendimiento de ambas estrategias.*



*Figura 8. Distribución t-Student con 29 grados de libertad, representando el nivel de confianza del 95 % y sus respectivos valores críticos.*



*Figura 9. Comparación del intervalo de confianza del 95 % correspondiente a la Pila Transaccional.*



*Figura 10. Comparación del intervalo de confianza del 95 % correspondiente al Reordenamiento Completo.*



*Figura 11. Comparación del coeficiente de variación de la Pila Transaccional y el Reordenamiento Completo durante las pruebas experimentales.*

**Explicación de los resultados gráficos:**

Los gráficos permiten visualizar el comportamiento estadístico de las dos estrategias evaluadas durante las 30 réplicas experimentales.

La distribución t-Student representa el nivel de confianza del 95 % con 29 grados de libertad, utilizado para calcular los intervalos de confianza de las muestras.

En los gráficos de intervalos de confianza se observa la distribución de los tiempos promedio de ambas estrategias. La Pila Transaccional presentó una media de 214.8371 ms, mientras que el Reordenamiento Completo obtuvo una media de 11.1387 ms.

Finalmente, la comparación del coeficiente de variación muestra que la Pila Transaccional alcanzó un 95.76 %, mientras que el Reordenamiento Completo presentó un 200.99 %, reflejando una mayor dispersión relativa en sus resultados experimentales.

### 5. Ejecución del Experimento

Para ejecutar el benchmark estadístico se utilizó Node.js, habilitando explícitamente el recolector de basura de V8 mediante el siguiente comando:

```bash
node --expose-gc BenchmarkEstadistico.js
```

Esta configuración permite invocar el recolector de basura durante las mediciones de memoria Heap y realizar la evaluación experimental de ambas estrategias.

**Repositorio de GitHub Grupal:**

https://github.com/fireddyordonez/Portafiolio-de-Estructura-de-Datos.git

**Nota de ejecución:** La implementación de la Pila Transaccional, el Gestor de Ordenamiento y el Benchmark Estadístico se encuentran organizados dentro del directorio correspondiente a la Práctica Experimental 06. El experimento permite comparar la recuperación mediante Backtracking/Undo frente al reordenamiento completo de los registros.

---

## 6. Conclusiones

★ La implementación de la Pila Transaccional permitió comprender la importancia de utilizar estructuras de datos dinámicas para gestionar y recuperar estados mediante operaciones LIFO. A través de las clases `NodoDelta` y `PilaTransaccional`, se logró almacenar únicamente las variaciones de estado, evitando la necesidad de clonar toda la colección de registros para realizar operaciones de Backtracking/Undo.

★ El desarrollo del Gestor de Ordenamiento Transaccional permitió aplicar mecanismos de actualización incremental y recuperación de registros. Se implementaron dos estrategias: la Pila Transaccional LIFO O(m) y el Reordenamiento Completo O(N log N), comprendiendo sus diferencias en cuanto a complejidad algorítmica y comportamiento durante la ejecución.

★ La evaluación experimental permitió comparar ambas estrategias mediante 30 réplicas independientes, utilizando 100.000 registros y 250 mutaciones. Los resultados mostraron un tiempo promedio de 214.8371 ms para la Pila Transaccional y 11.1387 ms para el Reordenamiento Completo, evidenciando que este último obtuvo un menor tiempo de ejecución bajo las condiciones evaluadas.

★ La aplicación del análisis estadístico permitió interpretar los resultados mediante indicadores como la media, desviación estándar, coeficiente de variación e intervalos de confianza del 95 %. Se identificó una mayor dispersión relativa en los tiempos del Reordenamiento Completo, con un coeficiente de variación del 200.99 %, frente al 95.76 % de la Pila Transaccional.

★ Finalmente, la medición de memoria Heap registró una variación neta de -5.367 MB. La práctica permitió comprender que la selección de una estructura de datos no debe depender únicamente de su complejidad teórica, sino también de las condiciones experimentales, el comportamiento del sistema y el aprovechamiento de los recursos computacionales.