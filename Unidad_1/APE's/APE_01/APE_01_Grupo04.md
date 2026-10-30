# 📂 Laboratorio inicial en IDE para medir bytes en tipos primitivos vs. objetos - Práctica Experimental 01 (APE-01)

Este directorio centraliza la documentación oficial y el desarrollo práctico de la **Práctica Experimental 01** del **Grupo 4**, enfocada en medir empíricamente el impacto en la memoria Heap (complejidad espacial $S(n)$) al procesar estructuras basadas en tipos primitivos frente a arreglos de objetos masivos en JavaScript (motor V8).

---

## 📄 Documento de la Práctica Experimental

El reporte completo de la guía y los resultados prácticos en formato PDF se encuentra disponible para su consulta y descarga en este repositorio:

<div align="center">

[![Ver Documento PDF](https://img.shields.io/badge/📖_Ver_Documento_PDF-Hacer_Clic_Aquí-blue?style=for-the-badge&logo=adobeacrobatreader)](./APE_01_Grupo04.pdf)

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

## 🔍 Resumen y Desarrollo de la Práctica (APE-01)

### 1. Enfoque del Problema y Tareas Realizadas
* **Contexto ABP/ABI:** Simulación de un sistema de geolocalización satelital sostenible encargado de cargar $N = 10^6$ (1 millón) de coordenadas en memoria. Se evaluó cómo la instanciación masiva de objetos colapsa los límites del entorno frente a alternativas eficientes de memoria contigua.

* **Tarea 1 (Profiler de Memoria):** Implementación de una función utilitaria basada en el módulo nativo `process.memoryUsage()` para medir el consumo del Heap en Megabytes (MB).

* **Tarea 2 (Enfoque de Objetos - TDA):** Creación de $1,000,000$ de instancias utilizando clases y constructores tradicionales (`CoordenadaObj`), registrando un consumo neto masivo en la memoria dinámica.

* **Tarea 3 (Enfoque de Primitivos - Data-Oriented Design):** Desacoplamiento de la abstracción mediante arreglos fuertemente tipados (`Float64Array`), obligando a V8 a almacenar los datos en bloques de memoria contiguos y optimizados.

### 2. Resultados Experimentales y Tabulación
Los datos obtenidos de la ejecución en Node.js reflejan la diferencia drástica en el consumo de recursos:

| Enfoque | Memoria Inicial | Memoria Final | Consumo Neto |
| :--- | :--- | :--- | :--- |
| **Primitivos** (`lab01_primitivos.js`) | 4.19 MB | 4.62 MB | **0.43 MB** |
| **Objetos** (`lab01_objetos.js`) | 4.19 MB | 94.48 MB | **90.29 MB** |

* **Conclusión empírica:** El uso de objetos genera un *overhead* significativo debido a las cabeceras y referencias internas, mientras que los `Float64Array` optimizan drásticamente el espacio al prescindir de metadatos por cada elemento.

### 3. Análisis Técnico y de Complejidad
* **Hidden Classes en V8:** El motor V8 agrupa objetos con idéntica estructura bajo una misma clase oculta, permitiendo un acceso directo a posiciones fijas de memoria sin consultar tablas hash lentas.

* **Espacio Asintótico:** Demostración teórica de que un arreglo de tipo `Float64` crece de forma lineal y constante respecto al número de elementos: $M(n) \in \Theta(n)$.

* **Impacto del Overhead:** Al modelar objetos, la función de memoria se expresa como $M_{objeto}(n) = Cn + K$ (donde $K$ representa el costo fijo o cabeceras), manteniendo un crecimiento asintótico $\mathcal{O}(n)$ pero con una constante de proporcionalidad sumamente elevada.

### 4. Preguntas de Control (Resumen Conceptual)
1. **Causa Material:** Un objeto en V8 está constituido por mapas ocultos y punteros dispersos en el Heap, a diferencia del bloque contiguo y plano de bits que ofrece un `Float64Array`.

2. **Causa Eficiente:** El intérprete (V8) actúa solo como agente instrumental; la responsabilidad principal recae en la decisión arquitectónica del programador al elegir la estructura de datos adecuada.

3. **Causa Final:** La abstracción en POO busca optimizar el pensamiento humano y la gestión de la complejidad lógica, reservando el acceso a nivel de hardware únicamente para escenarios críticos de rendimiento.

4. **Esencia y Existencia:** Dos estructuras que resuelven el mismo problema comparten la *esencia* (guardar un millón de coordenadas), pero difieren radicalmente en su *existencia* física al consumir volúmenes de energía y memoria totalmente distintos.