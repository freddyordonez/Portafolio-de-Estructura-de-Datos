# 📂 Mapeo de referencias en memoria y la auditoría de fugas de memoria - Práctica Experimental 03 (APE-03)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la **Práctica Experimental 03** del **Grupo 4**, enfocada en el mapeo de referencias en memoria, la simulación y auditoría de fugas de memoria (*Memory Leaks*), el forzado del Recolector de Basura (*Garbage Collector*) y la aplicación de principios de *Green Computing* en JavaScript (motor V8).

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./APE_03_Grupo04.pdf)

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

## 🔍 Resumen y Desarrollo de la Práctica (APE-03)

### 1. Enfoque del Problema y Tareas Realizadas
* **Contexto ABP/ABI:** El servidor satelital sufre un colapso por saturación del Heap tras horas de ejecución continua (`FATAL ERROR: Ineffective mark-compacts near heap limit`) debido a un crecimiento de complejidad espacial lineal $S(n) = O(n)$ sin control de límites.

* **Tarea 1 (Simulación de Memory Leak):** Creación del archivo `lab03_memory_leak.js` para registrar paquetes masivos de datos satelitales en un arreglo global (`historialSatelitalGlobal`), evidenciando un crecimiento lineal de la RAM hasta alcanzar los 386 MB en 500 iteraciones.

* **Tarea 2 (Forzado del Recolector de Basura):** Implementación de `lab03_gc_force.js` utilizando la bandera `--expose-gc` para invocar manualmente `global.gc()` dentro del ciclo iterativo, demostrando que la RAM sigue creciendo de forma idéntica porque la referencia global mantiene los objetos marcados como "en uso" por el algoritmo *Mark-and-Sweep*.

* **Tarea 3 (Solución y Mantenimiento Ecológico):** Refactorización en `lab03_solucion.js` limitando el historial a un máximo de 50 registros (`LIMITE_HISTORIAL = 50`) y aplicando desvinculación FIFO mediante el método `shift()`, transformando la complejidad espacial a constante $S(n) = O(1)$ y estabilizando el consumo energético (*Green Computing*).

### 2. Resultados Experimentales y Tabulación
La comparación entre la ejecución con pérdida de memoria y la solución controlada refleja el comportamiento del Heap:

| Iteración | Fuga de Memoria (`lab03_memory_leak.js`) | Solución Controlada $O(1)$ (`lab03_solucion.js`) | Análisis del Comportamiento Espacial |
| :--- | :--- | :--- | :--- |
| **200** | 157 MB | 157 MB | Comportamiento inicial idéntico mientras se llena el umbral inicial de la estructura. |
| **400** | 233 MB | 133 MB | La solución comienza a estabilizarse gracias a la liberación de los elementos más antiguos. |
| **600** | 310 MB (Estimado) | 95 MB | El Recolector de Basura actúa de forma efectiva; la RAM desciende y se mantiene regulada. |
| **800** | 386 MB (Estimado) | 58 MB | Se evidencia claramente el tope (*Green Computing*); el sistema no sufre desbordamientos. |
| **1000** | 460+ MB (Colapso inminente) | 210 MB (Estable en techo) | Mientras la fuga satura el servidor de forma infinita ($O(n)$), la solución optimizada estabiliza el consumo en un rango seguro y constante ($O(1)$). |

### 3. Análisis Técnico y de Arquitectura (ABI)
* **Algoritmo Mark-and-Sweep:** El motor V8 determina qué datos eliminar basándose en la accesibilidad desde la raíz (*root*). Al existir referencias activas en el ámbito global, los objetos no pueden ser barridos aunque se invoque el GC manualmente.

* **Optimización Espacial $O(1)$:** Al desvincular los nodos obsoletos mediante un límite estricto y un enfoque cíclico, se rompe la cadena de retención, permitiendo que el recolector de basura recupere los bloques de memoria dinámica de manera eficiente.

### 4. Preguntas de Control (Resumen Conceptual)
1. **Causa Material:** La estructura principal de un objeto complejo se asienta en el **Heap** por su tamaño dinámico e impredecible, mientras que el puntero o variable de referencia que lo enlaza se ubica en el **Stack** dentro del marco de ejecución activo.

2. **Causa Formal:** El alcance (*scope*) global actúa como una jaula estruc-tural permanente (raíz del programa), impidiendo que el algoritmo *Mark-and-Sweep* considere inalcanzables a los objetos enlazados en él, lo que hace inútil cualquier intento de limpieza si no se rompen las referencias explícitamente.

3. **Causa Eficiente:** El agente responsable de detener la acción del Recolector de Basura no es el motor en sí, sino la referencia o enlace activo olvidado por el desarrollador en el código, el cual protege al objeto de ser depurado.

4. **Causa Final:** El *telos* (propósito) de destruir intencionalmente los datos mediante la recolección de basura es conservar únicamente la información útil, preservando la capacidad operativa del sistema y evitando saturar los recursos finitos de la memoria.

5. **Esencia y Existencia:** Un dato olvidado en una estructura ocupa espacio físico en la RAM (existencia), pero al no ser consultado ni aportar utilidad lógica al programa, ha perdido su esencia. Sin embargo, al mantenerse enlazado por una referencia activa, el GC no puede liberarlo, constituyendo formalmente una fuga de memoria.