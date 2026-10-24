import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

//teste
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


    public static void desenharTela(int x, int y, int largura, int altura, String mat[][]) {
        System.out.println();
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (i == y && j == x) {
                    BibliotecaTCA.mudarCor(1);
                    System.out.printf("%s\t", mat[i][j]);
                } else {
                    BibliotecaTCA.mudarCor(-1);
                    System.out.printf("%s\t", mat[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("Use A, D, W, S para mover o personagem. Pressione ESC para sair.");
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void darEnter(){
        for(int i = 0; i < 15; i++){
            System.out.print("\n");
        }
    }

    public static void movimentacao(String mat[][], boolean matcop[][]) {
        int tecla;
        int i = 0;
        int j = 0;
        int linhas = mat.length;
        int colunas = mat[0].length;
        desenharTela(j, i, colunas, linhas, mat);
        while (i != linhas - 1&& j != colunas - 1) {
            // Lógica para detectar o movimento
            if (BibliotecaTCA.pressionouTecla()) {
                tecla = BibliotecaTCA.obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        j--;
                        if (j < 0){
                            j = 0;}
                            else if (matcop[i][j] == false) {
                            mat[i][j] = "_ ";
                            j++;
                        }else {
                            mat[i][j] = "# ";
                            mat[i][j + 1] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat);
                        break;
                    case 'd':
                    case 'D':
                        j++;
                        if (j > colunas - 1) {
                            j = colunas - 1;
                        } else if (matcop[i][j] == false) {
                            mat[i][j] = "_ ";
                            j--;
                        }else {
                            mat[i][j] = "# ";
                            mat[i][j - 1] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat);
                        break;
                    case 'w':
                    case 'W':
                        i--;

                        if (i < 0) {
                            i = 0;
                        } else if (matcop[i][j] == false) {
                            mat[i][j] = "_ ";
                            i++;
                        } else {
                            mat[i][j] = "# ";
                            mat[i + 1][j] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat);
                        break;
                    case 's':
                    case 'S':
                        i++;
                        if (i > linhas - 1) {
                            i = linhas - 1;
                        } else if (matcop[i][j] == false) {
                            mat[i][j] = "_ ";
                            i--;

                        } else {
                            mat[i][j] = "# ";
                            mat[i - 1][j] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat);
                        break;
                    case 27: // Código ASCII para a tecla ESC
                        return; // Sai do loop e termina o programa
                }
            }

            limparConsole();
        }

    }

    public static void main(String[] args) {
        String[][] mat1 = {
                { "# ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", "[] " }
        };
        boolean[][] matcop = {
                { true, false, true, true, true, false },
                { true, true, true, false, true, true },
                { true, true, false, true, true, false },
                { false, false, true, true, false, false },
                { true, false, true, false, true, true },
                { true, false, true, true, true, true }
        };
        movimentacao(mat1, matcop);
        // BibliotecaTCA.mudarCor(2);
        // imprimirMatriz(mat1);
    }
}
