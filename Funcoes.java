import java.util.Random;
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

     // Método que imprime o texto letra por letra
    public static void imprimirDevagar(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            System.out.print(texto.charAt(i)); 
            System.out.flush();                
            
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static int sortearNumero(int limite) {
        int valor;
        valor = SORTEIO.nextInt(limite);
        return valor;
    }

    public final static Random SORTEIO = new Random();

    public static String[][] criarMatrizInt(int linhas, int colunas, String ponto) {
        String[][] mat = new String[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mat[i][j] = ponto;
            }
        }
        return mat;
    }

    public static String lerTexto() {
        String valor;
        valor = TECLADO.next();
        return valor;
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
        BibliotecaTCA.mudarCor(-1);
        System.out.println("Use A, D, W, S para mover o personagem. Pressione ESC para sair.");
    }

    public static void desenharTelaTesouro(int x, int y, int largura, int altura, String mat[][]) {
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
        BibliotecaTCA.mudarCor(-1);
        System.out.println("Aperte Q para ver area quente, M para ver area morna, F para ver area fria");
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void puzzle1(String mat[][], boolean matcop[][]) {
        int tecla;
        int i = 0;
        int j = 0;
        int linhas = mat.length;
        int colunas = mat[0].length;
        desenharTela(j, i, colunas, linhas, mat);
        while (true) {
            // Lógica para detectar o movimento
            if (i == linhas - 1 && j == colunas - 1) {
                break;
            }
            if (BibliotecaTCA.pressionouTecla()) {
                tecla = BibliotecaTCA.obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        j--;
                        if (j < 0) {
                            j = 0;
                        } else if (matcop[i][j] == false) {
                            mat[i][j] = "| ";
                            j++;
                        } else {
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
                            mat[i][j] = "| ";
                            j--;
                        } else {
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

        }

    }

    public static int[] sortearPosTesouro(int i, int j, int linhas, int colunas) {
        int[] posicao = new int[2];
        do {
            posicao[0] = SORTEIO.nextInt(linhas);
            posicao[1] = SORTEIO.nextInt(colunas);
        } while (posicao[0] == i && posicao[1] == j);

        return posicao;
    }

    public static String[][] posicionarTesouro(int[] posicao, String[][] matcop2) {
        matcop2[posicao[0]][posicao[1]] = "<tesouro>";
        return matcop2;
    }

    public static int[] calcularDistTesouro(int[] pos, int i, int j) {
        int[] distTesouro = new int[2];
        distTesouro[0] = Math.abs(pos[0] - i);
        distTesouro[1] = Math.abs(pos[1] - j);
        return distTesouro;
    }

    public static void exibirQuenteOuFrio(int[] distTesouro) {
        if (distTesouro[0] <= 1 && distTesouro[1] <= 1) {
            System.out.println("Quente!");
        } else if (distTesouro[0] < 3 || distTesouro[1] < 3) {
            System.out.println("Morno!");
        } else {
            System.out.println("Frio!");
        }
    }

    public static void exibirAreaQuenteOuFria(int[] distTesouro) {
        if (distTesouro[0] <= 1 && distTesouro[1] <= 1) {

        } else if (distTesouro[0] < 3 || distTesouro[1] < 3) {

        } else {

        }
    }

    public static void puzzle2(String mat2[][], String matcop2[][]) {
        int tecla;
        int linhas = mat2.length;
        int colunas = mat2[0].length;
        int[] distTesouro = new int[2];
        int j = colunas / 2;
        int i = linhas / 2;
        int[] pos = sortearPosTesouro(i, j, linhas, colunas);
        matcop2 = posicionarTesouro(pos, matcop2);
        desenharTela(j, i, colunas, linhas, mat2);

        while (true) {
            // Lógica para detectar o movimento

            if (BibliotecaTCA.pressionouTecla()) {
                tecla = BibliotecaTCA.obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        j--;
                        if (j < 0) {
                            j = 0;
                        } else {
                            mat2[i][j] = "# ";
                            mat2[i][j + 1] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat2);
                        break;
                    case 'd':
                    case 'D':
                        j++;
                        if (j > colunas - 1) {
                            j = colunas - 1;
                        } else {
                            mat2[i][j] = "# ";
                            mat2[i][j - 1] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat2);
                        break;
                    case 'w':
                    case 'W':
                        i--;

                        if (i < 0) {
                            i = 0;
                        } else {
                            mat2[i][j] = "# ";
                            mat2[i + 1][j] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat2);
                        break;
                    case 's':
                    case 'S':
                        i++;
                        if (i > linhas - 1) {
                            i = linhas - 1;
                        } else {
                            mat2[i][j] = "# ";
                            mat2[i - 1][j] = ". ";
                        }
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat2);
                        break;
                    case 13:
                        if (i == pos[0] && j == pos[1]) {
                            System.out.println("Parabéns! Você encontrou o tesouro!");
                            break;
                        } else {
                            distTesouro = calcularDistTesouro(pos, i, j);
                            exibirQuenteOuFrio(distTesouro);
                            continue;
                        }
                    case 27: // Código ASCII para a tecla ESC
                        return; // Sai do loop e termina o programa
                    default:
                }
            }

        }

    }

    public static boolean[] preencherVet(boolean[] vet){
        for(int i = 0;i < vet.length;i++){
            vet[i] = true;
        }
        return vet;
    }

    public static String formatarPalavra(String palavra,int letras){
        char[] letrasFormatadas = new char[letras];
        for(int i = 0;i < letras;i++){
            letrasFormatadas[i] = palavra.charAt(i);
        }
        String palavraFormatada = new String(letrasFormatadas);
        return palavraFormatada;
    }

    public static void puzzle3(String[] senhas) {
        int letrasCorretas = 0;
        int letrasConfirmadas = 0;
        int j = 0;
        while (j < senhas.length) {
            String senhaAtual = senhas[j];
            boolean[] vetorControle = new boolean[senhaAtual.length()];
            vetorControle = preencherVet(vetorControle);
            System.out.println("A senha atual possui " + senhaAtual.length() + " digitos");
            String senhaInserida = lerTexto();
            senhaInserida = formatarPalavra(senhaInserida, senhaAtual.length());
            for (int i = 0; i < senhaInserida.length(); i++) {
                if (senhaInserida.charAt(i) == senhaAtual.charAt(i)){
                    letrasCorretas++;
                }
                for (int k = 0; k < senhaAtual.length(); k++) {
                    if (senhaInserida.charAt(i) == senhaAtual.charAt(k)){
                        if (vetorControle[k] == true) {
                            letrasConfirmadas++;
                            vetorControle[k] = false;
                        }
                        }
                }
            }
                letrasConfirmadas -= letrasCorretas;
                if (letrasConfirmadas == senhaAtual.length()) {
                    System.out.println("Voce acertou a senha");
                    j++;
                }
                else if (letrasConfirmadas > 0 || letrasCorretas > 0){
                    System.out.println("Voce acertou " + letrasCorretas + " e errou a posicao de " + letrasConfirmadas);
                }
            letrasConfirmadas = 0;
            letrasCorretas = 0;
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
        boolean[][] matcop1 = {
                { true, false, true, true, true, false },
                { true, true, true, false, true, true },
                { true, true, false, true, true, false },
                { false, false, true, true, false, false },
                { true, false, true, false, true, true },
                { true, false, true, true, true, true }
        };
        String[][] mat2 = {
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", "# ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " }
        };
        // movimentacao(mat1, matcop);
        String[][] matcop2 = {
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", "# ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " }
        };
        String[] senhas = { "maca", "banana", "uva" };
        puzzle3(senhas);
        //puzzle2(mat2, matcop2);

    }
}
