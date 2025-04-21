import javax.swing.*;
import java.awt.*;
import ejercicioCaballo.CaballoAjedrez;
import torresHanoi.TorresHanoi;
import ejercicioReinas.NReinas;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Botón para el juego Caballo Ajedrez
        JButton caballoAjedrezButton = new JButton("Caballo Ajedrez");
        caballoAjedrezButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Introduce el número de movimientos:");
            int movimientos;
            try {
                movimientos = Integer.parseInt(input);
                if (movimientos < 0) throw new NumberFormatException();
                new CaballoAjedrez(movimientos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor o igual a 0.");
            }
        });
        add(caballoAjedrezButton);

        // Botón para el juego Torres de Hanoi
        JButton torresHanoiButton = new JButton("Torres de Hanoi");
        torresHanoiButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Introduce el número de discos:");
            int numDiscos;
            try {
                numDiscos = Integer.parseInt(input);
                if (numDiscos <= 0) throw new NumberFormatException();
                new TorresHanoi(numDiscos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor a 0.");
            }
        });
        add(torresHanoiButton);

        // Botón para el juego N Reinas
        JButton nReinasButton = new JButton("N Reinas");
        nReinasButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Introduce el tamaño del tablero (N):");
            int n;
            try {
                n = Integer.parseInt(input);
                if (n <= 0) throw new NumberFormatException();
                new NReinas(n);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido mayor a 0.");
            }
        });
        add(nReinasButton);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}