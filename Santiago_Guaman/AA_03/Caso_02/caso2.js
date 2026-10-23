/**
 * UNIVERSIDAD NACIONAL DE LOJA - CARRERA DE COMPUTACIÓN
 * Asignatura: Estructura de Datos
 * Guía de Ejercicios Avanzados de Memoria en JavaScript (V8 Engine)
 * Semana 03: Diagramación de Punteros y Estados de Memoria (Stack, Heap y GC)
 */

// Caso de Estudio 2: Grafo Circular y Referencias Débiles
class NodoGrafo {
  constructor(id) {
    this.id = id;
    this.vecinos = [];
  }

  conectarCon(nodo) {
    this.vecinos.push(nodo);
  }
}

function construirGrafoCiclico() {
  let nodoA = new NodoGrafo("A");
  let nodoB = new NodoGrafo("B");
  let nodoC = new NodoGrafo("C");

  // Conexión circular bidireccional
  nodoA.conectarCon(nodoB);
  nodoB.conectarCon(nodoC);
  nodoC.conectarCon(nodoA);

  // Registro en un gestor centralizado
  gestorCache.set(nodoA.id, nodoA);

  return nodoA.id;
}

const gestorCache = new Map(); // ¿Map o WeakMap?

let idRaiz = construirGrafoCiclico();

// El estudiante debe analizar qué sucede al ejecutar:
// gestorCache.delete(idRaiz);