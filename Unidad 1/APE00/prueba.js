const memoryData = process.memoryUsage();
console.log("¡Hola! La memoria Heap usada es:", Math.round(memoryData.heapUsed / 1024 / 1024 * 100) / 100, "MB");