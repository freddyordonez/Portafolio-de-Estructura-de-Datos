import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;

/**
 * Lee resultados.csv y dibuja dos graficos como imagenes PNG:
 *   - grafico_barras.png   (barras agrupadas)
 *   - grafico_lineas.png   (lineas)
 *
 * Se ejecuta solo al final de AccesoArchivos, o por separado con:
 *   java GraficoResultados
 */
public class GraficoResultados {

    static final int ANCHO = 900;
    static final int ALTO = 560;
    static final int MARGEN_IZQ = 90;
    static final int MARGEN_DER = 40;
    static final int MARGEN_SUP = 70;
    static final int MARGEN_INF = 90;

    static final Color VERDE = new Color(31, 122, 77);    // secuencial
    static final Color ROJO = new Color(200, 83, 30);     // aleatorio

    // Datos leidos del CSV
    static int[] accesos;
    static double[] secuencial;
    static double[] aleatorio;
    static double maximo;   // valor mas alto del eje Y

    // ------------------------------------------------------------------
    static void leerCsv() throws Exception {
        BufferedReader lector = new BufferedReader(new FileReader("resultados.csv"));
        lector.readLine();                        // se salta la cabecera

        int filas = 0;
        String linea;
        // Primero se cuentan las filas
        BufferedReader contador = new BufferedReader(new FileReader("resultados.csv"));
        contador.readLine();
        while (contador.readLine() != null) {
            filas++;
        }
        contador.close();

        accesos = new int[filas];
        secuencial = new double[filas];
        aleatorio = new double[filas];

        int i = 0;
        while ((linea = lector.readLine()) != null) {
            String[] partes = linea.split(",");
            accesos[i] = Integer.parseInt(partes[0]);
            secuencial[i] = Double.parseDouble(partes[1]);
            aleatorio[i] = Double.parseDouble(partes[2]);
            if (secuencial[i] > maximo) maximo = secuencial[i];
            if (aleatorio[i] > maximo) maximo = aleatorio[i];
            i++;
        }
        lector.close();

        // Se redondea el maximo hacia arriba para que el eje quede "limpio"
        maximo = Math.ceil(maximo / 10.0) * 10.0;
    }

    // ------------------------------------------------------------------
    // Partes comunes: fondo, titulo, ejes y leyenda
    // ------------------------------------------------------------------
    static int convertirY(double valor) {
        int alturaUtil = ALTO - MARGEN_SUP - MARGEN_INF;
        return (int) (ALTO - MARGEN_INF - (valor / maximo) * alturaUtil);
    }

    static void dibujarBase(Graphics2D g, String titulo) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ANCHO, ALTO);

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString(titulo, MARGEN_IZQ, 35);

        // Lineas horizontales del fondo y valores del eje Y
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (int i = 0; i <= 5; i++) {
            double valor = maximo * i / 5.0;
            int y = convertirY(valor);
            g.setColor(new Color(225, 225, 225));
            g.drawLine(MARGEN_IZQ, y, ANCHO - MARGEN_DER, y);
            g.setColor(Color.DARK_GRAY);
            g.drawString(String.format("%.0f", valor), MARGEN_IZQ - 40, y + 5);
        }

        // Titulos de los ejes
        g.setColor(Color.DARK_GRAY);
        g.drawString("Latencia total de I/O (ms)", 10, 55);
        g.drawString("Numero de registros leidos", ANCHO / 2 - 70, ALTO - 20);

        // Leyenda
        g.setColor(VERDE);
        g.fillRect(ANCHO - 260, 20, 14, 14);
        g.setColor(Color.BLACK);
        g.drawString("Secuencial", ANCHO - 240, 32);
        g.setColor(ROJO);
        g.fillRect(ANCHO - 140, 20, 14, 14);
        g.setColor(Color.BLACK);
        g.drawString("Aleatorio", ANCHO - 120, 32);
    }

    static Graphics2D prepararGraficos(BufferedImage imagen) {
        Graphics2D g = imagen.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g;
    }

    // ------------------------------------------------------------------
    // Grafico de barras
    // ------------------------------------------------------------------
    static void graficoBarras() throws Exception {
        BufferedImage imagen = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = prepararGraficos(imagen);
        dibujarBase(g, "Latencia de I/O: acceso secuencial vs. aleatorio");

        int anchoGrupo = (ANCHO - MARGEN_IZQ - MARGEN_DER) / accesos.length;
        int anchoBarra = 70;
        int base = convertirY(0);

        for (int i = 0; i < accesos.length; i++) {
            int centro = MARGEN_IZQ + anchoGrupo * i + anchoGrupo / 2;

            int ySec = convertirY(secuencial[i]);
            g.setColor(VERDE);
            g.fillRect(centro - anchoBarra - 3, ySec, anchoBarra, base - ySec);

            int yAle = convertirY(aleatorio[i]);
            g.setColor(ROJO);
            g.fillRect(centro + 3, yAle, anchoBarra, base - yAle);

            // Valores encima de cada barra y etiqueta del grupo
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 13));
            g.drawString(String.format("%.2f", secuencial[i]), centro - anchoBarra - 3, ySec - 6);
            g.drawString(String.format("%.2f", aleatorio[i]), centro + 3, yAle - 6);
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g.drawString(String.valueOf(accesos[i]), centro - 20, base + 25);
        }
        g.dispose();
        ImageIO.write(imagen, "png", new File("grafico_barras.png"));
    }

    // ------------------------------------------------------------------
    // Grafico de lineas
    // ------------------------------------------------------------------
    static void graficoLineas() throws Exception {
        BufferedImage imagen = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = prepararGraficos(imagen);
        dibujarBase(g, "Latencia de I/O: acceso secuencial vs. aleatorio");

        // Posiciones X: los puntos se reparten por igual (1.000, 10.000, 100.000)
        int paso = (ANCHO - MARGEN_IZQ - MARGEN_DER) / accesos.length;
        int[] x = new int[accesos.length];
        for (int i = 0; i < accesos.length; i++) {
            x[i] = MARGEN_IZQ + paso * i + paso / 2;
        }

        g.setStroke(new BasicStroke(3f));
        for (int i = 0; i < accesos.length - 1; i++) {
            g.setColor(VERDE);
            g.drawLine(x[i], convertirY(secuencial[i]), x[i + 1], convertirY(secuencial[i + 1]));
            g.setColor(ROJO);
            g.drawLine(x[i], convertirY(aleatorio[i]), x[i + 1], convertirY(aleatorio[i + 1]));
        }

        for (int i = 0; i < accesos.length; i++) {
            g.setColor(VERDE);
            g.fillOval(x[i] - 6, convertirY(secuencial[i]) - 6, 12, 12);
            g.setColor(ROJO);
            g.fillOval(x[i] - 6, convertirY(aleatorio[i]) - 6, 12, 12);

            g.setFont(new Font("SansSerif", Font.BOLD, 13));
            g.setColor(VERDE);
            g.drawString(String.format("%.2f", secuencial[i]), x[i] - 15, convertirY(secuencial[i]) + 24);
            g.setColor(ROJO);
            g.drawString(String.format("%.2f", aleatorio[i]), x[i] - 15, convertirY(aleatorio[i]) - 12);

            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(accesos[i]), x[i] - 20, convertirY(0) + 45);
        }
        g.dispose();
        ImageIO.write(imagen, "png", new File("grafico_lineas.png"));
    }

    // ------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");   // no abre ventanas, solo guarda PNG
        leerCsv();
        graficoBarras();
        graficoLineas();
        System.out.println("Graficos guardados: grafico_barras.png y grafico_lineas.png");
    }
}
