import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class PruebaPasoPaso5 extends JFrame {
    public PruebaPasoPaso5(){
        setTitle("Prueba Paso a Paso 5");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        add(new Panel5());
        setVisible(true);
    }
    public static void main(String [] args){
        new PruebaPasoPaso5();
    }

}

class Panel5 extends JPanel implements ActionListener{
    private Timer timer;
    private Compuestas comp = new Compuestas();

    // Coordenadas originales de un triángulo
    private double[] x = {-50, 0, 50};
    private double[] y = {50, -50, 50};

    // Matriz de puntos original y lista de resultados
    private double[][] puntosOriginal;
    private double[][] puntosTransformados;

    private int paso = 0;

    // Colores distintos por transformación
    private Color[] colores = {
        Color.BLACK, Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE, Color.GREEN
    };

    public Panel5() {
        setBackground(Color.WHITE);
        puntosOriginal = comp.matrizPuntos(x, y);
        puntosTransformados = puntosOriginal;
        timer = new Timer(1500, this); // cada 1.5 segundos
        timer.start();
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

        // Dibujar plano cartesiano
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(0, centroY, ancho, centroY);
        g2.drawLine(centroX, 0, centroX, alto);

        // Dibujar figura original
        int[] pxOriginal = comp.actualizarPuntos(puntosOriginal, 0);
        int[] pyOriginal = comp.actualizarPuntos(puntosOriginal, 1);

        for (int i = 0; i < pxOriginal.length; i++) {
            pxOriginal[i] = centroX + pxOriginal[i];
            pyOriginal[i] = centroY - pyOriginal[i];
        }

        g2.setStroke(new BasicStroke(2));
        g2.setColor(colores[0]);
        g2.drawPolygon(pxOriginal, pyOriginal, pxOriginal.length);

        // Dibujar figura transformada (acumulada)
        if (paso > 0) {
            int[] px = comp.actualizarPuntos(puntosTransformados, 0);
            int[] py = comp.actualizarPuntos(puntosTransformados, 1);

            for (int i = 0; i < px.length; i++) {
                px[i] = centroX + px[i];
                py[i] = centroY - py[i];
            }

            g2.setColor(colores[Math.min(paso, colores.length - 1)]);
            g2.drawPolygon(px, py, px.length);
        }

        g2.setColor(Color.BLACK);
        g2.drawString("Paso actual: " + paso, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double[][] transformacion = null;

        switch (paso) {
            case 0:
                // Traslación
                transformacion = comp.T(100, 0);
                break;
            case 1:
                // Rotación
                transformacion = comp.R(45);
                break;
            case 2:
                // Escalación
                transformacion = comp.S(1.5, 0.5);
                break;
            case 3:
                // Reflexión respecto al eje X
                transformacion = comp.refX();
                break;
            case 4:
                // Inclinación en X
                transformacion = comp.CX(0.5);
                break;
            default:
                timer.stop();
                return;
        }

        puntosTransformados = comp.transformaPuntos(transformacion, puntosTransformados);
        paso++;
        repaint();
    }
}
