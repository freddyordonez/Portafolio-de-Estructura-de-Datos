# 📂 Investigación Persistencia de Datos (AA-02)

Este directorio centraliza la documentación oficial y los reportes individuales del **Grupo 4** correspondientes al análisis de la persistencia de datos, latencia de E/S y el uso de la API LocalStorage en JavaScript.

---

## 👥 Integrantes del Grupo 4
* Freddy Alexander Ordoñez Gonzalez
* Yimmy Onner Angulo Torres
* Richar Santiago Guaman Sanchez
* Erick Jahirs Roguel Baque
* Joaquin Emilio Moscol Castillo

---
## 📄 Documentación Individual de los Integrantes

Cada integrante cuenta con su respectivo reporte documentado en formato PDF bajo los lineamientos establecidos para el equipo:

<div align="center">

| Integrante | Documento PDF | Acceso Directo |
| :--- | :--- | :--- |
| **Freddy Alexander Ordoñez Gonzalez** | `AA_02_Ordoñez_Freddy.pdf` | [![Ver PDF](https://img.shields.io/badge/📖_Ver_PDF-Freddy-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_02_Ordoñez_Freddy.pdf) |
| **Yimmy Onner Angulo Torres** | `AA_02_Angulo_Yimmy.pdf` | [![Ver PDF](https://img.shields.io/badge/📖_Ver_PDF-Yimmy-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_02_Angulo_Yimmy.pdf) |
| **Richar Santiago Guaman Sanchez** | `AA_02_Guaman_Richar.pdf` | [![Ver PDF](https://img.shields.io/badge/📖_Ver_PDF-Richar-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_02_Guaman_Richar.pdf) |
| **Erick Jahirs Roguel Baque** | `AA_02_Roguel_Erick.pdf` | [![Ver PDF](https://img.shields.io/badge/📖_Ver_PDF-Erick-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_02_Roguel_Erick.pdf) |
| **Joaquin Emilio Moscol Castillo** | `AA_02_Moscol_Joaquin.pdf` | [![Ver PDF](https://img.shields.io/badge/📖_Ver_PDF-Joaquin-blue?style=for-the-badge&logo=adobeacrobatreader)](./AA_02_Moscol_Joaquin.pdf) |

*Para mayor información y detalle técnico, ingresar al PDF documentado y completo de cada integrante.*

</div>


## 🔍 Resumen General del Tema (AA-02)

### 1. Persistencia de Datos y Reducción de Latencia de E/S
* **Mitigación de cuellos de botella:** Se analizó cómo la información manipulada en JavaScript suele ser volátil frente a la recarga de páginas. Para evitar consultas constantes al servidor que generan latencia y sobrecarga en la red, se implementó el uso de **LocalStorage** como un mecanismo nativo de almacenamiento persistente del lado del cliente.

* **Capacidad y esquema:** Bajo un modelo simple de pares clave-valor sin fecha de caducidad explícita, este recurso ofrece hasta 5 MB por dominio, superando ampliamente las restricciones de las cookies tradicionales.

### 2. Consideraciones Técnicas y Serialización
* **Conversión de estructuras:** Debido a que la API está diseñada exclusivamente para aceptar cadenas de texto (*strings*), el almacenamiento directo de objetos complejos arroja el valor predeterminado `"[object Object]"`.

* **Métodos esenciales:** Se estudió el uso obligatorio de `JSON.stringify()` para serializar los datos antes de guardarlos y de `JSON.parse()` para deserializarlos al recuperarlos, garantizando la integridad de la información en tiempo de ejecución.

### 3. Implementación de Casos Prácticos Modulares
* **Caché de preferencias de UI:** Gestión del estado estético (modo oscuro o claro) para recuperarlo instantáneamente al recargar el navegador sin afectar al servidor.

* **Manejo de objetos estructurados:** Almacenamiento y lectura segura de registros complejos mediante serialización.

* **Autoguardado de formularios:** Captura de eventos de entrada (`oninput`) en tiempo real para respaldar la información ingresada por los usuarios y prevenir pérdidas de datos ante interrupciones.

### 4. Conclusiones del Equipo
* El uso coordinado de herramientas del lado del cliente optimiza drásticamente la velocidad de respuesta del software web.

* Delegar la retención de preferencias y estados temporales al navegador reduce de forma significativa la sobrecarga de operaciones de Entrada/Salida en el servidor.