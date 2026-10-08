function getMemoryUsage() {
    const memoryData = process.memoryUsage();
    const heapUsedMB = Math.round(memoryData.heapUsed / 1024 / 1024 * 100) / 100;
    return heapUsedMB;
}

module.exports = { getMemoryUsage };