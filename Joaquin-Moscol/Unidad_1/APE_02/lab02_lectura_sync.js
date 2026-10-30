const fs = require('fs');
const { performance } = require('perf_hooks');
const FILE_NAME = 'coordenadas_masivas.csv';

console.log(`[Lectura Síncrona] Iniciando carga en memoria...`);
const memoryBefore = process.memoryUsage().heapUsed;
const start = performance.now();

// I/O Bloqueante: El hilo principal se detiene hasta cargar todo
// [GC] ZONA CRÍTICA 1: readFileSync carga TODO el archivo (~27 MB) como un solo
// string en el heap. Ese bloque grande presiona al Garbage Collector.
const data = fs.readFileSync(FILE_NAME, 'utf-8');

// Simulamos procesamiento separando por saltos de línea
// [GC] ZONA CRÍTICA 2: split crea un arreglo con 1,000,001 strings. Son millones
// de objetos que el GC debe recorrer, marcar y luego liberar. Aquí se gastan
// más ciclos del Garbage Collector.
const lineas = data.split('\n');

const end = performance.now();
const memoryAfter = process.memoryUsage().heapUsed;
console.log(`[Lectura Síncrona] Total registros leídos: ${lineas.length - 1}`);
console.log(`[Lectura Síncrona] Tiempo de I/O + Parsing: ${((end - start) / 1000).toFixed(2)} segundos.`);
console.log(`[Lectura Síncrona] Consumo Neto de RAM: ${((memoryAfter - memoryBefore) / 1024 / 1024).toFixed(2)} MB`);
