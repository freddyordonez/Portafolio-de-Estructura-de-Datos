// Tarea 1: Implementación del TDA Lista Doblemente Enlazada
class Nodo {
  constructor(dato) {
    this.dato = dato;        // Carga útil (la coordenada)
    this.siguiente = null;   // Puntero al siguiente nodo
    this.anterior = null;    // Puntero al nodo anterior (referencia bidireccional)
  }
}

class ListaSatelital {
  constructor() {
    this.cabeza = null;  // Primer elemento de la lista
    this.actual = null;  // Cursor de navegación para next() y prev()
    this.tamaño = 0;     // Contador para S(n)
  }

  // Insertar al inicio: Complejidad O(1)
  prepend(dato) {
    const nuevoNodo = new Nodo(dato);
    if (!this.cabeza) {
      this.cabeza = nuevoNodo;
      this.actual = nuevoNodo;
    } else {
      nuevoNodo.siguiente = this.cabeza;  // el nuevo apunta a la cabeza actual
      this.cabeza.anterior = nuevoNodo;   // la cabeza actual apunta de regreso al nuevo
      this.cabeza = nuevoNodo;            // la cabeza pasa a ser el nuevo nodo
    }
    this.tamaño++;
  }

  // Lleva el cursor al primer nodo: O(1)
  irAlInicio() {
    this.actual = this.cabeza;
  }

  // Avanza el cursor un nodo y devuelve su dato: O(1)
  next() {
    if (this.actual && this.actual.siguiente) {
      this.actual = this.actual.siguiente;
      return this.actual.dato;
    }
    return null; // no hay siguiente
  }

  // Retrocede el cursor un nodo y devuelve su dato: O(1)
  prev() {
    if (this.actual && this.actual.anterior) {
      this.actual = this.actual.anterior;
      return this.actual.dato;
    }
    return null; // no hay anterior
  }

  // Tarea 3: Recorrido secuencial O(n)
  imprimirPrimeros(limite) {
    let actual = this.cabeza;
    let contador = 0;
    let salida = "";

    while (actual && contador < limite) {
      salida += `[ID: ${actual.dato.id}] -> `;
      actual = actual.siguiente;
      contador++;
    }
    console.log(salida + "NULL");
  }
}

module.exports = { ListaSatelital };
