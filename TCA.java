import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

//teste
public class TCA {
    public final static Scanner TECLADO = new Scanner(System.in);

    // =============================FUNCOES MENU
    // INICIO====================================
    // Liga o Java às funções _kbhit e _getch do Windows (só usada no Windows)
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();

        int _getch();
    }

    public static void limparTela() {
        System.out.print("\033[2J"); // apaga a tela
        System.out.print("\033[H"); // volta o cursor para o canto (1, 1)
        System.out.flush(); // garante que os comandos acima sejam enviados agora
    }

    public static int escolherNoMenuBasico(String titulo, String[] opcoes) {
        int selecionada = 0;

        while (true) {
            desenharMenuBasico(titulo, opcoes, selecionada);
            int tecla = obtemTeclaPressionada(); // espera o jogador apertar uma tecla

            if (tecla == 'w' || tecla == 'W') {
                selecionada--;
                if (selecionada < 0) {
                    selecionada = opcoes.length - 1; // do topo, pula para a última
                }
            } else if (tecla == 's' || tecla == 'S') {
                selecionada++;
                if (selecionada >= opcoes.length) {
                    selecionada = 0; // da última, volta para o topo
                }
            } else if (tecla == 13 || tecla == 10) { // ENTER
                return selecionada;
            } else if (tecla == 27) { // ESC
                return -1;
            }
        }
    }

    public static void desenharMenuBasico(String titulo, String[] opcoes, int selecionada) {
        // descobre o tamanho do maior texto, para a caixa caber tudo
        int maior = titulo.length();
        for (int i = 0; i < opcoes.length; i++) {
            if (opcoes[i].length() > maior) {
                maior = opcoes[i].length();
            }
        }
        int largura = maior + 4; // espaço para o "> " e uma margem

        limparTela();
        System.out.println("╔" + "═".repeat(largura) + "╗");
        System.out.println("║  " + completar(titulo, maior) + "  ║");
        System.out.println("╠" + "═".repeat(largura) + "╣");
        for (int i = 0; i < opcoes.length; i++) {
            if (i == selecionada) {
                System.out.print("║> ");
                mudarCor(VERDE);
                System.out.print(completar(opcoes[i], maior));
                mudarCor(COR_PADRAO); // volta para a cor padrão
                System.out.println("  ║");
            } else {
                System.out.println("║  " + completar(opcoes[i], maior) + "  ║");
            }
        }
        System.out.println("╚" + "═".repeat(largura) + "╝");
        System.out.println();
        System.out.println("Use W/S para navegar e ENTER para selecionar. ESC para sair.");
    }

    // Guarda o momento em que o cronômetro começou (em milissegundos)
    static long inicioCronometro = 0;

    /// home/aluno/Área de trabalho/TCA/TCA.java

    // Começa (ou recomeça) a contar o tempo do zero.
    //
    // Exemplo: jogo com tempo limite de 30 segundos
    // iniciarCronometro();
    // while (segundosPassados() < 30) {
    // // ... jogo rodando ...
    // }
    // System.out.println("Acabou o tempo!");
    public static void iniciarCronometro() {
        inicioCronometro = System.currentTimeMillis();
    }

    // Retorna quantos segundos passaram desde o iniciarCronometro().
    // O valor tem casas decimais: 1.5 quer dizer um segundo e meio.
    public static double segundosPassados() {
        long agora = System.currentTimeMillis();
        return (agora - inicioCronometro) / 1000.0;
    }

    public static int escolherNoMenu(String titulo, String[] opcoes, int[] ataque, int vida, int pntsdacao,
            int pocoesDCura, int vidaInimiga, int ataqueInimigo, int cargaEspecialPersonagem,
            int cargaEspecialInimigo) {
        int selecionada = 0;
        BibliotecaTCA.esconderCursor();
        while (true) {
            desenharMenu(titulo, opcoes, selecionada, vida, ataque, pntsdacao, pocoesDCura, vidaInimiga, ataqueInimigo,
                    cargaEspecialPersonagem, cargaEspecialInimigo);
            int tecla = obtemTeclaPressionada(); // espera o jogador apertar uma tecla

            if (tecla == 'w' || tecla == 'W') {
                selecionada--;
                if (selecionada < 0) {
                    selecionada = opcoes.length - 1; // do topo, pula para a última
                }
            } else if (tecla == 's' || tecla == 'S') {
                selecionada++;
                if (selecionada >= opcoes.length) {
                    selecionada = 0; // da última, volta para o topo
                }
            } else if (tecla == 13 || tecla == 10) { // ENTER
                return selecionada;
            } else if (tecla == 27) { // ESC
                return -1;
            }
        }
    }

    public static void escreverEm(int linha, int coluna, String texto) {
        irPara(linha, coluna);
        System.out.print(texto);
    }

    public static void irPara(int linha, int coluna) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df", escCode, linha, coluna));
    }

    // Desenha o menu numa caixa, com a opção selecionada em verde e
    // marcada com ">". A caixa cresce para caber o texto mais comprido.
    // É usada pelo escolherNoMenu, mas também dá para chamar sozinha.
    public static void desenharMenu(String titulo, String[] opcoes, int selecionada, int vida, int[] ataque,
            int pntsdacao, int pocoesdCura, int vidaInimiga, int ataqueInimigo, int cargaEspecialPersonagem,
            int cargaEspecialInimigo) {
        // descobre o tamanho do maior texto, para a caixa caber tudo
        int maior = titulo.length();
        for (int i = 0; i < opcoes.length; i++) {
            if (opcoes[i].length() > maior) {
                maior = opcoes[i].length();
            }
        }
        int largura = maior + 4; // espaço para o "> " e uma margem

        BibliotecaTCA.limparTela();

        informativo(vida, ataque, pntsdacao, pocoesdCura, vidaInimiga, ataqueInimigo, cargaEspecialPersonagem,
                cargaEspecialInimigo);

        System.out.println("╔" + "═".repeat(largura) + "╗");
        System.out.println("║  " + completar(titulo, maior) + "  ║");
        System.out.println("╠" + "═".repeat(largura) + "╣");
        for (int i = 0; i < opcoes.length; i++) {
            if (i == selecionada) {
                System.out.print("║> ");
                mudarCor(VERDE);
                System.out.print(completar(opcoes[i], maior));
                mudarCor(COR_PADRAO); // volta para a cor padrão
                System.out.println("  ║");
            } else {
                System.out.println("║  " + completar(opcoes[i], maior) + "  ║");
            }
        }
        System.out.println("╚" + "═".repeat(largura) + "╝");
        System.out.println();
        System.out.println("Use W/S para navegar e ENTER para selecionar. ESC para sair.");
    }

    // Completa o texto com espaços à direita até ter o tamanho pedido.
    // Ex.: completar("oi", 5) -> "oi "
    static String completar(String texto, int tamanho) {
        return String.format("%-" + tamanho + "s", texto);
    }

    static boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
    static boolean tecladoPreparado = false;

    // Retorna true se alguma tecla foi pressionada. NÃO espera: se nenhuma
    // tecla foi apertada, retorna false na hora e o jogo continua.
    public static boolean pressionouTecla() {
        if (windows) {
            return Crt.INSTANCE._kbhit() != 0;
        }
        prepararTecladoMacLinux();
        try {
            return System.in.available() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Retorna o código ASCII da tecla pressionada (ex.: 'a', 'w', 27 = ESC).
    // Se nenhuma tecla foi apertada, ESPERA até o usuário apertar uma.
    public static int obtemTeclaPressionada() {
        if (windows) {
            return Crt.INSTANCE._getch();
        }
        prepararTecladoMacLinux();
        try {
            return System.in.read();
        } catch (Exception e) {
            return -1;
        }
    }

    // Mostra "Pressione uma tecla para continuar..." e PARA o programa
    // até o jogador apertar qualquer tecla.
    //
    // Exemplo:
    // System.out.println("GAME OVER");
    // esperarTecla();
    public static void esperarTecla() {
        System.out.println("Pressione uma tecla para continuar...");
        obtemTeclaPressionada(); // fica esperando a tecla
    }

    // Mac/Linux: faz o terminal entregar cada tecla na hora (sem ENTER) e
    // sem mostrar a letra na tela. Quando o programa termina, volta ao normal.
    static void prepararTecladoMacLinux() {
        if (tecladoPreparado) {
            return;
        }
        tecladoPreparado = true;
        executarStty("-icanon -echo -icrnl min 1"); // -icrnl: ENTER vira 13, igual ao Windows
        Runtime.getRuntime().addShutdownHook(new Thread(() -> executarStty("sane")));
    }

    static void executarStty(String opcoes) {
        try {
            new ProcessBuilder("sh", "-c", "stty " + opcoes + " < /dev/tty").start().waitFor();
        } catch (Exception e) {
            // se não conseguir, o teclado só vai funcionar com ENTER
        }
    }
    // ===================================================================

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void gotoXY(int linha, int coluna) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df", escCode, linha, coluna));
    }

    static boolean restauracaoRegistrada = false;

    public static void esconderCursor() {
        System.out.print("\033[?25l");
        System.out.flush();

        // Registra só uma vez o código que roda quando o programa termina
        // (um "shutdown hook"), mesmo que esconderCursor seja chamada várias vezes.
        if (!restauracaoRegistrada) {
            restauracaoRegistrada = true;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                mudarCor(COR_PADRAO);
                mudarCorFundo(COR_PADRAO);
                mostrarCursor();
            }));
        }
    }

    public static void mostrarCursor() {
        System.out.print("\033[?25h");
        System.out.flush();
    }

    // Constantes das cores básicas, para usar com mudarCor e mudarCorFundo.
    // Assim dá para escrever mudarCor(VERDE) em vez de mudarCor(2).
    // "final" quer dizer que o valor nunca muda.
    public static final int PRETO = 0;
    public static final int VERMELHO = 1;
    public static final int VERDE = 2;
    public static final int AMARELO = 3;
    public static final int AZUL = 4;
    public static final int MAGENTA = 5;
    public static final int CIANO = 6;
    public static final int CINZA = 7;
    public static final int BRANCO = 15;
    public static final int VIOLETA = 93;
    public static final int VERDE_LIMA = 46;
    public static final int LARANJA = 208;
    public static final int COR_PADRAO = -1; // a cor original do terminal

    // Muda a cor do TEXTO. Tudo o que for escrito depois sai nessa cor.
    //
    // A cor é um número de 0 a 255 (são 256 cores!). Para as cores
    // básicas, use as constantes acima. Para ver todas as 256 cores e
    // escolher o número, chame a função mostrarCores().
    // Use COR_PADRAO (-1) para voltar à cor original do terminal.
    //
    // Exemplo:
    // mudarCor(VERDE); // texto verde
    // System.out.println("Você venceu!");
    // mudarCor(208); // texto laranja (uma das 256 cores)
    // System.out.println("Cuidado!");
    // mudarCor(COR_PADRAO); // volta ao normal
    //
    // ATENÇÃO:
    // - Use mudarCor(COR_PADRAO) no fim do programa para o terminal não ficar
    // colorido.
    // - Cuidado com texto preto em terminal escuro (ou branco em terminal claro):
    // ele fica invisível!
    // - Só funciona rodando no terminal (não no console do Eclipse/NetBeans).
    public static void mudarCor(int cor) {
        if (cor >= 0 && cor <= 255) {
            System.out.print("\033[38;5;" + cor + "m");
        } else {
            System.out.print("\033[39m"); // cor padrão do terminal
        }
    }

    // Muda a cor do FUNDO (atrás das letras). Usa as mesmas cores
    // do mudarCor: constantes ou números de 0 a 255.
    //
    // Exemplo:
    // mudarCorFundo(AZUL); // fundo azul
    // mudarCor(AMARELO); // texto amarelo
    // System.out.print(" PLACAR: 10 ");
    // mudarCorFundo(COR_PADRAO); // volta o fundo ao normal
    // mudarCor(COR_PADRAO); // volta o texto ao normal
    //
    // Dica: escreva espaços " " com a cor de fundo para desenhar blocos coloridos.
    public static void mudarCorFundo(int cor) {
        if (cor >= 0 && cor <= 255) {
            System.out.print("\033[48;5;" + cor + "m");
        } else {
            System.out.print("\033[49m"); // cor padrão do terminal
        }
    }

    public static int lerValorInt() {
        int valor;
        valor = TECLADO.nextInt();
        return valor;
    }

    // Método que imprime o texto letra por letra
    public static void imprimirDevagar(String texto) {
        BibliotecaTCA.esconderCursor();
        for (int i = 0; i < texto.length(); i++) {
            System.out.print(texto.charAt(i));
            esperar(80);
        }
        System.out.println();
    }

    // Método que imprime o menu letra por letra

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
        limparTela();
        System.out.println();
        BibliotecaTCA.esconderCursor();
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

    public static void desenharTela_AreiaMovedica(int x, int y, int largura, int altura, String mat[][],
            String matcop[][], int[] posTesouro) {
        System.out.println();
        BibliotecaTCA.esconderCursor();
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {

                if (i == y && j == x) {
                    BibliotecaTCA.mudarCor(VERMELHO);
                    System.out.print("#\t");
                } else if (x - AREADEVERFICACAO_AREIASMOVEDICAS <= j && j <= x + AREADEVERFICACAO_AREIASMOVEDICAS
                        && y - AREADEVERFICACAO_AREIASMOVEDICAS <= i && i <= y + AREADEVERFICACAO_AREIASMOVEDICAS) {
                    BibliotecaTCA.mudarCor(AMARELO);
                    System.out.printf("%s\t", matcop[i][j]);
                } else {
                    BibliotecaTCA.mudarCor(-1);
                    System.out.print(".\t");
                }
            }
            System.out.println();
        }
        BibliotecaTCA.mudarCor(-1);
        System.out.println("Use A, D, W, S para mover o personagem. Pressione ESC para sair.");

    }

    public static void desenharTelaPuzzle1(int x, int y, int largura, int altura, String mat[][], int vidas) {
        System.out.println();
        BibliotecaTCA.esconderCursor();
        BibliotecaTCA.limparTela();

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
        System.out.println("Use A, D, W, S para mover o personagem. Evite bater nas paredes invisiveis.");
        System.out.println("Voce possui atualmente: ");
        mudarCor(VERMELHO);
        System.out.print(vidas + " vidas\n");
        mudarCor(-1);
    }

    public static void desenharTelaTesouro(int x, int y, int largura, int altura, String mat[][]) {
        System.out.println();
        BibliotecaTCA.esconderCursor();
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
        System.out.println(
                "Aperte V para verificar areias movedicas e ENTER para verificar tesouro; A,W,S,D para movimentacao");

    }

    public static boolean puzzle1(String mat[][], boolean matcop[][]) {
        int tecla;
        int linhas = mat.length;
        int colunas = mat[0].length;
        int i = 0;
        int j = 0;
        int vidas = 4;
        BibliotecaTCA.esconderCursor();
        String[][] matTemporaria = new String[mat.length][mat[0].length];
        matTemporaria = atualizarMatriz(mat, matTemporaria);

        desenharTela(j, i, colunas, linhas, matTemporaria);

        while (true) {
            // Lógica para detectar o movimento
            if (i == linhas - 1 && j == colunas - 1) {
                return true;
            }
            if (BibliotecaTCA.pressionouTecla()) {
                tecla = BibliotecaTCA.obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        j--;
                        BibliotecaTCA.limparTela();
                        if (j < 0) {
                            j = 0;
                        } else if (matcop[i][j] == false) {
                            matTemporaria[i][j] = "| ";
                            j++;
                            vidas--;

                            mudarCor(-1);
                            if (vidas == 0) {
                                return false;
                            }
                        } else {
                            matTemporaria[i][j] = "# ";
                            matTemporaria[i][j + 1] = ". ";
                        }
                        desenharTelaPuzzle1(j, i, colunas, linhas, matTemporaria, vidas);
                        break;
                    case 'd':
                    case 'D':
                        j++;
                        BibliotecaTCA.limparTela();
                        if (j > colunas - 1) {
                            j = colunas - 1;
                        } else if (matcop[i][j] == false) {
                            matTemporaria[i][j] = "| ";
                            j--;
                            vidas--;
                            if (vidas == 0) {
                                return false;
                            }
                        } else {
                            matTemporaria[i][j] = "# ";
                            matTemporaria[i][j - 1] = ". ";
                        }
                        desenharTelaPuzzle1(j, i, colunas, linhas, matTemporaria, vidas);
                        break;
                    case 'w':
                    case 'W':
                        i--;
                        BibliotecaTCA.limparTela();
                        if (i < 0) {
                            i = 0;
                        } else if (matcop[i][j] == false) {
                            matTemporaria[i][j] = "_ ";
                            i++;
                            vidas--;

                            mudarCor(-1);
                            if (vidas == 0) {
                                return false;
                            }
                        } else {
                            matTemporaria[i][j] = "# ";
                            matTemporaria[i + 1][j] = ". ";
                        }

                        desenharTelaPuzzle1(j, i, colunas, linhas, matTemporaria, vidas);
                        break;
                    case 's':
                    case 'S':
                        i++;
                        BibliotecaTCA.limparTela();
                        if (i > linhas - 1) {
                            i = linhas - 1;
                        } else if (matcop[i][j] == false) {
                            matTemporaria[i][j] = "_ ";
                            i--;
                            vidas--;

                            mudarCor(-1);
                            if (vidas == 0) {
                                return false;
                            }
                        } else {
                            matTemporaria[i][j] = "# ";
                            matTemporaria[i - 1][j] = ". ";
                        }

                        desenharTelaPuzzle1(j, i, colunas, linhas, matTemporaria, vidas);
                        break;
                }
            }

        }

    }

    public static final int QNT_AREIAS_MOVEDICAS = 15;

    public static int[] sortearPosTesouro(int i, int j, int linhas, int colunas) {
        int[] posicao = new int[2];
        do {
            posicao[0] = SORTEIO.nextInt(linhas);
            posicao[1] = SORTEIO.nextInt(colunas);
        } while (posicao[0] == i && posicao[1] == j);

        return posicao;
    }

    public static String[][] posicionarTesouro(int[] posicao, String[][] matcop2) {
        matcop2[posicao[0]][posicao[1]] = "tesouro";
        return matcop2;
    }

    public static String[][] posicionarAreiasMovedicas(String[][] matcop2, int linhas, int colunas, int[] posicao) {
        int i = 0;
        while (i < QNT_AREIAS_MOVEDICAS) {
            int linha = SORTEIO.nextInt(linhas);
            int coluna = SORTEIO.nextInt(colunas);
            if (posicao[0] != linha && posicao[1] != coluna && matcop2[linha][coluna] != "areia"
                    && matcop2[linha][coluna] != "#" && linha != linha / 2 + 1 && linha != linha / 2 - 1
                    && coluna != coluna / 2 + 1 && coluna != coluna / 2 - 1 && linha != posicao[0] + 1
                    && linha != posicao[0] - 1 && coluna != posicao[1] + 1 && coluna != posicao[1] - 1) {
                matcop2[linha][coluna] = "areia";
                i++;
            }
        }
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
            System.out.println(" Quente!");
        } else if (distTesouro[0] < 3 || distTesouro[1] < 3) {
            System.out.println(" Morno!");
        } else {
            System.out.println(" Frio!");
        }
    }

    public final static int AREADEVERFICACAO_AREIASMOVEDICAS = 1;
    public final static int COOLDOWN = 5;

    public static void mostrarAreiaMovedica(String[][] mat, String[][] matcop2, int i, int j) {
        for (int k = i - 2; k < i + 2; k++) {
            for (int l = j - 2; l < j + 2; l++) {
                System.out.printf("%s\t", matcop2[k][l]);
            }
        }

    }

    public static void esperar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            // não faz nada: se a espera for interrompida, o programa só continua
        }
    }

    public static boolean puzzle2(String mat2[][]) {
        int tecla;
        BibliotecaTCA.esconderCursor();
        int linhas = mat2.length;
        int colunas = mat2[0].length;
        int[] distTesouro = new int[2];
        String[][] matcop2 = new String[linhas][colunas];
        String[][] matPadrao = new String[linhas][colunas];
        matPadrao = atualizarMatriz(mat2, matPadrao);
        matcop2 = atualizarMatriz(mat2, matcop2);
        int j = colunas / 2;
        int i = linhas / 2;
        int[] pos = sortearPosTesouro(i, j, linhas, colunas);
        matcop2 = posicionarAreiasMovedicas(matcop2, linhas, colunas, pos);
        matcop2[i][j] = ". ";
        boolean primeiraVez = true;
        double segundos = 0;

        iniciarCronometro();
        segundosPassados();
        desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
        while (segundosPassados() <= 45) {
            escreverEm(1, 0, String.format("%.1f s", segundosPassados()));
            esperar(100);
            // Lógica para detectar o movimento

            if (BibliotecaTCA.pressionouTecla()) {
                tecla = BibliotecaTCA.obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        j--;
                        if (j < 0) {
                            j = 0;
                        } else if (matcop2[i][j] == "areia") {
                            System.out.println(" Voce caiu em uma areia movediça! Game over!");
                            esperar(800);
                            return false;
                        } else {
                            matPadrao[i][j] = "# ";
                            matPadrao[i][j + 1] = ". ";
                        }

                        BibliotecaTCA.limparTela();
                        desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
                        ;
                        break;
                    case 'd':
                    case 'D':
                        j++;
                        if (j > colunas - 1) {
                            j = colunas - 1;
                        } else if (matcop2[i][j] == "areia") {
                            System.out.println(" Voce caiu em uma areia movediça! Game over!");
                            esperar(800);
                            return false;
                        } else {
                            matPadrao[i][j] = "# ";
                            matPadrao[i][j - 1] = ". ";
                        }

                        BibliotecaTCA.limparTela();
                        desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
                        break;
                    case 'w':
                    case 'W':
                        i--;

                        if (i < 0) {
                            i = 0;
                        } else if (matcop2[i][j] == "areia") {
                            System.out.println(" Voce caiu em uma areia movediça! Game over!");
                            esperar(800);
                            return false;
                        } else {
                            matPadrao[i][j] = "# ";
                            matPadrao[i + 1][j] = ". ";
                        }

                        BibliotecaTCA.limparTela();
                        desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
                        break;
                    case 's':
                    case 'S':
                        i++;
                        if (i > linhas - 1) {
                            i = linhas - 1;
                        } else if (matcop2[i][j] == "areia") {
                            System.out.println(" Voce caiu em uma areia movediça! Game over!");
                            esperar(800);
                            return false;
                        } else {
                            matPadrao[i][j] = "# ";
                            matPadrao[i - 1][j] = ". ";
                        }

                        BibliotecaTCA.limparTela();
                        desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
                        break;
                    case 13:
                        if (i == pos[0] && j == pos[1]) {
                            System.out.println(" Parabéns! Você encontrou o tesouro!");
                            System.out.println();
                            return true;
                        } else {
                            distTesouro = calcularDistTesouro(pos, i, j);
                            exibirQuenteOuFrio(distTesouro);
                            continue;
                        }
                    case 'v':
                    case 'V':
                        limparTela();
                        if (segundosPassados() - segundos < COOLDOWN) {
                            int segundosQFaltam = COOLDOWN - (int) (segundosPassados() - segundos);
                            System.out.println(" Voce ainda nao pode usar a habilidade espere " + segundosQFaltam);
                            esperar(300);
                            desenharTelaTesouro(j, i, colunas, linhas, matPadrao);
                        } else if (System.currentTimeMillis() - segundos >= COOLDOWN || primeiraVez) {
                            desenharTela_AreiaMovedica(j, i, colunas, linhas, matPadrao, matcop2, pos);
                            segundos = segundosPassados();
                            primeiraVez = false;
                        }

                    default:

                }
            }
        }
        return false;
    }

    public static boolean[] preencherVet(boolean[] vet) {
        for (int i = 0; i < vet.length; i++) {
            vet[i] = true;
        }
        return vet;
    }

    public static String formatarPalavra(String palavra, int letras) {
        char[] letrasFormatadas = new char[letras];
        for (int i = 0; i < letras; i++) {
            letrasFormatadas[i] = palavra.charAt(i);
        }
        String palavraFormatada = new String(letrasFormatadas);
        return palavraFormatada;
    }

    public static void puzzle3(String[] perguntas) {

        int j = 0;
        BibliotecaTCA.esconderCursor();
        while (j < perguntas.length) {

        }
    }

    public static int defesa(int dano) {
        return dano / 2;
    }

    public static int defesaCompleta(int dano) {
        return dano - dano;
    }

    public static void informativo(int vida, int[] ataque, int pontosDeAcao, int pocoesDcura, int vidaInimiga,
            int ataqueInimigo, int cargaEspecialPersonagem, int cargaEspecialInimigo) {
        BibliotecaTCA.limparTela();
        System.out.println("Voce possui atualmente:");
        mudarCor(VERMELHO);
        System.out.println("Vida: " + vida);
        mudarCor(VERDE);
        System.out.println("Ataque: " + ataque[0] + "-" + ataque[1]);
        mudarCor(VIOLETA);
        System.out.println("Carga especial: ");

        for (int i = 0; i < cargaEspecialPersonagem; i++) {
            mudarCorFundo(VIOLETA);
            System.out.print(" ");
            mudarCorFundo(-1);
            System.out.print("|");
        }
        for (int i = 0; i < ATAQUE_ESPECIAL_PERSONAGEM - cargaEspecialPersonagem; i++) {
            mudarCorFundo(VERMELHO);
            System.out.print(" ");
            mudarCorFundo(-1);
            System.out.print("|");
        }
        mudarCor(AZUL);
        System.out.println("\nPocoes de cura: " + pocoesDcura);
        mudarCor(AMARELO);
        System.out.println("Pontos de acao: " + pontosDeAcao + "\n");
        mudarCor(-1);
        System.out.println("O Inimigo está atualmente com: ");
        mudarCor(VERMELHO);
        System.out.println("Vida: " + vidaInimiga);
        mudarCor(VERDE);
        System.out.println("Ataque: " + ataqueInimigo);
        mudarCor(VERDE_LIMA);
        System.out.println("Carga especial: ");
        for (int i = 0; i < cargaEspecialInimigo; i++) {
            mudarCorFundo(VERDE_LIMA);
            System.out.print(" ");
            mudarCorFundo(-1);
            System.out.print("|");
        }
        for (int i = 0; i < ATAQUE_ESPECIAL_INIMIGO - cargaEspecialInimigo; i++) {
            mudarCorFundo(VERMELHO);
            System.out.print(" ");
            mudarCorFundo(-1);
            System.out.print("|");
        }
        System.out.println();
        mudarCor(-1);

    }

    public static final int ATAQUE_ESPECIAL_PERSONAGEM = 5;
    public static final int ATAQUE_ESPECIAL_INIMIGO = 3;

    public static boolean sistemaDBatalha(int vidaInimiga, int ataqueInimigo, int vidaTotal, int atqLeve, int atqPesado,
            int eficienciaDeDefesa) {
        boolean lutaVencida = false;
        int cargaEspecialPersonagem = 0;
        int cargaEspecialInimigo = 0;
        int[] ataque = { atqLeve, atqPesado };
        int cura = 30;
        BibliotecaTCA.esconderCursor();
        int ataqueBaseInimigo = ataqueInimigo;
        int pocoesDCura = 3;
        int vida = vidaTotal;
        String[] opcoes = { "Ataque", "Defesa", "Cura" };
        String[] ataques = { "Ataque pesado", "Ataque leve", "Ataque especial" };
        String[] defesas = { "Defesa imparcial", "Defesa completa" };
        String[] espaco = { "   " };
        while (true) {
            if (vida <= 0) {
                BibliotecaTCA.escolherNoMenu("Voce morreu", espaco);
                BibliotecaTCA.esperar(2000);
                return lutaVencida;
            }
            int pontosDeAcao = 3;
            while (pontosDeAcao != 0) {
                limparTela();

                int escolha = escolherNoMenu("   Batalha   ", opcoes, ataque, vida, pontosDeAcao, pocoesDCura,
                        vidaInimiga, ataqueInimigo, cargaEspecialPersonagem, cargaEspecialInimigo);
                int subescolha = 0;
                if (escolha == 0) {

                    subescolha = escolherNoMenu("   Ataques   ", ataques, ataque, vida, pontosDeAcao, pocoesDCura,
                            vidaInimiga, ataqueInimigo, cargaEspecialPersonagem, cargaEspecialInimigo);
                    if (subescolha == 0) {
                        if (pontosDeAcao >= 2) {
                            int sorteio = SORTEIO.nextInt(5);
                            if (sorteio <= 3) {
                                vidaInimiga -= atqPesado;
                                mudarCor(VERDE);
                                System.out.println("Voce acerta um golpe pesado e causa " + atqPesado + " de dano");
                                cargaEspecialPersonagem++;
                                BibliotecaTCA.esperar(1500);
                                mudarCor(-1);
                                pontosDeAcao -= 2;
                                if (vidaInimiga <= 0) {
                                    return lutaVencida = true;
                                }
                            } else {
                                mudarCor(VERMELHO);
                                System.out.println("Voce erra o golpe pesado");
                                BibliotecaTCA.esperar(1500);
                                mudarCor(-1);
                                pontosDeAcao -= 2;
                            }

                        } else {
                            mudarCor(VERMELHO);
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            mudarCor(-1);
                            BibliotecaTCA.esperar(1500);
                            continue;
                        }
                    } else if (subescolha == 1) {

                        if (pontosDeAcao >= 1) {
                            vidaInimiga -= atqLeve;
                            mudarCor(VERDE);
                            System.out.println("Voce acerta um golpe leve e causa " + atqLeve + " de dano");
                            mudarCor(-1);
                            pontosDeAcao--;
                            if (vidaInimiga <= 0) {
                                mudarCor(VERMELHO);
                                System.out.println("Voce derrotou o boss");
                                mudarCor(-1);
                                return lutaVencida = true;
                            }
                        } else if (subescolha == 2) {
                            if (pontosDeAcao > 0) {
                                if (cargaEspecialPersonagem == ATAQUE_ESPECIAL_PERSONAGEM) {
                                    vidaInimiga -= atqPesado * 2;
                                    mudarCor(VERDE);
                                    System.out.println(
                                            "Voce acerta um golpe especial e causa " + atqPesado * 2 + " de dano");
                                    mudarCor(-1);
                                    pontosDeAcao--;
                                    cargaEspecialPersonagem = 0;
                                    if (vidaInimiga <= 0) {
                                        mudarCor(VERMELHO);
                                        System.out.println("Voce derrotou o boss");
                                        mudarCor(-1);
                                        return lutaVencida = true;
                                    }
                                } else {
                                    mudarCor(VERMELHO);
                                    System.out.println("Voce nao tem carga especial suficiente");
                                    mudarCor(-1);
                                    BibliotecaTCA.esperar(1500);
                                    continue;
                                }
                            }
                        } else {
                            mudarCor(VERMELHO);
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            mudarCor(-1);
                            BibliotecaTCA.esperar(1500);
                            continue;
                        }
                    } else if (subescolha == 2) {
                        if (pontosDeAcao > 0) {
                            if (cargaEspecialPersonagem == ATAQUE_ESPECIAL_PERSONAGEM) {
                                vidaInimiga -= atqPesado * 2;
                                mudarCor(VERDE);
                                imprimirDevagar("Voce acerta um golpe especial e causa " + atqPesado * 2 + " de dano");
                                mudarCor(-1);
                                pontosDeAcao--;
                                cargaEspecialPersonagem = 0;
                            }
                            if (vidaInimiga <= 0) {
                                mudarCor(VERMELHO);
                                imprimirDevagar("Voce derrotou o boss");
                                esperar(500);
                                mudarCor(-1);
                                return lutaVencida = true;
                            }
                        }
                    } else if (subescolha == 27) {
                        continue;
                    }
                } else if (escolha == 1) {

                    subescolha = escolherNoMenu("   Defesas   ", defesas, ataque, vida, pontosDeAcao, pocoesDCura,
                            vidaInimiga, ataqueInimigo, cargaEspecialPersonagem, cargaEspecialInimigo);
                    if (subescolha == 0) {
                        if (pontosDeAcao >= 1) {
                            ataqueInimigo /= 2;
                            pontosDeAcao--;
                        } else {
                            mudarCor(VERMELHO);
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            mudarCor(-1);
                            BibliotecaTCA.esperar(1500);
                            continue;
                        }
                    } else if (subescolha == 1) {
                        if (pontosDeAcao == 3) {
                            ataqueInimigo = 0;
                            pontosDeAcao -= 3;
                        } else {
                            mudarCor(VERMELHO);
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            mudarCor(-1);
                            BibliotecaTCA.esperar(1500);
                            continue;
                        }
                    } else if (subescolha == 27) {
                        continue;
                    }
                } else if (escolha == 2) {

                    if (vida > vidaTotal * 0.7) {
                        mudarCor(VERMELHO);
                        System.out.println("Voce nao esta machucado o suficiente para usar a pocao");
                        mudarCor(-1);
                        BibliotecaTCA.esperar(1500);
                        continue;
                    } else if (pocoesDCura != 0) {
                        vida += cura;
                        pocoesDCura--;
                        continue;
                    } else {
                        mudarCor(VERMELHO);
                        System.out.println("Voce nao tem pocoes de cura suficiente");
                        mudarCor(-1);
                        BibliotecaTCA.esperar(1500);
                        continue;
                    }
                }

            }
            if (cargaEspecialInimigo == ATAQUE_ESPECIAL_INIMIGO) {
                ataqueInimigo *= 2;
                vida -= ataqueInimigo;
                cargaEspecialInimigo = 0;
                imprimirDevagar("O inimigo usou um ataque especial causando " + ataqueBaseInimigo * 2 + " de dano");
            } else {
                vida -= ataqueInimigo;
                cargaEspecialInimigo++;
                mudarCor(VERMELHO);

                imprimirDevagar("O inimigo ataca causando " + ataqueBaseInimigo + " de dano");
                mudarCor(-1);
            }
            if (ataqueInimigo == 0) {
                mudarCor(VERDE);
                BibliotecaTCA.esperar(1000);
                System.out.println("Voce bloqueia perfeitamente o ataque");
                BibliotecaTCA.esperar(500);
                mudarCor(-1);
            } else if (ataqueBaseInimigo > ataqueInimigo) {
                mudarCor(VERDE);
                BibliotecaTCA.esperar(500);
                System.out.println("Voce defende parcialmente mas recebe " + ataqueInimigo + " de dano");
                BibliotecaTCA.esperar(500);
                mudarCor(-1);
            }
            ataqueInimigo = ataqueBaseInimigo;
        }

    }

    public static int[] goblinAnciao() {
        int[] status = new int[2];
        int dano = 50;
        int vida = 200;
        status[1] = vida;
        status[0] = dano;
        return status;
    }

    public static int[] pirata() {
        int[] status = new int[2];
        int dano = 75;
        int vida = 240;
        status[0] = vida;
        status[1] = dano;
        return status;
    }

    public static int[] reiPerdido() {
        int[] status = new int[2];
        int dano = 75;
        int vida = 400;
        status[0] = vida;
        status[1] = dano;
        return status;
    }


    public static String[][] atualizarMatriz(String[][] mat1, String[][] matBase) {
        if (mat1.length != matBase.length || mat1[0].length != matBase[0].length) {
            return null;
        }
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                matBase[i][j] = mat1[i][j];
            }
        }
        return matBase;
    }

    public final static int ATRIBUICAOBASE = 5;
    public final static int TENTATIVASNECESSARIAS = 5;

    public static void main(String[] args) {
        mudarCor(VERMELHO);
        System.out.println("Digite o seu nome (Aviso: ele nao podera ser trocado depois de escolhido)");
        mudarCor(COR_PADRAO);
        String nome = lerTexto();
        Historia.part1(nome);
        esconderCursor();
        String[][] matPuzzle1;
        boolean[][] matcopPuzzle1;
        MatrizesPuzzle_1 matrizesPuzzle1 = new MatrizesPuzzle_1();
        int numero = sortearNumero(4);
        String[][] mat2 = {
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", "# ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
            { ". ", ". ", ". ", ". ", ". ", ". ", ". " }
        };
        
        int areasExploradas = 0;
        int pontosDeUpgrade = 0;
        int tentativas = 0;
        Atributos atributosPadrao = new Atributos();
        int ataquePesado = atributosPadrao.ataquePesado;
        int ataqueLeve = atributosPadrao.ataqueLeve;
        int vida = atributosPadrao.vida;
        int eficienciaDeDefesa = atributosPadrao.eficienciaDeDefesa;
        
        String[] areas = { "Aldeia dos Goblins", "Ilha do Pirata", "Tumba do Rei Perdido" };
        String[] menuInicial = { "Explorar areas", "Status do personagem" };
        String[] senhas = { "maca", "banana", "uva" };
        boolean[] areasCompletas = { false, false, false };
        int[] statusInimigo = new int[2];
        
        while (areasExploradas != 3) {
            if (numero == 1) {
                matPuzzle1 = matrizesPuzzle1.mat1;
                matcopPuzzle1 = matrizesPuzzle1.matcop1;
            }else if (numero == 2) {
                matPuzzle1 = matrizesPuzzle1.mat2;
                matcopPuzzle1 = matrizesPuzzle1.matcop2;
            }else{
                matPuzzle1 = matrizesPuzzle1.mat3;
                matcopPuzzle1 = matrizesPuzzle1.matcop3;
            }
            String[][] matBasePuzzle1 = new String[matPuzzle1.length][matPuzzle1.length];
            matBasePuzzle1 = matPuzzle1;
            
            int escolha = escolherNoMenuBasico("   Menu Inicial   ", menuInicial);
            if (escolha == 0) {
                System.out.println("Escolha a area que deseja explorar");
                BibliotecaTCA.esperar(2000);
                int area = escolherNoMenuBasico("   Areas   ", areas);
                if (area == 0) {
                    if (areasCompletas[0] == true) {
                        mudarCor(VERMELHO);
                        System.out.println("Voce ja explorou " + areas[0]);
                        mudarCor(-1);
                        continue;
                    }
                    boolean passou = false;
                    boolean restart = false;
                    while (passou == false) {
                        System.out.println("Voce tem "+ (TENTATIVASNECESSARIAS - tentativas) + " tentativas para concluir este labririnto\n(Ele será resetado apos " + TENTATIVASNECESSARIAS + " tentativas)");
                        esperar(1500);
                        matBasePuzzle1 = atualizarMatriz(matPuzzle1, matBasePuzzle1);
                        passou = puzzle1(matBasePuzzle1, matcopPuzzle1);
                        tentativas++;
                        if (tentativas == TENTATIVASNECESSARIAS) {
                            restart = true;
                            break;
                        }
                    }
                    if (restart == true) {
                        continue;
                    }
                    do {
                        Historia.interacaoGoblinAnciaoInicio();
                        mudarCor(VERMELHO);
                        System.out.println("E entao voce acha o boss");
                        mudarCor(-1);
                        BibliotecaTCA.esperar(2000);
                        statusInimigo = goblinAnciao();
                        areasCompletas[0] = sistemaDBatalha(statusInimigo[1], statusInimigo[0], vida, ataqueLeve,
                                ataquePesado, eficienciaDeDefesa);
                    } while (areasCompletas[0] == false);
                    areasExploradas++;
                    pontosDeUpgrade += ATRIBUICAOBASE;
                    Historia.interacaoGoblinAnciaoFinal();
                    System.out.println("Voce recebeu: " + ATRIBUICAOBASE);
                    esperar(700);
                    continue;
                } else if (area == 1) {
                    if (areasCompletas[1] == true) {
                        mudarCor(VERMELHO);
                        System.out.println("Voce ja explorou " + areas[1]);
                        mudarCor(-1);
                        continue;
                    }
                    do {
                        limparTela();
                        areasCompletas[1] = puzzle2(mat2);
                    } while (areasCompletas[1] == false);
                    do {
                        Historia.interacaoPirataInicio();
                        mudarCor(VERMELHO);
                        System.out.println("E entao voce acha o boss");
                        mudarCor(-1);
                        BibliotecaTCA.esperar(2000);
                        statusInimigo = pirata();
                        areasCompletas[1] = sistemaDBatalha(statusInimigo[0], statusInimigo[1], vida, ataqueLeve,
                                ataquePesado, eficienciaDeDefesa);
                    } while (areasCompletas[1] == false);
                    areasExploradas++;
                    pontosDeUpgrade += ATRIBUICAOBASE;
                    Historia.interacaoPirataFinal();
                    System.out.println("Voce recebeu: " + ATRIBUICAOBASE);
                    esperar(700);
                    continue;
                } else if (area == 2) {
                    if (areasCompletas[2] == true) {
                        mudarCor(VERMELHO);
                        System.out.println("Voce ja explorou " + areas[2]);
                        mudarCor(-1);
                        continue;
                    }
                    puzzle3(senhas);
                    do {
                        Historia.interacaoReiPerdidoInicio();
                        mudarCor(VERMELHO);
                        System.out.println("E entao voce acha o boss");
                        mudarCor(-1);
                        BibliotecaTCA.esperar(2000);
                        statusInimigo = reiPerdido();
                        areasCompletas[2] = sistemaDBatalha(statusInimigo[0], statusInimigo[1], vida, ataqueLeve,
                                ataquePesado, eficienciaDeDefesa);
                    } while (areasCompletas[2] == false);
                    areasExploradas++;
                    pontosDeUpgrade += ATRIBUICAOBASE;
                    Historia.interacaoReiPerdidoFinal();
                    imprimirDevagar("Voce recebeu: " + ATRIBUICAOBASE);
                    esperar(700);
                    continue;

                }
            } else if (escolha == 1) {
                String[] atributos = { "Ataque pesado", "Ataque leve", "Vida", "Eficiencia de defesa" };
                int[] pontosGastos = { 0, 0, 0, 0 };
                String[] pontos = { "1 ponto", "Todos os pontos", "Reset de status" };
                int escolhaDePontos;
                boolean voltar = false;

                while (true) {
                    int subescolha = escolherNoMenuBasico("   Status do personagem   ", atributos);
                    voltar = false;
                    if (subescolha == 0) {
                        if (voltar) {
                            break;
                        }
                        
                        while (true) {
                            System.out.println("Ataque pesado: " + ataquePesado);
                            System.out.println("Voce possui atualmente " + pontosDeUpgrade + " pontos de upgrade e "+ pontosGastos[0] + " Investidos nesse status");
                            System.out.println(
                                    "Deseja aumentar o ataque pesado em quantos pontos? (1 ponto = 10 de ataque pesado)(pressione ESC para voltar)");
                                    esperar(2000);
                            escolhaDePontos = escolherNoMenuBasico("Ajuste seus atributos", pontos);
                            if (escolhaDePontos == 0) {
                                if (pontosDeUpgrade == 0) {
                                    System.out.println("Voce esta sem pontos de upgrade");
                                } else {
                                    ataquePesado = ataquePesado + 10;
                                    pontosDeUpgrade--;
                                    pontosGastos[0]++;
                                }
                            } else if (escolhaDePontos == 27 || escolhaDePontos == -1) {
                                voltar = true;
                                break;
                            } else if (escolhaDePontos == 1) {
                                ataquePesado = ataquePesado + (pontosDeUpgrade * 10);
                                pontosGastos[0] += pontosDeUpgrade;
                                pontosDeUpgrade = 0;
                            } else if (escolhaDePontos == 2) {
                                ataquePesado = atributosPadrao.ataquePesado;
                                pontosDeUpgrade = pontosGastos[0];
                            }
                        }
                    } else if (subescolha == 1) {
                        if (voltar) {
                            break;
                        }
                        while (true) {
                            System.out.println("Ataque leve: " + ataqueLeve);
                            System.out.println("Voce possui atualmente " + pontosDeUpgrade + " pontos de upgrade e "+ pontosGastos[1] + " Investidos nesse status");
                            System.out.println(
                                    "Deseja aumentar o ataque leve em quantos pontos? (1 ponto = 5 de ataque leve) (pressione ESC para voltar)");
                                    esperar(2000);
                            escolhaDePontos = escolherNoMenuBasico("Ajuste seus atributos", pontos);
                            if (escolhaDePontos == 0) {
                                if (pontosDeUpgrade == 0) {
                                    System.out.println("Voce esta sem pontos de upgrade");
                                } else {
                                    ataqueLeve = ataqueLeve + 5;
                                    pontosDeUpgrade--;
                                }
                            } else if (escolhaDePontos == 27 || escolhaDePontos == -1) {
                                voltar = true;
                                break;
                            } else if (escolhaDePontos == 1) {
                                ataqueLeve = ataqueLeve + (pontosDeUpgrade * 5);
                                pontosGastos[1] += pontosDeUpgrade;
                                pontosDeUpgrade = 0;
                            } else if (escolhaDePontos == 2) {
                                ataquePesado = atributosPadrao.ataquePesado;
                                pontosDeUpgrade = pontosGastos[1];
                            }
                        }
                    } else if (subescolha == 2) {
                        if (voltar) {
                            break;
                        }
                        while (true) {
                            System.out.println("Vida: " + vida);
                            System.out.println("Voce possui atualmente " + pontosDeUpgrade + " pontos de upgrade e " + pontosGastos[2] + " Investidos nesse status");
                            System.out.println(
                                    "Deseja aumentar a vida em quantos pontos? (1 ponto = 10 de vida) (pressione ESC para voltar)");
                                    esperar(2000);
                            escolhaDePontos = escolherNoMenuBasico("Ajuste seus atributos", pontos);
                            if (escolhaDePontos == 0) {
                                if (pontosDeUpgrade == 0) {
                                    System.out.println("Voce esta sem pontos de upgrade");
                                } else {
                                    vida = vida + 10;
                                    pontosDeUpgrade--;
                                    pontosGastos[2]++;
                                }
                            } else if (escolhaDePontos == 27 || escolhaDePontos == -1) {
                                voltar = true;
                                break;
                            } else if (escolhaDePontos == 1) {
                                vida = vida + (pontosDeUpgrade * 10);
                                pontosGastos[2] += pontosDeUpgrade;
                                pontosDeUpgrade = 0;
                            } else if (escolhaDePontos == 2) {
                                vida = atributosPadrao.vida;
                                pontosDeUpgrade = pontosGastos[2];
                            }
                        }
                    } else if (subescolha == 3) {
                        if (voltar) {
                            break;
                        }
                        System.out.println("Eficiencia de defesa: " + eficienciaDeDefesa);
                        System.out.println("Voce possui atualmente " + pontosDeUpgrade + " pontos de upgrade e "+ pontosGastos[3] + " Investidos nesse status");
                        System.out.println(
                                "Deseja aumentar a eficiencia de defesa em quantos pontos? (1 ponto = 2 de eficiencia de defesa)(pressione ESC para voltar)");
                                esperar(2000);

                        while (true) {
                            escolhaDePontos = escolherNoMenuBasico("Ajuste seus atributos", pontos);
                            if (escolhaDePontos == 0) {
                                if (pontosDeUpgrade == 0) {
                                    System.out.println("Voce esta sem pontos de upgrade");
                                } else {
                                    eficienciaDeDefesa = eficienciaDeDefesa + 5;
                                    pontosDeUpgrade--;
                                    pontosGastos[3]++;
                                }
                            } else if (escolhaDePontos == 27 || escolhaDePontos == -1) {
                                voltar = true;
                                break;
                            } else if (escolhaDePontos == 1) {
                                eficienciaDeDefesa = eficienciaDeDefesa + (pontosDeUpgrade * 5);
                                pontosGastos[3] += pontosDeUpgrade;
                                pontosDeUpgrade = 0;
                            } else if (escolhaDePontos == 2) {
                                eficienciaDeDefesa = atributosPadrao.eficienciaDeDefesa;
                                pontosDeUpgrade = pontosGastos[3];
                            }
                        }
                    } else if (subescolha == 27 || subescolha == -1) {
                        break;
                    }
                    continue;
                }
            }
        }

    }
}
