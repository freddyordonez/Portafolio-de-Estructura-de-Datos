# 📂 Listas Doblemente Enlazadas y Pilas: Navegación Bidireccional y Gestión LIFO de Mínimo Costo Energético - Práctica Experimental 05 (APE-05)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la Práctica Experimental 05 del Grupo 4, enfocada en el diseño e implementación de una Lista Doblemente Enlazada y una Pila Transaccional para permitir la navegación bidireccional y la gestión de operaciones mediante el principio LIFO. Además, se realiza una comparación experimental del tiempo de ejecución y el uso de recursos frente a los arreglos nativos de JavaScript, considerando los principios de Green Computing y la eficiencia en el uso de los recursos del sistema.

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

Proximamente

*Para mayor información, ingresar al PDF documentado y completo.*

</div>

---

## 👥 Integrantes del Grupo 4
* Freddy Alexander Ordoñez Gonzalez
* Yimmy Onner Angulo Torres
* Richard Santiago Guaman Sanchez
* Erick Jahirs Roguel Baque
* Joaquin Emilio Moscol Castillo

---

## 🔍 Resumen y Desarrollo de la Práctica (APE-05)

### 1. Enfoque del Problema y Tareas Realizadas
* **Contexto ABP/ABI:** Se implementó una estructura de Lista Doblemente Enlazada y una Pila Transaccional para manejar un historial de operaciones y permitir una navegación eficiente hacia adelante y hacia atrás. El objetivo fue comparar su rendimiento frente al uso de un arreglo nativo de JavaScript.

* **Tarea 1 (Lista Doblemente Enlazada):** Creación de `ListaDoble.js`, implementando los nodos con referencias `next` y `prev`. Se utilizaron los punteros `head` y `tail` para facilitar la inserción y eliminación de elementos en los extremos en `tiempo O(1).`

* **Tarea 2 (Forzado del Recolector de Basura):** Implementación de `PilaTransaccional.js` utilizando la Lista Doblemente Enlazada como estructura interna. Se desarrollaron las operaciones `push()` y `pop()` para agregar y retirar operaciones siguiendo el principio `LIFO (Last In, First Out)`, aprovechando la inserción y eliminación al final de la lista con una `complejidad temporal de O(1).`

* **Tarea 3 (Benchmark de rendimiento):** Creación de `BenchmarkNavegacion.js` para comparar *500.000* operaciones entre un **Array Nativo** utilizando `unshift()` y `shift()` y la **PilaTransaccional** mediante `push()` y `pop()`. El experimento permitió comparar el tiempo de ejecución y el consumo de memoria `Heap` de ambas implementaciones.

### 2. Resultados Experimentales y Tabulación

La comparación entre el **Array nativo y la Pila Transaccional** permite observar diferencias importantes en el tiempo de ejecución al realizar *500.000 operaciones*. Los resultados obtenidos muestran que la estructura basada en la Lista Doblemente Enlazada permite realizar las operaciones de inserción y extracción de manera mucho más eficiente.

| Estructura | Operaciones | Tiempo de ejecución |Interpretacion|
|---|---:|---:|---|
| Array nativo (`unshift/shift`) | 500.000 | 5:57.907 min |Es mas lenta en el procesamiento, debido a que tiene que mover muchos elementos|
| Pila Transaccional (`push/pop`) | 500.000 | 92.759 ms |Es mucho mas rapida y consume menos recurso|

**Explicación de los resultados:**

**Array nativo:** Para realizar las 500.000 operaciones utilizando `unshift()` y `shift()`, el programa tardó aproximadamente 5 minutos y 57 segundos. Esto se debe a que estas operaciones pueden requerir el desplazamiento de los elementos del arreglo, por lo que el tiempo de procesamiento aumenta cuando la cantidad de datos es mayor.

**Pila Transaccional:** Utilizando `push()` y `pop()`, la Pila Transaccional realizó las mismas 500.000 operaciones en aproximadamente 92.759 milisegundos. Al utilizar una Lista Doblemente Enlazada, puede trabajar directamente sobre el extremo de la estructura sin desplazar los demás elementos, manteniendo una complejidad temporal de O(1).

### 3. Análisis Técnico y de Arquitectura (ABI)

Los resultados muestran una diferencia muy grande entre ambas estructuras. Mientras el Array nativo necesitó casi **6 minutos**, la Pila Transaccional completó el mismo trabajo en aproximadamente **0,093 segundos**. Esto demuestra que, para este tipo de operaciones, la estructura enlazada ofrece un rendimiento considerablemente mayor.

### 4. Gráfico Comparativo

<img width="1089" height="640" alt="imagen" src="https://github.com/user-attachments/assets/33373587-2ec8-4910-8bbe-f0981716e947" />

*Figura 1. Gráfico de barras comparativo entre el Array nativo y la Pila Transaccional.*

**Explicación de la Figura 1:**

La gráfica muestra una diferencia considerable en el tiempo de ejecución de las dos estructuras. El Array nativo, utilizando unshift() y shift(), tardó aproximadamente 358 segundos (5 minutos y 57 segundos) en completar las 500.000 operaciones. En cambio, la Pila Transaccional, mediante push() y pop(), realizó las mismas operaciones en aproximadamente 92,759 milisegundos (0,093 segundos).

Esto demuestra que la Pila Transaccional presenta un mejor rendimiento para este tipo de operaciones, ya que puede insertar y eliminar elementos directamente desde el extremo de la estructura sin necesidad de desplazar los demás elementos.

### 5. Preguntas de Control (Resumen Conceptual)
1. **En el contexto de las estructuras de datos dinámicas, ¿cuál es la "sustancia" de un nodo en una Lista Doblemente Enlazada en comparación con la de una celda de un arreglo contiguo, y cómo modifica el puntero anterior (prev) su naturaleza constitutiva?**
   
2. **¿Por qué la operación de inserción y extracción LIFO en una Pila implementada mediante punteros dobles alcanza la causa eficiente de la inmediatez O1, superando el trabajo mecánico de reubicación física de celdas que realiza el motor V8 en un arreglo nativo?**

3. **¿Cuál es el fin último (telos) de la navegación bidireccional en un historial de operaciones o sistema de transacciones Undo/Redo, y en qué medida optimiza la estabilidad energética del sistema de cómputo?**

   El objetivo de la navegación bidireccional es poder avanzar y retroceder fácilmente en el historial de operaciones, como cuando usamos Undo y Redo. Los punteros next y prev permiten moverse en ambas direcciones sin tener que mover todos los elementos.
   Esto hace que el programa trabaje más rápido y use menos recursos. En la prueba de 500.000 operaciones, se pudo notar una gran diferencia: el arreglo con unshift() y shift() tardó casi 6 minutos, mientras que la PilaTransaccional realizó las mismas operaciones en solo unos 93 milisegundos. Esto demuestra que la pila es mucho más rápida y que la CPU necesita trabajar durante menos tiempo, aprovechando mejor los recursos del computador.

4. **Aristóteles sostenía que nada existe sin una forma y materia dada. Si para ganar flexibilidad bidireccional se requiere duplicar las referencias de memoria prev y next, ¿cuándo deja el gasto de memoria Heap de ser una virtud de diseño para convertirse en un exceso ontológico (overhead)?**

   Gastar esa memoria extra en el Heap deja de ser algo bueno y se convierte en un desperdicio cuando se hace un programa en el que realmente nunca se va a necesitar ir hacia atrás en los datos, es decir, si solo se va a guardar información que fluye en una sola dirección (como leer una lista siempre hacia adelante), crear ese enlace extra para retroceder (prev) es gastar memoria en vano. Esa "forma" de poder ir en dos direcciones jamás se la va a usar, así que esa "materia" (la memoria RAM de la computadora) simplemente se desperdicia.
   Solo vale la pena gastar ese espacio si de verdad me va a salvar de que el programa se ponga lento al intentar regresar. Si el sistema no necesita ir para atrás, ese enlace extra pierde todo su propósito y se vuelve puro peso muerto para la memoria.

5. **Considerando la conservación de energía como principio técnico y ético, ¿de qué manera la eliminación instantánea de referencias sin desplazamiento de elementos mitiga el trabajo electromagnético de la CPU?**

