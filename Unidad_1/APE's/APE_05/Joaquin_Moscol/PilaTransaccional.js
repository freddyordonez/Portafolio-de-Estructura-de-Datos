const { ListaDoblementeEnlazada } = require('./ListaDoble');

class PilaTransaccional {
  constructor() {
    this.lista = new ListaDoblementeEnlazada();
  }

  push(comando) {
    this.lista.insertarFinal(comando);
  }

  pop() {
    return this.lista.eliminarFinal();
  }

  obtenerTamano() {
    return this.lista.longitud;
  }
}

module.exports = PilaTransaccional;
