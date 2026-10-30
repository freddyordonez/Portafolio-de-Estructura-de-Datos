# 📂 Implementación acceso secuencial JS - Práctica Experimental 02 (APE-02)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la **Práctica Experimental 02** del **Grupo 4**, enfocada en la implementación de acceso secuencial a archivos masivos en disco mediante el módulo nativo de I/O en JavaScript, cuantificando la latencia temporal $T(n)$ y el impacto espacial en la memoria Heap $S(n)$ entre enfoques bloqueantes y flujos asíncronos (Streams).

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./APE_02_Grupo04.pdf)

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

## 🔍 Resumen y Desarrollo de la Práctica (APE-02)

### 1. Enfoque del Problema y Tareas Realizadas
* **Contexto ABP/ABI:** El sistema satelital requiere procesar $N = 10^6$ (1 millón) de coordenadas previamente almacenadas en un archivo físico en el servidor. Se evaluó cómo la carga masiva en un solo bloque colapsa la memoria RAM frente a estrategias de lectura optimizadas.

* **Tarea 1 (Generación de Datos - Mock Data):** Creación del archivo `coordenadas_masivas.csv` con $10^6$ registros utilizando escritura en flujo (`fs.createWriteStream`), logrando un peso físico en disco de 26.86 MB.

* **Tarea 2 (Lectura Síncrona - Bloqueante):** Implementación de `fs.readFileSync` para cargar todo el archivo de golpe en la memoria RAM y procesarlo mediante división por saltos de línea.

* **Tarea 3 (Lectura Asíncrona con Streams):** Uso de flujos de lectura (`fs.createReadStream`) para procesar fragmentos (*chunks*) secuenciales de manera no bloqueante, manteniendo una huella de memoria constante.

### 2. Resultados Experimentales y Tabulación
La comparación directa de rendimiento entre ambos enfoques refleja el impacto en los recursos del sistema:

| Métrica Evaluada | Enfoque 1: Lectura Síncrona (`lab02_lectura_sync.js`) | Enfoque 2: Lectura por Streams (`lab02_lectura_stream.js`) |
| :--- | :--- | :--- |
| **Total de Registros** | 1,000,001 | 1,000,001 |
| **Tiempo de I/O / Procesamiento** | 0.09 segundos | 0.04 segundos |
| **Consumo Neto de RAM** | 37.89 MB | **1.71 MB** |
| **Complejidad Espacial** | $O(n)$ (Carga masiva en RAM) | **$O(1)$** (Huella constante por buffer) |
| **Comportamiento Operativo** | Bloqueante (detiene la ejecución) | No bloqueante asíncrono por fragmentos |

### 3. Análisis Matemático y Arquitectura (ABI)
* **Demostración Asintótica:** La lectura síncrona impone $S(n) = O(n)$ debido a la creación del arreglo completo de líneas en memoria. En contraste, el enfoque con Streams procesa fragmentos de tamaño fijo ($64\text{ KB}$), resultando en una complejidad espacial constante $S(n) = O(1)$.

* **Thread Pool de libuv y Bus de Datos:** `readFileSync` bloquea el hilo principal esperando la respuesta del disco. Por el contrario, `createReadStream` delega la lectura en segundo plano al *Thread Pool* de libuv (4 hilos por defecto), permitiendo que el Event Loop atienda callbacks de forma asíncrona y que los datos viajen en bloques pequeños y constantes.

* **Green Computing:** Al reducir drásticamente el consumo de RAM y la presión sobre el Recolector de Basura, se disminuyen los ciclos innecesarios del procesador, reduciendo el gasto térmico y el consumo eléctrico del servidor.

### 4. Preguntas de Control (Resumen Conceptual)
1. **Causa Material:** La "materia" que se satura en el ordenador durante la lectura síncrona es la memoria RAM principal, la cual almacena energía eléctrica temporal, a diferencia de la persistencia magnética/electrónica estática del disco duro.

2. **Causa Formal:** La estructura lineal y de longitud variable de un archivo CSV impide calcular mediante una fórmula directa la posición de un registro lejano, obligando a un recorrido secuencial $O(n)$ a menos que se utilicen estructuras de tamaño fijo o índices.

3. **Causa Eficiente:** El Event Loop actúa como coordinador inicial, pero la verdadera causa eficiente que transfiere los electrones desde el disco a la RAM es el sistema operativo a través de sus llamadas (*Syscalls*).

4. **Causa Final:** El *telos* (finalidad) de la persistencia en disco no es competir con la velocidad de la caché o la RAM, sino garantizar la continuidad de la información y el conocimiento más allá del tiempo de ejecución de los procesos.

5. **Esencia y Existencia:** Ambas formas procesan el *mismo* conjunto lógico de datos (esencia), pero difieren radicalmente en su *existencia* física al ocupar volúmenes de energía y memoria totalmente distintos.