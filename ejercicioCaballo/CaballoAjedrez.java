package ejercicioCaballo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaballoAjedrez extends JFrame {
    private static final Map<Integer, int[]> MOVIMIENTOS = new HashMap<>();
    private JTextArea logArea;

    /* Bloque estático que inicializa los movimientos válidos del caballo
       en un teclado numérico. Cada posición tiene un arreglo de enteros
       que representa las posiciones a las que puede moverse el caballo.
    */
    static {
        MOVIMIENTOS.put(0, new int[]{4, 6});
        MOVIMIENTOS.put(1, new int[]{6, 8});
        MOVIMIENTOS.put(2, new int[]{7, 9});
        MOVIMIENTOS.put(3, new int[]{4, 8});
        MOVIMIENTOS.put(4, new int[]{3, 9, 0});
        MOVIMIENTOS.put(5, new int[]{}); // Sin movimientos válidos
        MOVIMIENTOS.put(6, new int[]{1, 7, 0});
        MOVIMIENTOS.put(7, new int[]{2, 6});
        MOVIMIENTOS.put(8, new int[]{1, 3});
        MOVIMIENTOS.put(9, new int[]{2, 4});
    }

    /* Constructor que inicializa la interfaz gráfica para mostrar
       los movimientos del caballo. Configura el área de texto para
       mostrar los resultados, centra la ventana en la pantalla y
       calcula los movimientos posibles.
    */
    public CaballoAjedrez(int movimientos) {
        setTitle("Movimientos del Caballo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        setVisible(true);

        calcularMovimientos(movimientos);
    }

    /* Método calcularMovimientos que recorre todas las posiciones iniciales
       del teclado numérico (0-9) y calcula los movimientos válidos para cada
       una. También registra y muestra los caminos posibles en el área de texto.
    */
    private void calcularMovimientos(int movimientos) {
        int totalMovimientos = 0;

        for (int i = 0; i <= 9; i++) {
            List<List<Integer>> caminos = new ArrayList<>();
            int movimientosDesdePosicion = contarMovimientos(i, movimientos, new ArrayList<>(), caminos);
            totalMovimientos += movimientosDesdePosicion;

            logArea.append("Posición inicial " + i + ": " + movimientosDesdePosicion + " movimientos válidos\n");
            for (List<Integer> camino : caminos) {
                logArea.append("  " + camino + "\n");
            }
        }

        logArea.append("\nTotal de movimientos válidos: " + totalMovimientos + "\n");
    }

    /* Método recursivo contarMovimientos que calcula todos los caminos posibles
       desde una posición inicial con un número dado de movimientos restantes.
       Registra cada camino completo en la lista de caminos y devuelve el número
       total de movimientos válidos.
    */
    private int contarMovimientos(int posicion, int movimientosRestantes,
                                  List<Integer> caminoActual, List<List<Integer>> caminos) {
        if (movimientosRestantes == 0) {
            List<Integer> caminoFinal = new ArrayList<>(caminoActual);
            caminoFinal.add(posicion);
            caminos.add(caminoFinal);
            return 1;
        }

        int count = 0;
        for (int siguiente : MOVIMIENTOS.get(posicion)) {
            List<Integer> nuevoCamino = new ArrayList<>(caminoActual);
            nuevoCamino.add(posicion);
            count += contarMovimientos(siguiente, movimientosRestantes - 1, nuevoCamino, caminos);
        }

        return count;
    }

    /* Método main que solicita al usuario el número de movimientos
       a través de un cuadro de diálogo. Si la entrada es válida,
       crea una nueva instancia de la clase CaballoAjedrez para
       calcular y mostrar los movimientos posibles.
    */
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Introduce el número de movimientos:");
        int movimientos;
        try {
            movimientos = Integer.parseInt(input);
            if (movimientos < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor o igual a 0.");
            return;
        }
        new CaballoAjedrez(movimientos);
    }
}