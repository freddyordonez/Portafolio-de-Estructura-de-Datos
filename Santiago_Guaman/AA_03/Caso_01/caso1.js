/**
 * UNIVERSIDAD NACIONAL DE LOJA - CARRERA DE COMPUTACIÓN
 * Asignatura: Estructura de Datos
 * Guía de Ejercicios Avanzados de Memoria en JavaScript (V8 Engine)
 * Semana 03: Diagramación de Punteros y Estados de Memoria (Stack, Heap y GC)
 */
 
 // Caso de Estudio 1: Shared Lexical Context Leak
let ejecutorGlobal = null;

function crearProcesadorResiduos() {
  // Asignación de 10 MB en el Heap
  let datosMasivos = new Array(10 * 1024 * 1024).fill("DATA_CRITICA");
  let copiaAnterior = ejecutorGlobal;

  // Función interna 1: NUNCA se invoca directamente, pero utiliza datosMasivos
  let funcionRetenida = function () {
    if (datosMasivos) {
      console.log("Referencia activa a datos masivos");
    }
  };

  // Función interna 2: Sobrevive asignándose a la variable global
  ejecutorGlobal = function () {
    console.log("Ejecutando proceso liviano...");
    if (copiaAnterior) {
      copiaAnterior();
    }
  };
}

// Simulación de llamadas repetidas
setInterval(() => {
  crearProcesadorResiduos();
  console.log(`Heap usado: ${(process.memoryUsage().heapUsed / 1024 / 1024).toFixed(2)} MB`);
}, 1000);