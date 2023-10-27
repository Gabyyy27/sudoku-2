
package lab3_sudoku;

/**
 *
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;


public class Sudoku extends JFrame {

    final int ROWS = 9;
    final int COLUMNS = 9;
    int numerosColocados = 0;

    pos tablero[][];
    JPanel panelTablero;

    ArrayList<pos> cuad1;
    ArrayList<pos> cuad2;
    ArrayList<pos> cuad3;
    ArrayList<pos> cuad4;
    ArrayList<pos> cuad5;
    ArrayList<pos> cuad6;
    ArrayList<pos> cuad7;
    ArrayList<pos> cuad8;
    ArrayList<pos> cuad9;

    public Sudoku() {
        // Instanciar arreglos
        // <editor-fold>
        cuad1 = new ArrayList<pos>();
        cuad2 = new ArrayList<pos>();
        cuad3 = new ArrayList<pos>();
        cuad4 = new ArrayList<pos>();
        cuad5 = new ArrayList<pos>();
        cuad6 = new ArrayList<pos>();
        cuad7 = new ArrayList<pos>();
        cuad8 = new ArrayList<pos>();
        cuad9 = new ArrayList<pos>();

        // </editor-fold>
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelTablero = new JPanel();
        add(panelTablero);
        panelTablero.setLayout(new GridLayout(ROWS, COLUMNS));
        tablero = new pos[ROWS][COLUMNS];

        llenarTablero();
        generarNumerosAleatorios();
        setVisible(true);

    }

    
    private void guardarEnCuadrante(pos casilla) {
        int row = casilla.getRow();
        int col = casilla.getColumn();

        ArrayList<pos> cuadrante = getCuadranteFromPos(row, col);
        cuadrante.add(casilla);

        if (cuadrante == cuad1 || cuadrante == cuad3 || cuadrante == cuad5 || cuadrante == cuad7 || cuadrante == cuad9) {
            casilla.getLabel().setBackground(Color.green);
        }

    }

    private boolean esValidoFila(int num, int row) {
        for (int c = 0; c < COLUMNS; c++) {
            if (tablero[row][c].getCurrentNumber() == num) {
                return false;
            }
        }

        return true;
    }

    private boolean esValidoColumna(int num, int col) {
        for (int r = 0; r < COLUMNS; r++) {
            if (tablero[r][col].getCurrentNumber() == num) {
                return false;
            }
        }

        return true;
    }

    private pos getCasillaFromPos(int row, int col) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                pos casilla = tablero[r][c];

                if (casilla.getRow() == row && casilla.getColumn() == col) {
                    return casilla;
                }
            }
        }

        return null;
    }

    private ArrayList<pos> getCuadranteFromPos(int row, int col) {
        if (row <= 2) {
            if (col <= 2) {
                return cuad1;
            } else if (col <= 5) {
                return cuad2;
            } else {
                return cuad3;
            }

        } else if (row <= 5) {
            if (col <= 2) {
                return cuad4;

            } else if (col <= 5) {
                return cuad5;
            } else {
                return cuad6;

            }
        } else {
            if (col <= 2) {
                return cuad7;

            } else if (col <= 5) {
                return cuad8;
            } else {
                return cuad9;

            }
        }
    }

    private boolean esValidoCuadrante(int num, int row, int col) {
        ArrayList<pos> cuad = getCuadranteFromPos(row, col);

        for (pos casilla : cuad) {
            if (casilla.getCurrentNumber() == num) {
                return false;
            }
        }
        return true;
    }

    private void llenarTablero() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                pos casillaClickeada = null;
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLUMNS; c++) {
                        if (tablero[r][c].getLabel() == e.getSource()) {
                            casillaClickeada = tablero[r][c];
                            break;
                        }
                    }
                }
                
                if (casillaClickeada.canChange()) {
                    administrarClick(casillaClickeada);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede cambiar el valor de las casillas", "ERROR", JOptionPane.ERROR_MESSAGE);
                }    
            }
        };

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                tablero[r][c] = new pos(r, c);
                tablero[r][c].getLabel().addMouseListener(mouseAdapter);
                panelTablero.add(tablero[r][c].getLabel());
                guardarEnCuadrante(tablero[r][c]);
            }
        }

    }

    private void administrarClick(pos casillaClickeada) {
        int n;
        do {
            
            try{
                n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un numero "));
                
                if (n <= 0 || n >= 10) {
                    JOptionPane.showMessageDialog(null, "Solo se permiten numeros del 1 al 9");

                }
                
                if (n == 0) {
                    casillaClickeada.setCurrentNumber(n);
                    return;
                }
                
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Solo se permiten numeros del 1 al 9");
                n = -1;
            }
        } while (n <= 0 || n>=10);
        
        if (esNumeroValido(n, casillaClickeada.getColumn(), casillaClickeada.getRow())) {
            casillaClickeada.setCurrentNumber(n);
            numerosColocados++;
            verificarWin();
        } else {
            JOptionPane.showMessageDialog(this, "El numero esta repetido.", "ERROR", JOptionPane.ERROR_MESSAGE);
        };
        
        
    }

    private boolean esNumeroValido(int num, int col, int row) {
        return (esValidoCuadrante(num, row, col)
                && esValidoFila(num, row)
                && esValidoColumna(num, col));

    }

    private void generarNumerosAleatorios() {
        Random random = new Random();
        int numerosPendientes = 20;
        boolean esValido;
        
        // iterar cada columna y generar una cantidad aleatoria en esa columna
        for (int c = 0; c < COLUMNS; c++) {
            
            int cantidadNumerosEnColumna = random.nextInt(2, 5);
            
            if (numerosPendientes - cantidadNumerosEnColumna < 0) {
                cantidadNumerosEnColumna = numerosPendientes;
            }
            
            
            
            
            for (int i = 0; i < cantidadNumerosEnColumna; i++) {
                do {
                    
                    int filaSeleccionada = random.nextInt(0, 9);
                    int numeroGenerado = random.nextInt(1, 10);
                    
                    esValido = (esValidoCuadrante(numeroGenerado, filaSeleccionada, c)
                            && esValidoFila(numeroGenerado, filaSeleccionada)
                            && esValidoColumna(numeroGenerado, c)) && tablero[filaSeleccionada][c].getCurrentNumber() == -1;

                    
                    
                    if (esValido) {
                        tablero[filaSeleccionada][c].setCurrentNumber(numeroGenerado);
                        tablero[filaSeleccionada][c].setChange(false);
                        numerosPendientes--;
                        
                        numerosColocados++; 
                    }

                } while (!esValido);

            }

        }
        
        
    }
    
    private void verificarWin() {
        if (numerosColocados == 81) {            
            JOptionPane.showMessageDialog(null, "¡Felicidades! Ha ganado");
            dispose();
        }
        
    }

    public static void main(String[] args) {
        new Sudoku();
    }
}
