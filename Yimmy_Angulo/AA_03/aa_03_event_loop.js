
// Caso de Estudio 3: Event Loop Queue & Retención por Callbacks
const EventEmitter = require('events');
const emisorGlobal = new EventEmitter();

class GestorSesionUsuario {
  constructor(idUsuario) {
    this.idUsuario = idUsuario;
    // Reserva de 5 MB de datos por sesión
    this.bufferSesion = new Uint8Array(5 * 1024 * 1024);
    
    // Suscripción al emisor global
    this.oyenteEvento = () => {
      this.actualizarActividad();
    };
    
    emisorGlobal.on('pingSistema', this.oyenteEvento);
  }

  actualizarActividad() {
    console.log(`Sesión ${this.idUsuario} activa.`);
  }

  cerrarSesion() {
    console.log(`Cerrando sesión de ${this.idUsuario}`);
    // NOTA: El desarrollador olvidó desregistrar el evento en emisorGlobal
  }
}

function simularTrafico() {
  for (let i = 0; i < 50; i++) {
    let usuario = new GestorSesionUsuario(`USR_${i}`);
    usuario.cerrarSesion();
    // La variable local `usuario` sale de alcance (Stack) al terminar la iteración
  }
}

simularTrafico();
// Forzar la ejecución del evento global
emisorGlobal.emit('pingSistema');