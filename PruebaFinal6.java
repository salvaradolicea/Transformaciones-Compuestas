
import javax.swing.*;
import java.awt.*;

public class PruebaFinal6 extends JFrame {

    public PruebaFinal6() {
        setTitle("Transformaciones con matriz Compuesta");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PanelCompuesto());
        setVisible(true);
    }

    public static void main(String[] args) {
        new PruebaFinal6();
    }
}

class PanelCompuesto extends JPanel {

    private Compuestas comp = new Compuestas();

    //Coordenadas originales de un triángulo
    private double[] x = {-50, 0, 50};
    private double[] y = {50, -50, 50};

    //Matrices de transformación individuales
    private double[][] T, R, S, Ref, CX;

    //Matriz compuesta final
    private double[][] matrizCompuesta;
    private double[][] puntosOriginales, puntosTransformados;

    public PanelCompuesto() {
        setBackground(Color.WHITE);

        //Definición de transformaciones
        T = comp.T(100, 0);// Traslación
        R = comp.R(45);// Rotación
        S = comp.S(1.5, 0.5);// Escalación
        Ref = comp.refX();// Reflexión respecto al eje X
        CX = comp.CX(0.5);// Inclinación (cizallamiento) en X

        //Crear matriz compuesta
        //El orden de multiplicación importa 
        matrizCompuesta = comp.multiplicarMatrices(
                CX,
                comp.multiplicarMatrices(
                        Ref,
                        comp.multiplicarMatrices(
                                S,
                                comp.multiplicarMatrices(R, T)
                        )
                )
        );

        //Crear matriz de puntos y aplicar transformación 
        puntosOriginales = comp.matrizPuntos(x, y);
        puntosTransformados = comp.transformaPuntos(matrizCompuesta, puntosOriginales);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        int centroX = ancho / 2;
        int centroY = alto / 2;

        // Dibujar ejes del plano 
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(0, centroY, ancho, centroY);
        g2.drawLine(centroX, 0, centroX, alto);

        //Dibujar figura original
        int[] px = comp.actualizarPuntos(puntosOriginales, 0);
        int[] py = comp.actualizarPuntos(puntosOriginales, 1);

        for (int i = 0; i < px.length; i++) {
            px[i] = centroX + px[i];
            py[i] = centroY - py[i];
        }

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(px, py, px.length);

        // Dibujar figura transformada
        int[] pxT = comp.actualizarPuntos(puntosTransformados, 0);
        int[] pyT = comp.actualizarPuntos(puntosTransformados, 1);

        for (int i = 0; i < pxT.length; i++) {
            pxT[i] = centroX + pxT[i];
            pyT[i] = centroY - pyT[i];
        }

        g2.setColor(Color.MAGENTA);
        g2.drawPolygon(pxT, pyT, pxT.length);

        //Texto informativo
        g2.setColor(Color.BLACK);
        g2.drawString("Objeto negro = original", 10, 20);
        g2.drawString("Objeto magenta = transformado con matriz compuesta", 10, 40);
    }
}
