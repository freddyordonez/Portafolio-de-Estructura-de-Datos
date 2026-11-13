<<<<<<< HEAD
=======
const { ListaDoblementeEnlazada } = require('./ListaDoble');

/**
 * Pila (Stack) orientada a Sostenibilidad Computacional.
 * Operaciones fundamentales Push y Pop en O(1) estricto.
 */
class PilaTransaccional {
  constructor() {
    this.lista = new ListaDoblementeEnlazada();
  }

  /**
   * Apila un comando de telemetría en el tope.
   * Complejidad Temporal: O(1)
   */
  push(comando) {
    this.lista.insertarFinal(comando);
  }
<<<<<<< HEAD
=======

>>>>>>> Freddy_Ordonez
  /**
   * Desapila y retorna el comando en el tope (Undo).
   * Complejidad Temporal: O(1)
   */
  pop() {
    return this.lista.eliminarFinal();
  }

  /**
   * Retorna la cantidad de elementos almacenados.
   */
  obtenerTamano() {
    return this.lista.longitud;
  }
}

<<<<<<< HEAD
module.exports = PilaTransaccional;
=======
module.exports = PilaTransaccional;
>>>>>>> Freddy_Ordonez
>>>>>>> 502988a04f2e4f6f0ac626a28f4ae123a8eb9b02
