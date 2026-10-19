
import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class Funcoes {
    public final static Scanner TECLADO = new Scanner(System.in);

    public static int lerValorInt() {
        int valor;
        valor = TECLADO.nextInt();
        return valor;
    }

    public static String[][] criarMatrizInt(int linhas, int colunas, String ponto) {
        String[][] mat = new String[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mat[i][j] = ponto;
            }
        }
        return mat;
    }

    public static void imprimirMatriz(String[][] mat) {
        int linhas, colunas;
        linhas = mat.length;
        colunas = mat[0].length;
        System.out.print("\n");

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("%s\t", mat[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static boolean verificarMovimentoA(int coluna, String[][] mat) {
        boolean verificao = false;
        if (coluna - 1 >= 0) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoD(int coluna, String[][] mat) {
        boolean verificao = false;
        if (coluna + 1 < mat[0].length) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoW(int linha, String[][] mat) {
        boolean verificao = false;
        if (linha - 1 >= 0) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoS(int linha, String[][] mat) {
        boolean verificao = false;
        if (linha + 1 < mat.length) {
            verificao = true;
        }
        return verificao;
    }

    public static void movimentacao(String mat[][]) {
        int tecla;
        int i = 0;
        int j = 0;
        imprimirMatriz(mat);
        while (true) {
            tecla = BibliotecaTCA.obtemTeclaPressionada();
            if (tecla == 'a' && verificarMovimentoA(j, mat)) {
                j--;
                mat[i][j] = "# ";
                mat[i][j + 1] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 'w' && verificarMovimentoW(i, mat)) {
                i--;
                mat[i][j] = "# ";
                mat[i + 1][j] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 'd' && verificarMovimentoD(j, mat)) {
                j++;
                mat[i][j] = "# ";
                mat[i][j - 1] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 's' && verificarMovimentoS(i, mat)) {
                i++;
                mat[i][j] = "# ";
                mat[i - 1][j] = ". ";
                imprimirMatriz(mat);
            }
            if(mat[mat.length-1][mat[0].length-1] == mat[i][j]){ {
                break;
            }
            
        }
    }

}

    public static void main(String[] args) {
        String[][] mat1 = {
                { "# ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", "[] " },
        };
        movimentacao(mat1);
        // BibliotecaTCA.mudarCor(2);
        //imprimirMatriz(mat1);
    }
}

import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class Funcoes {
    public final static Scanner TECLADO = new Scanner(System.in);

    public static int lerValorInt() {
        int valor;
        valor = TECLADO.nextInt();
        return valor;
    }

    public static String[][] criarMatrizInt(int linhas, int colunas, String ponto) {
        String[][] mat = new String[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mat[i][j] = ponto;
            }
        }
        return mat;
    }

    public static void imprimirMatriz(String[][] mat) {
        int linhas, colunas;
        linhas = mat.length;
        colunas = mat[0].length;
        System.out.print("\n");

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("%s\t", mat[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static boolean verificarMovimentoA(int coluna, String[][] mat) {
        boolean verificao = false;
        if (coluna - 1 >= 0) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoD(int coluna, String[][] mat) {
        boolean verificao = false;
        if (coluna + 1 < mat[0].length) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoW(int linha, String[][] mat) {
        boolean verificao = false;
        if (linha - 1 >= 0) {
            verificao = true;
        }
        return verificao;
    }

    public static boolean verificarMovimentoS(int linha, String[][] mat) {
        boolean verificao = false;
        if (linha + 1 < mat.length) {
            verificao = true;
        }
        return verificao;
    }

    public static void movimentacao(String mat[][]) {
        int tecla;
        int i = 0;
        int j = 0;
        imprimirMatriz(mat);
        while (true) {
            tecla = BibliotecaTCA.obtemTeclaPressionada();
            if (tecla == 'a' && verificarMovimentoA(j, mat)) {
                j--;
                mat[i][j] = "# ";
                mat[i][j + 1] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 'w' && verificarMovimentoW(i, mat)) {
                i--;
                mat[i][j] = "# ";
                mat[i + 1][j] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 'd' && verificarMovimentoD(j, mat)) {
                j++;
                mat[i][j] = "# ";
                mat[i][j - 1] = ". ";
                imprimirMatriz(mat);
            } else if (tecla == 's' && verificarMovimentoS(i, mat)) {
                i++;
                mat[i][j] = "# ";
                mat[i - 1][j] = ". ";
                imprimirMatriz(mat);
            }
            if(mat[mat.length-1][mat[0].length-1] == mat[i][j]){ {
                break;
            }
            
        }
    }

}

    public static void main(String[] args) {
        String[][] mat1 = {
                { "# ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", "[] " },
        };
        movimentacao(mat1);
        // BibliotecaTCA.mudarCor(2);
        //imprimirMatriz(mat1);
    }
}

