# 📂 Implementación de Listas Dinámicas Simples sin Overhead en JavaScript - Práctica Experimental 04 (APE-04)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la **Práctica Experimental 04** del **Grupo 4**, enfocada en el diseño e implementación del Tipo de Dato Abstracto (TDA) Lista Simple Enlazada sin *overhead*, contrastando empíricamente el tiempo de ejecución y el impacto en la CPU frente a los arreglos nativos bajo los principios de *Green Computing*.

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./APE_04_Grupo04.pdf)

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

## 🔍 Resumen y Desarrollo de la Práctica (APE-04)

### 1. Enfoque del Problema y Tareas Realizadas
* **Contexto ABP/ABI:** Simulación de una red social y sistema de servidores que procesan millones de peticiones por segundo. Se evalúa cómo el uso de estructuras de datos ineficientes incrementa el consumo eléctrico, la temperatura y los costos operativos del hardware.

* **Tarea 1 (Implementación del TDA Nodo y Lista Simple):** Creación del archivo `lab04_lista_simple.js` definiendo la clase `Nodo` (con carga útil y puntero `siguiente`) y la clase `ListaSatelital` dotada del método `prepend()` para inserciones eficientes al inicio con complejidad $O(1)$.

* **Tarea 2 (Benchmark de Estrés Térmico):** Implementación de `lab04_benchmark.js` para medir los milisegundos requeridos al insertar $N = 100,000$ elementos al inicio de un arreglo nativo (`unshift`) frente a la lista enlazada (`prepend`).

* **Tarea 3 (Implementación del Recorrido):** Incorporación del método secuencial `imprimirPrimeros(limite)` para navegar a través de los nodos de la lista enlazada desde la cabeza hasta el límite especificado.

### 2. Resultados Experimentales y Tabulación
Los resultados obtenidos en el *benchmark* demuestran una diferencia abismal en el rendimiento de ambas estructuras al realizar inserciones masivas al inicio:

| Métrica / Parámetro | Arreglo Nativo (`unshift`) | Lista Enlazada Simple (`prepend`) | Diferencia / Impacto Real |
| :--- | :--- | :--- | :--- |
| **Tiempo de Ejecución** | 1,743.81 ms (Estructura base 1x) | 9.62 ms | 181.3 veces más rápida |
| **Factor Aceleración** | Base | 181.3x | Reducción drástica de la latencia en milisegundos |
| **Comportamiento Térmico / CPU** | Energía Alta (Desplazamiento masivo de índices en memoria contigua) | Energía Baja (Asignación directa de punteros sin mover datos) | Ahorro directo de ciclos de CPU y menor estrés térmico |

### 3. Análisis Técnico y de Arquitectura (ABI)
* **Gestión de Memoria y Rendimiento:** El arreglo nativo desplaza físicamente todos sus elementos en bloques contiguos al usar `unshift()`, generando una degradación asintótica cuadrática $O(n^2)$ en un bucle completo. Por el contrario, la lista enlazada reasigna únicamente un puntero con `prepend()`, manteniendo un crecimiento lineal eficiente $O(n)$ que mitiga el consumo de ciclos de reloj de la ALU.

* **Recorrido Secuencial:** Al no utilizar índices numéricos directos, la lista enlazada enlaza lógicamente cada elemento mediante referencias en memoria, garantizando la correcta navegación y validación de los datos tras la inserción masiva.

### 4. Preguntas de Control (Resumen Conceptual)
1. **Causa Material:** La conexión `this.siguiente` que une nodos esparcidos aleatoriamente por el Heap no es un bloque contiguo, sino una referencia o puntero numérico de 64 bits que almacena la dirección exacta de memoria del siguiente objeto, permitiendo a la ALU realizar saltos lógicos sin reorganizar la memoria física.

2. **Causa Eficiente:** El único agente que se debe modificar en la inserción es la referencia `this.cabeza` y el puntero del nuevo nodo, logrando una complejidad constante $O(1)$ por inserción sin desplazar datos, a diferencia del desplazamiento masivo de índices que ejecuta el motor V8 en los arreglos.

3. **Causa Final:** El *telos* (propósito) de construir una lista enlazada en producción no es superar de forma genérica a los arreglos optimizados de bajo nivel, sino adaptar la organización de los datos a patrones específicos de operaciones (como inserciones o eliminaciones frecuentes en los extremos) donde su costo algorítmico ofrezca una ventaja real.

4. **Esencia y Existencia:** Un nodo que pierde su referencia sigue existiendo físicamente ocupando electrones en la RAM (existencia), pero al volverse inaccesible para la lista pierde su utilidad práctica (esencia). Eventualmente, el algoritmo *Mark-and-Sweep* del Recolector de Basura lo identificará como inalcanzable y liberará su espacio físico.