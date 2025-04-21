package ejercicioReinas;

import javax.swing.*;
import java.awt.*;

public class NReinas extends JFrame {
    private final int n;
    private final int[] posiciones; // Almacena la posición de las reinas en cada fila
    private final JTextArea posicionesArea;

    /* Constructor que inicializa la interfaz gráfica para el problema de las N reinas.
       Configura el tablero, el área de texto para mostrar las posiciones de las reinas,
       y centra la ventana en la pantalla. También resuelve el problema y actualiza
       la interfaz con la solución encontrada o un mensaje de error si no hay solución.
    */
    public NReinas(int n) {
        this.n = n;
        this.posiciones = new int[n];
        setTitle("Problema de las N Reinas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Etiqueta para mostrar el número total de reinas
        JLabel totalReinasLabel = new JLabel("Número total de reinas: " + n, SwingConstants.CENTER);
        totalReinasLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        add(totalReinasLabel, BorderLayout.NORTH);

        // Panel para el tablero
        TableroPanel tableroPanel = new TableroPanel();
        add(tableroPanel, BorderLayout.CENTER);

        // Área de texto para mostrar las posiciones
        posicionesArea = new JTextArea();
        posicionesArea.setEditable(false);
        posicionesArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(posicionesArea), BorderLayout.SOUTH);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Resolver el problema y actualizar la interfaz
        if (resolverNReinas(0)) {
            actualizarPosiciones();
        } else {
            posicionesArea.setText("No se encontró solución.");
        }

        setVisible(true);
    }

    /* Método resolverNReinas que utiliza backtracking para resolver el problema.
       Intenta colocar una reina en cada fila, verificando que no haya conflictos
       con las reinas ya colocadas. Si encuentra una solución válida, retorna true.
    */
    private boolean resolverNReinas(int fila) {
        if (fila == n) {
            return true; // Todas las reinas han sido colocadas
        }

        for (int columna = 0; columna < n; columna++) {
            if (esSeguro(fila, columna)) {
                posiciones[fila] = columna; // Colocar reina
                if (resolverNReinas(fila + 1)) {
                    return true;
                }
                posiciones[fila] = -1; // Retroceder
            }
        }
        return false;
    }

    /* Método esSeguro que verifica si es seguro colocar una reina en una posición
       específica (fila, columna). Comprueba que no haya otra reina en la misma
       columna, ni en las diagonales principal o secundaria.
    */
    private boolean esSeguro(int fila, int columna) {
        for (int i = 0; i < fila; i++) {
            if (posiciones[i] == columna || // Misma columna
                posiciones[i] - i == columna - fila || // Misma diagonal principal
                posiciones[i] + i == columna + fila) { // Misma diagonal secundaria
                return false;
            }
        }
        return true;
    }

    /* Método actualizarPosiciones que actualiza el área de texto con las posiciones
       de las reinas en el formato (fila, columna). Recorre el arreglo de posiciones
       y construye una representación legible para el usuario.
    */
    private void actualizarPosiciones() {
        StringBuilder sb = new StringBuilder("Posiciones de las reinas:\n");
        for (int fila = 0; fila < n; fila++) {
            sb.append("(").append(fila).append(", ").append(posiciones[fila]).append(")\n");
        }
        posicionesArea.setText(sb.toString());
    }

    /* Clase interna TableroPanel que se encarga de dibujar el tablero de ajedrez
       y las reinas en la interfaz gráfica. Alterna los colores de las celdas y
       dibuja un círculo rojo para representar cada reina.
    */
    private class TableroPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int size = Math.min(getWidth(), getHeight()) / n; // Tamaño de cada celda
            for (int fila = 0; fila < n; fila++) {
                for (int columna = 0; columna < n; columna++) {
                    // Alternar colores del tablero
                    if ((fila + columna) % 2 == 0) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect(columna * size, fila * size, size, size);

                    // Dibujar reina si está en esta posición
                    if (posiciones[fila] == columna) {
                        g.setColor(Color.RED);
                        g.fillOval(columna * size + size / 4, fila * size + size / 4, size / 2, size / 2);
                    }
                }
            }
        }
    }

    /* Método main que solicita al usuario el tamaño del tablero (N) a través
       de un cuadro de diálogo. Si la entrada es válida, crea una nueva instancia
       de la clase NReinas para resolver el problema y mostrar la solución.
    */
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Introduce el tamaño del tablero (N):");
        int n;
        try {
            n = Integer.parseInt(input);
            if (n <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor a 0.");
            return;
        }
        new NReinas(n);
    }
}