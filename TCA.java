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

    // Guarda o momento em que o cronômetro começou (em milissegundos)
    static long inicioCronometro = 0;

    // Começa (ou recomeça) a contar o tempo do zero.
    //
    // Exemplo: jogo com tempo limite de 30 segundos
    //   iniciarCronometro();
    //   while (segundosPassados() < 30) {
    //       // ... jogo rodando ...
    //   }
    //   System.out.println("Acabou o tempo!");
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
        for (int i = 0; i < texto.length(); i++) {
            System.out.print(texto.charAt(i));
            System.out.flush();
            BibliotecaTCA.esconderCursor();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    // Método que imprime o menu letra por letra
    public static void imprimirDevagarMenu(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            System.out.print(texto.charAt(i));
            System.out.flush();

            try {
                Thread.sleep(0, 5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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
        System.out.println("Aperte Q para ver area quente, M para ver area morna, F para ver area fria");
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

    public static final int QNT_AREIAS_MOVEDICAS = 30;

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
            if (matcop2[linha][coluna] != "tesouro" && matcop2[linha][coluna] != "areia"
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
            System.out.println("Quente!");
        } else if (distTesouro[0] < 3 || distTesouro[1] < 3) {
            System.out.println("Morno!");
        } else {
            System.out.println("Frio!");
        }
    }

    public static boolean verificarAreiasMovedicas(int i, int j, String[][] matcop2) {
        if (matcop2[i][j] == "areia") {
            System.out.println("Voce caiu em uma area de areia movedica!");
            return false;
        }
        return true;
    }

    public static int areaDVerificacaoMovedica = 2;

    public static void mostrarAreiaMovedica(String matcop2[][],int i, int j){
        for(int k = i - 2;k < i + 2; k++){
            for(int l = j - 2;l < j + 2;l++){
                System.out.print(matcop2[k][l]);
            }
        }
    }

    public static boolean puzzle2(String mat2[][]) {
        int tecla;
        BibliotecaTCA.esconderCursor();
        int linhas = mat2.length;
        int colunas = mat2[0].length;
        int[] distTesouro = new int[2];
        String[][] matcop2 = new String[linhas][colunas];
        matcop2 = atualizarMatriz(mat2, matcop2);
        int j = colunas / 2;
        int i = linhas / 2;
        int[] pos = sortearPosTesouro(i, j, linhas, colunas);
        matcop2 = posicionarTesouro(pos, matcop2);
        matcop2 = posicionarAreiasMovedicas(matcop2, linhas, colunas, pos);
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
                        verificarAreiasMovedicas(i, j, matcop2);
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
                        verificarAreiasMovedicas(i, j, matcop2);
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
                        verificarAreiasMovedicas(i, j, matcop2);
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
                        verificarAreiasMovedicas(i, j, matcop2);
                        BibliotecaTCA.limparTela();
                        desenharTela(j, i, colunas, linhas, mat2);
                        break;
                    case 13:
                        if (matcop2[i][j] == "tesouro") {
                            System.out.println("Parabéns! Você encontrou o tesouro!");
                            return true;
                        } else {
                            distTesouro = calcularDistTesouro(pos, i, j);
                            exibirQuenteOuFrio(distTesouro);
                            continue;
                        }
                    case'v':
                    case'V': 
                        return false; 
                    default:
                }
            }

        }

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

    public static void puzzle3(String[] senhas) {
        int letrasCorretas = 0;
        int letrasConfirmadas = 0;
        int j = 0;
        BibliotecaTCA.esconderCursor();
        while (j < senhas.length) {

            String senhaAtual = senhas[j];
            boolean[] vetorControle = new boolean[senhaAtual.length()];
            vetorControle = preencherVet(vetorControle);
            System.out.println("A senha atual possui " + senhaAtual.length() + " digitos");
            String senhaInserida = lerTexto();
            senhaInserida = formatarPalavra(senhaInserida, senhaAtual.length());
            for (int i = 0; i < senhaInserida.length(); i++) {
                if (senhaInserida.charAt(i) == senhaAtual.charAt(i)) {
                    letrasCorretas++;
                }
                for (int k = 0; k < senhaAtual.length(); k++) {
                    if (senhaInserida.charAt(i) == senhaAtual.charAt(k)) {
                        if (vetorControle[k] == true) {
                            letrasConfirmadas++;
                            vetorControle[k] = false;
                        }
                    }
                }
            }
            letrasConfirmadas -= letrasCorretas;
            if (letrasCorretas == senhaAtual.length()) {
                System.out.println("Voce acertou a senha");
                j++;
            } else if (letrasConfirmadas > 0 || letrasCorretas > 0) {
                System.out.println("Voce acertou " + letrasCorretas + " e errou a posicao de " + letrasConfirmadas);
            }
            letrasConfirmadas = 0;
            letrasCorretas = 0;
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

    public static boolean sistemaDBatalha(int vidaInimiga, int ataqueInimigo) {
        boolean lutaVencida = false;
        int[] ataque = { 30, 10 };
        int cargaEspecialPersonagem = 0;
        int cargaEspecialInimigo = 0;
        int vida = 100;
        int cura = 30;
        BibliotecaTCA.esconderCursor();
        int ataqueBaseInimigo = ataqueInimigo;
        int pocoesDCura = 3;
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
                            int sorteio = SORTEIO.nextInt(4) + 1;
                            if (sorteio <= 3) {
                                vidaInimiga -= ataque[0];
                                mudarCor(VERDE);
                                System.out.println("Voce acerta um golpe pesado e causa " + ataque[0] + " de dano");
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
                            vidaInimiga -= ataque[1];
                            mudarCor(VERDE);
                            System.out.println("Voce acerta um golpe leve e causa " + ataque[1] + " de dano");
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
                                    vidaInimiga -= ataque[0] * 2;
                                    mudarCor(VERDE);
                                    System.out.println(
                                            "Voce acerta um golpe especial e causa " + ataque[0] * 2 + " de dano");
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
                                vidaInimiga -= ataque[0] * 2;
                                mudarCor(VERDE);
                                imprimirDevagar("Voce acerta um golpe especial e causa " + ataque[0] * 2 + " de dano");
                                mudarCor(-1);
                                pontosDeAcao--;
                                cargaEspecialPersonagem = 0;
                            }
                            if (vidaInimiga <= 0) {
                                mudarCor(VERMELHO);
                                System.out.println("Voce derrotou o boss");
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

                    if (vida > 70) {
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
                vida -= ataqueInimigo * 2;
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
        int dano = 35;
        int vida = 240;
        status[0] = vida;
        status[1] = dano;
        return status;
    }

    public static int[] reiPerdido() {
        int[] status = new int[2];
        int dano = 40;
        int vida = 400;
        status[0] = vida;
        status[1] = dano;
        return status;
    }

    public static void interaçaoGoblinAnciao() {
        System.out.println("Aperte esc para pular o dialogo e enter para prosseguir");
        int entrada = obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        if (entrada != 27) {
            mudarCor(VERDE);
            imprimirDevagar("Voce realmente acha que vai escapar daqui com vida???");
            BibliotecaTCA.esperar(500);
            imprimirDevagar("Nos goblins temos odio a raca humana");
            BibliotecaTCA.esperar(500);
            System.out.println("                                  @@@@@@@@@@@@@@@@@                             \r\n" + //
                    "                             @@@@@@@%************#%@@@@@@                       \r\n" + //
                    "                        @@@@%%*************************#%@@@                    \r\n" + //
                    "                      @@%#*********************************%@@                  \r\n" + //
                    "                    @@%**********************************#***%@@                \r\n" + //
                    "                   @@%**#*************************************#@@@           @@@\r\n" + //
                    "                  @@@*******************************************@@         @@#@@\r\n" + //
                    "@@                @@%**%#***********************************##**#@@      @@%**@@\r\n" + //
                    "@@#@@             @@#**##***********************************@#***@@     @@#**#@@\r\n" + //
                    " @%**#@@          @@***#%**********************************#@#***%@    @@****%@@\r\n" + //
                    "  @@****%@@       @@****#***********************************@#***#@   @%*****%@@\r\n" + //
                    "  @@#******@@     @%***@@*************%#*@#****************#@#***#@  @@***%#*%@@\r\n" + //
                    "   @@********%@   @%***%@*************#%*%******************@#***#@ @@#*%#*#*%@@\r\n" + //
                    "    @@***#%##**%@ @#*#@#*************************************%%**#@@@**%***#*%@@\r\n" + //
                    "    @@#**#***%#**%@##%*****************##%*#############*********%@@#*%****#**@@\r\n" + //
                    "     @@**#****#%**%@*%%%@%%%########%%%#*********#%@@%%%%@@@@@@%*@@**%*****#**@@\r\n" + //
                    "     @@%**#*****%#*@%****#@@@@#%***%@#*%*****%*#%++++@@@@@%#**###@#*#********#@@\r\n" + //
                    "      @@#*********#*#*#%##%@%#++*#@#***#*****#***#%@%####@##*%@*@#*#***##****%@@\r\n" + //
                    "       @@%****%###*#**%#****#####**#*********#***##***********##***%********%@@ \r\n" + //
                    "        @@@#********#****#********@#**#***********#@%*****%@%*****#********%@@  \r\n" + //
                    "           @@@*****@**#***%%****@%****#**************@@@@@#****%%*#*##****@@@   \r\n" + //
                    "             @@%***#**%#*****#%*******#%**********************#@#*#@#***#@@     \r\n" + //
                    "              @@%***#*#@#**************#%********************%@###*****@@@      \r\n" + //
                    "               @@#*****#@#**********#***%****#%@@#********#%@%**##***#@@@       \r\n" + //
                    "                @@%******%@@##*****%@********#@%**%***%@@@%#********#@@         \r\n" + //
                    "                 @@#******#%%%#***#@##%%#***#%****#%****%@%#********%@@         \r\n" + //
                    "                  @@@#***#%@%*****@****#%@%@#******##***%@@@@#****#@@@          \r\n" + //
                    "                    @@@@@@@@@#****%******#**************#@@ @@@@@@@@@           \r\n" + //
                    "                            @#***#*******##%%%%**********@@                     \r\n" + //
                    "                            @%********@@%###**##@@@#*****@@                     \r\n" + //
                    "                            @%*****%@%*************#%****#@@@                   \r\n" + //
                    "                            @#****************************%@@                   \r\n" + //
                    "                           @%**************************%@@@                     \r\n" + //
                    "                             @@@##*****************#%@@@@@                      \r\n" + //
                    "                              @@#%@@%#**********#%@@%##%@@                      \r\n" + //
                    "                              @@####%%@@##**##%@@%#####%@@                      \r\n" + //
                    "                              @@%#######%@@@%%#########%@@                      \r\n" + //
                    "                              @@%######################%@@                      \r\n" + //
                    "                              @@#######%###############%@@                      \r\n" + //
                    "                              @@######%@%#####%@#######%@@                      \r\n" + //
                    "                             @@%#######@@#####@@########@@@                     \r\n" + //
                    "                             @@########%@@###@@%########%@@                     \r\n" + //
                    "");
            mudarCor(-1);
        }

    }

    public static void interaçaoPirata() {
        System.out.println("Aperte esc para pular o dialogo e enter para prosseguir");
        int entrada = obtemTeclaPressionada();

        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        if (entrada != 27) {
            mudarCor(AMARELO);
            imprimirDevagar("Hmm...");
            BibliotecaTCA.esperar(500);
            imprimirDevagar("Nao faco ideia de quem voce e");
            BibliotecaTCA.esperar(500);
            imprimirDevagar("Mas nao vou deixar voce levar esse tesouro tao facil");
            BibliotecaTCA.esperar(500);
            System.out.println("⠀⠀⠀⠀⠀⢀⣤⣶⣾⣿⣿⣿⣷⣶⣤⡀⠀⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⢰⡟⠛⠉⠙⢻⣿⡟⠋⠉⠙⢻⡇⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⢸⣷⣀⣀⣠⣾⠛⣷⣄⣀⣀⣼⡏⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⣀⠀⠀⠛⠋⢻⣿⣧⣤⣸⣿⡟⠙⠛⠀⠀⣀⠀⠀\r\n" + //
                    "⢀⣰⣿⣦⠀⠀⠀⠼⣿⣿⣿⣿⣿⡷⠀⠀⠀⣰⣿⣆⡀\r\n" + //
                    "⢻⣿⣿⣿⣧⣄⠀⠀⠁⠉⠉⠋⠈⠀⠀⣀⣴⣿⣿⣿⡿\r\n" + //
                    "⠀⠀⠀⠈⠙⠻⣿⣶⣄⡀⠀⢀⣠⣴⣿⠿⠛⠉⠁⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⠀⠀⠀⠉⣻⣿⣷⣿⣟⠉⠀⠀⠀⠀⠀⠀⠀\r\n" + //
                    "⠀⠀⠀⠀⢀⣠⣴⣿⠿⠋⠉⠙⠿⣷⣦⣄⡀⠀⠀⠀⠀\r\n" + //
                    "⣴⣶⣶⣾⡿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠙⠻⣿⣷⣶⣶⣦\r\n" + //
                    "⠙⢻⣿⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⡿⠋\r\n" + //
                    "⠀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠀⠀");
            mudarCor(-1);
        }
    }

    public static void interaçaoReiPerdido() {
        System.out.println("Aperte esc para pular o dialogo e enter para prosseguir");
        int entrada = obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        if (entrada != 27) {
            mudarCor(208);
            imprimirDevagar("Que sono!!!");
            BibliotecaTCA.esperar(500);
            imprimirDevagar("QUEM OUSA ME ACORDAR DE UM SONO DE MAIS DE 1000 ANOS");
            BibliotecaTCA.esperar(500);
            mudarCor(VERMELHO);
            imprimirDevagar("(A estatua começa a brilhar e rachar)");
            BibliotecaTCA.esperar(500);
            mudarCor(208);
            imprimirDevagar("Sinta a minha furia");
            BibliotecaTCA.esperar(500);
            System.out.println(
                    "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@%***%@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@@@@@%***==+*****+==***%@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@@@#=****+==***#*==+****=#@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@***===+****==**+**==*#**+===***@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@#***+==****+=*****=+****==+*#*#@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@#***==+****=*****=****+==***#@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@************=:+************@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@#*******##**=:=***#*******#@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@%*******+++++*++++********%@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@+*+==+++*#########*+++==**+@@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@+===+##*++***###***+**##+===+@@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@%===+###=**=#######+**=###====%@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@====+###+@*%%#####%%*%+###+====@@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@%****+##########*##########+****%@@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@*=+#*+=########*****########=+*#+=*@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@====**==*######*+++*#######==**====@@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@+==----===*#+===========*#*===---===+@@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@+==-----===**===*#####*===**===-----==+@@@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@#====----====+===###***###+==+====----====#@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@@@======--======*#####===#####*======--======@@@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@%%%%@@@======-========*##+===+##*========-======@@@%%%%@@@@@@@@@@\r\n" + //
                            "@@@@@@@@%%%%%%%%@+=====---=======================---=====+@%%%%%%%%@@@@@@@@\r\n" + //
                            "@@@@@@@@@%%%%%%%%%*===-----=====================-----===*%%%%%%%%%@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@%%%%%%%%%+--------+===============+--------+%%%%%%%%%@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@%%%%%%%%%=---=*****===========****+=---=%%%%%%%%%@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@@@@+%%%%%%%%%######*****++=++*****######%%%%%%%%%+@@@@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@@@@--::.:%%%%%%%%%#######*********#######%%%%%%%%%:.:::-@@@@@@@@@@@\r\n" + //
                            "@@@@@@@@#--::::::.-%%%%%%%%%###################%%%%%%%%%-.:::--:--#@@@@@@@@\r\n" + //
                            "@@@@@@%::-:::::::::.:#%%%%%%%%%#############%%%%%%%%%#:.:::::::::---%@@@@@@\r\n" + //
                            "@@@@@+:::::::::::::::..#%%%%%%%%%#########%%%%%%%%%#..:::::::::::::--+@@@@@\r\n" + //
                            "@@@@+::::::::::::::::::.:*%%%%%*%%%#####%%%##%%%%*:.::::::::::::::::::+@@@@\r\n" + //
                            "@@@#:::::::::::::::::::::.:+%#***%%%##*%%%#**#%+..:::::::::::::::::::::#@@@\r\n" + //
                            "@@@::::::::::::::::::::::::..*%%%%####***%%%%*..:-::::::::::::::::::::--@@@\r\n" + //
                            "@@+::::::::::::::::::::::::::..+%%####***%%+..::::::::::::::::::::::::::+@@\r\n" + //
                            "@@=::::::::::::::::::::::::::::..+####***=..::::::::::::::::::::::::::::=@@\r\n" + //
                            "@@-::::::::::::::::::::::::::::-:.*###***:::::::::::::::::::::::::::::::-@@\r\n" + //
                            "@@::::::::::::::::::::::::::::::::::+#+::::::::::::::::::::::::::::::::::@@\r\n" + //
                            "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\r\n" //
            );
            mudarCor(-1);
        }
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
        String[][] matBasePuzzle1 = new String[mat1.length][mat1.length];
        matBasePuzzle1 = mat1;
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
        // movimentacao(mat1, matcop);

        int areasExploradas = 0;
        String[] areas = { "Aldeia dos Goblins", "Ilha do Pirata", "Tumba do Rei Perdido" };
        String[] senhas = { "maca", "banana", "uva" };
        boolean[] areasCompletas = { false, false, false };
        int[] statusInimigo = new int[2];
        while (areasExploradas != 3) {
            System.out.println("Escolha a area que deseja explorar");
            BibliotecaTCA.esperar(2000);
            int area = BibliotecaTCA.escolherNoMenu("   Areas   ", areas);
            if (area == 0) {
                if (areasCompletas[0] == true) {
                    mudarCor(VERMELHO);
                    System.out.println("Voce ja explorou " + areas[0]);
                    mudarCor(-1);
                    continue;
                }
                boolean passou = false;
                while (passou == false) {
                    matBasePuzzle1 = atualizarMatriz(mat1, matBasePuzzle1);
                    passou = puzzle1(matBasePuzzle1, matcop1);
                }
                do {
                    interaçaoGoblinAnciao();
                    mudarCor(VERMELHO);
                    System.out.println("E entao voce acha o boss");
                    mudarCor(-1);
                    BibliotecaTCA.esperar(2000);
                    statusInimigo = goblinAnciao();
                    areasCompletas[0] = sistemaDBatalha(statusInimigo[1], statusInimigo[0]);
                } while (areasCompletas[0] == false);
                areasExploradas++;
                continue;
            } else if (area == 1) {
                if (areasCompletas[1] == true) {
                    mudarCor(VERMELHO);
                    System.out.println("Voce ja explorou " + areas[1]);
                    mudarCor(-1);
                    continue;
                }
                puzzle2(mat2);
                do {
                    interaçaoPirata();
                    mudarCor(VERMELHO);
                    System.out.println("E entao voce acha o boss");
                    mudarCor(-1);
                    BibliotecaTCA.esperar(2000);
                    statusInimigo = pirata();
                    areasCompletas[1] = sistemaDBatalha(statusInimigo[0], statusInimigo[1]);
                } while (areasCompletas[1] == false);
                areasExploradas++;
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
                    interaçaoReiPerdido();
                    mudarCor(VERMELHO);
                    System.out.println("E entao voce acha o boss");
                    mudarCor(-1);
                    BibliotecaTCA.esperar(2000);
                    statusInimigo = reiPerdido();
                    areasCompletas[2] = sistemaDBatalha(statusInimigo[0], statusInimigo[1]);
                } while (areasCompletas[2] == false);
                areasExploradas++;
                continue;
            }
        }

    }
}
