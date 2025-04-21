package torresHanoi;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class TorresHanoi extends JFrame {
    private final int numDiscos;
    private final Torre[] torres;
    private final int discoAltura = 20;
    private final int discoAnchoBase = 200;
    private final int discoAnchoIncremento = 20;
    private int movimientos = 0;

    /* El constructor inicializa el juego de las Torres de Hanoi.
       Configura las torres, coloca los discos en la primera torre,
       y establece las propiedades de la ventana,
       como el tamaño, el título y la posición centrada en la pantalla.
       También inicia un hilo para resolver el problema de forma animada.
    */
    public TorresHanoi(int numDiscos) {
        this.numDiscos = numDiscos;
        this.torres = new Torre[3];
        for (int i = 0; i < 3; i++) {
            torres[i] = new Torre();
        }
        for (int i = 1; i <= numDiscos; i++) {
            torres[0].push(i);
        }

        setTitle("Torres de Hanoi");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        setVisible(true);

        new Thread(this::resolverHanoi).start();
    }

    /* El metodo resolverHanoi inicia la resolución del problema de las Torres de Hanoi.
       Llama al metodo recursivo moverDiscos para mover todos los discos de la torre inicial (0) a la torre final (2),
       utilizando la torre auxiliar (1).
    */
    private void resolverHanoi() {
        moverDiscos(numDiscos, 0, 2, 1);
    }

    /* El metodo recursivo moverDiscos implementa la lógica de las Torres de Hanoi.
       Si hay discos por mover, primero mueve n-1 discos de la torre origen a la torre auxiliar,
       luego mueve el disco más grande a la torre destino, y finalmente mueve los n-1 discos de la torre auxiliar
       a la torre destino. Cada movimiento actualiza la interfaz gráfica y añade una pausa para la animación.
    */
    private void moverDiscos(int n, int origen, int destino, int auxiliar) {
        if (n > 0) {
            moverDiscos(n - 1, origen, auxiliar, destino);
            torres[destino].push(torres[origen].pop());
            movimientos++;
            repaint();
            try {
                Thread.sleep(500); // Pausa para animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moverDiscos(n - 1, auxiliar, destino, origen);
        }
    }

    /* El etodo paint dibuja las torres y los discos en la ventana.
       Calcula las posiciones y tamaños de los discos en función de su tamaño y la torre a la que pertenecen.
       También muestra el número de movimientos realizados en la parte superior izquierda de la ventana.
       Este metodo se llama automáticamente cada vez que se actualiza la interfaz gráfica.
    */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int torreAncho = getWidth() / 3;
        int torreAltura = getHeight() - 100;

        for (int i = 0; i < 3; i++) {
            int x = i * torreAncho + torreAncho / 2;
            g.fillRect(x - 5, 100, 10, torreAltura); // Draw poles

            Stack<Integer> discos = torres[i].getDiscos();
            int y = getHeight() - 20;
            for (int disco : discos) {
                int ancho = discoAnchoBase - (disco - 1) * discoAnchoIncremento;

                // Color único para cada disco
                g.setColor(new Color(50 + disco * 20, 100 + disco * 10, 150 + disco * 5));
                g.fillRect(x - ancho / 2, y - discoAltura, ancho, discoAltura);

                y -= discoAltura;
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Movimientos: " + movimientos, 10, 50);
    }

    /* La clase interna Torre representa una torre en el juego.
       Utiliza una pila (Stack) para almacenar los discos.
       Proporciona métodos para agregar discos (push), retirar discos (pop) y obtener una copia de los discos actuales.
    */
    private static class Torre {
        private final Stack<Integer> discos = new Stack<>();

        public void push(int disco) {
            discos.push(disco);
        }

        public int pop() {
            return discos.pop();
        }

        public Stack<Integer> getDiscos() {
            return (Stack<Integer>) discos.clone();
        }
    }

    /* El metodo main solicita al usuario el número de discos a través de un cuadro de diálogo.
       Si la entrada es válida, crea una nueva instancia del juego con el número de discos especificado.
    */
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Introduce el número de discos:");
        int numDiscos;
        try {
            numDiscos = Integer.parseInt(input);
            if (numDiscos <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor a 0.");
            return;
        }
        new TorresHanoi(numDiscos);
    }
}