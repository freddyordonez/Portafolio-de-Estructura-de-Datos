import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * APE 02 - Acceso secuencial y aleatorio a archivos con metricas de I/O.
 *
 * Que hace este programa:
 *   1. Convierte coordenadas_masivas.csv (creado con lab02_generador.js) en el archivo
 *      binario coordenadas_masivas.dat, donde todos los registros miden lo mismo.
 *   2. Lee el archivo de forma SECUENCIAL (un registro tras otro).
 *   3. Lee el archivo de forma ALEATORIA (saltando a posiciones al azar con seek).
 *   4. Mide la latencia en milisegundos y guarda resultados.csv.
 *   5. Llama a GraficoResultados para dibujar los graficos.
 *
 * Como ejecutarlo (en la carpeta del proyecto):
 *   node lab02_generador.js     (solo si todavia no existe coordenadas_masivas.csv)
 *   javac AccesoArchivos.java GraficoResultados.java
 *   java AccesoArchivos
 */
public class AccesoArchivos {

    // Cada registro mide siempre lo mismo: id (4 bytes) + latitud (8) + longitud (8) = 20 bytes.
    // Por eso el registro numero i empieza exactamente en el byte  i * 20.
    static final int TAM_REGISTRO = 20;
    static final int TOTAL_REGISTROS = 1000000;
    static final String ARCHIVO_CSV = "coordenadas_masivas.csv";
    static final String ARCHIVO = "coordenadas_masivas.dat";

    // Cantidades de registros que se leen en cada prueba y repeticiones por prueba
    static final int[] CANTIDADES = {1000, 10000, 100000};
    static final int REPETICIONES = 5;

    // ------------------------------------------------------------------
    // 1. CONVERTIR EL CSV A ARCHIVO BINARIO
    // ------------------------------------------------------------------
    // En el CSV cada linea mide distinto ("5,12.5,-3.1" vs "999999,-45.123456,170.5"),
    // asi que no se puede saltar directo a un registro. En el binario todos miden
    // 20 bytes, y eso es lo que permite el acceso aleatorio con seek().
    static void convertirCsvABinario() throws Exception {
        System.out.println("Convirtiendo " + ARCHIVO_CSV + " a " + ARCHIVO + " ...");
        long inicio = System.nanoTime();

        // BufferedReader lee el texto linea por linea.
        BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_CSV));
        // DataOutputStream escribe int y double directamente en binario.
        // BufferedOutputStream acumula los datos y los escribe por bloques (mas rapido).
        DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(ARCHIVO)));

        lector.readLine();                       // se salta la cabecera: id,latitud,longitud
        String linea = lector.readLine();
        int total = 0;
        while (linea != null) {
            String[] partes = linea.split(",");  // partes[0]=id, partes[1]=latitud, partes[2]=longitud
            salida.writeInt(Integer.parseInt(partes[0]));          // 4 bytes
            salida.writeDouble(Double.parseDouble(partes[1]));     // 8 bytes
            salida.writeDouble(Double.parseDouble(partes[2]));     // 8 bytes
            total++;
            linea = lector.readLine();
        }
        salida.close();
        lector.close();

        double ms = (System.nanoTime() - inicio) / 1000000.0;
        double mb = new File(ARCHIVO).length() / (1024.0 * 1024.0);
        System.out.printf("Archivo creado: %d registros, %.2f MB, %.0f ms%n%n", total, mb, ms);
    }

    // ------------------------------------------------------------------
    // 2. LECTURA SECUENCIAL
    // ------------------------------------------------------------------
    // Se lee desde el inicio; despues de cada lectura el puntero del archivo
    // avanza solo, asi que cada read() trae el registro siguiente.
    static double lecturaSecuencial(RandomAccessFile archivo, int cantidad) throws Exception {
        byte[] bytes = new byte[TAM_REGISTRO];
        double sumaLatitudes = 0;

        archivo.seek(0);                         // puntero al inicio
        for (int i = 0; i < cantidad; i++) {
            archivo.readFully(bytes);            // lee 20 bytes y avanza el puntero
            ByteBuffer registro = ByteBuffer.wrap(bytes);
            registro.getInt();                   // id (no se usa, solo se salta)
            sumaLatitudes += registro.getDouble();
        }
        return sumaLatitudes;
    }

    // ------------------------------------------------------------------
    // 3. LECTURA ALEATORIA
    // ------------------------------------------------------------------
    // Para cada registro se calcula su posicion (indice * 20) y se salta
    // hasta ahi con seek(). No hace falta recorrer los registros anteriores.
    static double lecturaAleatoria(RandomAccessFile archivo, int[] indices) throws Exception {
        byte[] bytes = new byte[TAM_REGISTRO];
        double sumaLatitudes = 0;

        for (int i = 0; i < indices.length; i++) {
            long posicion = (long) indices[i] * TAM_REGISTRO;
            archivo.seek(posicion);              // salto directo al registro
            archivo.readFully(bytes);
            ByteBuffer registro = ByteBuffer.wrap(bytes);
            registro.getInt();
            sumaLatitudes += registro.getDouble();
        }
        return sumaLatitudes;
    }

    // ------------------------------------------------------------------
    // 4. MEDICION (en milisegundos)
    // ------------------------------------------------------------------
    // Cada prueba se repite varias veces y se toma la mediana (el valor del
    // medio), asi un tiempo raro no distorsiona el resultado.
    static double medianaDe(double[] tiempos) {
        Arrays.sort(tiempos);
        return tiempos[tiempos.length / 2];
    }

    static double medirSecuencial(RandomAccessFile archivo, int cantidad) throws Exception {
        double[] tiempos = new double[REPETICIONES];
        for (int r = 0; r < REPETICIONES; r++) {
            long inicio = System.nanoTime();
            lecturaSecuencial(archivo, cantidad);
            tiempos[r] = (System.nanoTime() - inicio) / 1000000.0;   // nanosegundos -> ms
        }
        return medianaDe(tiempos);
    }

    static double medirAleatorio(RandomAccessFile archivo, int cantidad) throws Exception {
        // Las posiciones al azar se preparan ANTES de medir, para no contar ese tiempo.
        Random azar = new Random(12345);
        int[] indices = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            indices[i] = azar.nextInt(TOTAL_REGISTROS);
        }

        double[] tiempos = new double[REPETICIONES];
        for (int r = 0; r < REPETICIONES; r++) {
            long inicio = System.nanoTime();
            lecturaAleatoria(archivo, indices);
            tiempos[r] = (System.nanoTime() - inicio) / 1000000.0;
        }
        return medianaDe(tiempos);
    }

    // ------------------------------------------------------------------
    // PROGRAMA PRINCIPAL
    // ------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        File f = new File(ARCHIVO);
        if (!f.exists() || f.length() != (long) TOTAL_REGISTROS * TAM_REGISTRO) {
            if (!new File(ARCHIVO_CSV).exists()) {
                System.out.println("No existe " + ARCHIVO_CSV + ". Ejecuta primero: node lab02_generador.js");
                return;
            }
            convertirCsvABinario();
        }

        RandomAccessFile archivo = new RandomAccessFile(ARCHIVO, "r");

        // Verificacion: leer directamente el registro 500000 sin pasar por los anteriores
        archivo.seek(500000L * TAM_REGISTRO);
        System.out.println("Verificacion -> registro 500000: id=" + archivo.readInt()
                + ", latitud=" + archivo.readDouble() + ", longitud=" + archivo.readDouble());

        // Calentamiento: la primera ejecucion en Java suele ser mas lenta, no se cuenta.
        medirSecuencial(archivo, 1000);
        medirAleatorio(archivo, 1000);

        System.out.println("\nLatencia de I/O en milisegundos (mediana de " + REPETICIONES + " repeticiones)\n");
        System.out.println("Accesos   | Secuencial (ms) | Aleatorio (ms) | Aleatorio / Secuencial");
        System.out.println("----------|-----------------|----------------|-----------------------");

        PrintWriter csv = new PrintWriter("resultados.csv");
        csv.println("accesos,secuencial_ms,aleatorio_ms");

        for (int i = 0; i < CANTIDADES.length; i++) {
            int cantidad = CANTIDADES[i];
            double secuencial = medirSecuencial(archivo, cantidad);
            double aleatorio = medirAleatorio(archivo, cantidad);

            System.out.printf("%-9d | %15.3f | %14.3f | %15.2fx%n",
                    cantidad, secuencial, aleatorio, aleatorio / secuencial);
            csv.printf("%d,%.3f,%.3f%n", cantidad, secuencial, aleatorio);
        }
        csv.close();
        archivo.close();

        System.out.println("\nResultados guardados en resultados.csv");
        GraficoResultados.main(new String[0]);
    }
}
