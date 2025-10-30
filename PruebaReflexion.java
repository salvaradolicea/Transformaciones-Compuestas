import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PruebaReflexion extends JFrame {

    public PruebaReflexion() {
        setTitle("Prueba de Reflexión");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregamos el panel que hará las transformaciones
        add(new PanelRef());

        setVisible(true);
    }

    public static void main(String[] args) {
        new PruebaReflexion();
    }
}

class PanelRef extends JPanel implements ActionListener {
    private Timer timer;
    private Compuestas comp = new Compuestas();

    // Coordenadas iniciales (un triángulo)
    private double[] x = {-50, 0, 50};
    private double[] y = {50, -50, 50};

    // Matriz de puntos homogéneos
    private double[][] puntos;
    private int paso = 0; // Control de los pasos

    // Constructor del panel
    public PanelRef() {
        setBackground(Color.WHITE);
        puntos = comp.matrizPuntos(x, y);
        timer = new Timer(1500, this); // un paso cada 1.5 segundos
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

        // Dibujar ejes del plano
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(0, centroY, ancho, centroY);
        g2.drawLine(centroX, 0, centroX, alto);

        // Dibujar el triángulo transformado
        int[] px = comp.actualizarPuntos(puntos, 0);
        int[] py = comp.actualizarPuntos(puntos, 1);

        for (int i = 0; i < px.length; i++) {
            px[i] = centroX + px[i];
            py[i] = centroY - py[i];
        }

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLUE);
        g2.fillPolygon(px, py, px.length);

        // Texto del paso actual
        g2.setColor(Color.BLACK);
        g2.drawString("Paso actual: " + paso, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double[][] transformacion = null;

        switch (paso) {
            case 0:
                // Reflexión respecto al eje X
                transformacion = comp.refX();
                break;
            case 1:
                // Reflexión respecto al eje Y
                transformacion = comp.refY();
                break;
            case 2:
                // Reflexión respecto a la línea y = x
                transformacion = comp.refYigualX();
                break;
            case 3:
                // Reflexión respecto a la línea y = -x
                transformacion = comp.refYIgualMenosX();
                break;
            case 4:
                // Reflexión respecto al origen
                transformacion = comp.refOrigen();
                break;
            default:
                timer.stop();
                return;
        }

        // Aplicar la transformación a los puntos
        puntos = comp.transformaPuntos(transformacion, puntos);
        paso++;
        repaint();
    }
}
