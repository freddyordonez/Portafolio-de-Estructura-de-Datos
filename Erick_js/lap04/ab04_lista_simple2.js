// Tarea 1: Implementación del TDA Lista Enlazada
class Nodo {
    constructor(dato) {
        this.dato = dato; // Carga útil (la coordenada)
        this.siguiente = null; // Puntero al siguiente nodo (referencia)
    }
}
 // Método para imprimir la lista (Recorrido secuencial O(n))
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
