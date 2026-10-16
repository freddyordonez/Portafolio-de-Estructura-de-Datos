const { performance } = require('perf_hooks');

// Referencia global que provoca la fuga de memoria
const historialSatelitalGlobal = []; 

console.log("[Auditoría] Iniciando simulación con intento de forzar GC...");

function procesarCoordenada() {
    const paquete = {
        timestamp: Date.now(),
        lat: Math.random() * 90,
        lng: Math.random() * 180,
        payload: new Array(100000).fill('datos_topograficos_basura') 
    };

    historialSatelitalGlobal.push(paquete);
}

// Monitoreo y intento de limpieza cada 100 iteraciones
for (let i = 1; i <= 500; i++) {
    procesarCoordenada();

    if (i % 100 === 0) {
        // Si global.gc está expuesto, forzamos el barrido manual
        if (global.gc) {
            global.gc(); 
        }
        const memoryUsed = Math.round(process.memoryUsage().heapUsed / 1024 / 1024);
        console.log(`Iteración ${i} | RAM post-GC: ${memoryUsed} MB`);
    }
}

console.log(`[Auditoría] Finalizado. El arreglo retiene ${historialSatelitalGlobal.length} registros en el Heap.`);