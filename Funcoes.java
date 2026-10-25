import java.util.Random;
import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

//teste
public class Funcoes {
    public final static Scanner TECLADO = new Scanner(System.in);

// =============================FUNCOES MENU INICIO====================================
     // Liga o Java às funções _kbhit e _getch do Windows (só usada no Windows)
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);
        int _kbhit();
        int _getch();
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
    //   System.out.println("GAME OVER");
    //   esperarTecla();
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
    public static final int PRETO      = 0;
    public static final int VERMELHO   = 1;
    public static final int VERDE      = 2;
    public static final int AMARELO    = 3;
    public static final int AZUL       = 4;
    public static final int MAGENTA    = 5;
    public static final int CIANO      = 6;
    public static final int CINZA      = 7;
    public static final int BRANCO     = 15;
    public static final int COR_PADRAO = -1; // a cor original do terminal

    // Muda a cor do TEXTO. Tudo o que for escrito depois sai nessa cor.
    //
    // A cor é um número de 0 a 255 (são 256 cores!). Para as cores
    // básicas, use as constantes acima. Para ver todas as 256 cores e
    // escolher o número, chame a função mostrarCores().
    // Use COR_PADRAO (-1) para voltar à cor original do terminal.
    //
    // Exemplo:
    //   mudarCor(VERDE);                  // texto verde
    //   System.out.println("Você venceu!");
    //   mudarCor(208);                    // texto laranja (uma das 256 cores)
    //   System.out.println("Cuidado!");
    //   mudarCor(COR_PADRAO);             // volta ao normal
    //
    // ATENÇÃO:
    //   - Use mudarCor(COR_PADRAO) no fim do programa para o terminal não ficar colorido.
    //   - Cuidado com texto preto em terminal escuro (ou branco em terminal claro):
    //     ele fica invisível!
    //   - Só funciona rodando no terminal (não no console do Eclipse/NetBeans).
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
    //   mudarCorFundo(AZUL);              // fundo azul
    //   mudarCor(AMARELO);                // texto amarelo
    //   System.out.print(" PLACAR: 10 ");
    //   mudarCorFundo(COR_PADRAO);        // volta o fundo ao normal
    //   mudarCor(COR_PADRAO);             // volta o texto ao normal
    //
    // Dica: escreva espaços " " com a cor de fundo para desenhar blocos coloridos.
    public static void mudarCorFundo(int cor) {
        if (cor >= 0 && cor <= 255) {
            System.out.print("\033[48;5;" + cor + "m");
        } else {
            System.out.print("\033[49m"); // cor padrão do terminal
        }
    }   


    public static void atualizarMenu(int opcaoSelecionada, String[] opcoes) {
        limparConsole();

        int larguraOpcao = "MENU INICIAL".length();
        for (String opcao : opcoes) {
            larguraOpcao = Math.max(larguraOpcao, opcao.trim().length());
        }
        int larguraBotao = larguraOpcao + 4;
        int larguraInterior = larguraBotao * 2 + 6;
        String borda = "═".repeat(larguraInterior);

        System.out.println();
        System.out.println();
        System.out.println();
        mudarCor(AZUL);
        imprimirDevagarMenu("╔" + borda + "╗");
        System.out.println();
        String titulo = "MENU INICIAL";
        int espacoTitulo = larguraInterior - titulo.length();
        imprimirDevagarMenu("║" + " ".repeat(espacoTitulo / 2) + titulo
                + " ".repeat(espacoTitulo - espacoTitulo / 2) + "║");
        System.out.println();
        imprimirDevagarMenu("╠" + borda + "╣");
        System.out.println();
        int linhas = (opcoes.length + 1) / 2;
        for (int linha = 0; linha < linhas; linha++) {
            int indiceEsquerdo = linha * 2;
            int indiceDireito = indiceEsquerdo + 1;

            mudarCor(AZUL);
            imprimirDevagarMenu("║  ");
            imprimirBordaBotao(larguraBotao, "┌", "┐", indiceEsquerdo == opcaoSelecionada);
            mudarCor(AZUL);
            imprimirDevagarMenu("  ");
            imprimirBordaBotao(larguraBotao, "┌", "┐", indiceDireito == opcaoSelecionada);
            mudarCor(AZUL);
            imprimirDevagarMenu("  ║");
            System.out.println();

            mudarCor(AZUL);
            imprimirDevagarMenu("║  ");
            imprimirConteudoBotao(opcoes[indiceEsquerdo], indiceEsquerdo == opcaoSelecionada, larguraBotao);
            mudarCorFundo(COR_PADRAO);
            mudarCor(AZUL);
            imprimirDevagarMenu("  ");
            if (indiceDireito < opcoes.length) {
                imprimirConteudoBotao(opcoes[indiceDireito], indiceDireito == opcaoSelecionada, larguraBotao);
            } else {
                imprimirConteudoBotao("", false, larguraBotao);
            }
            mudarCor(AZUL);
            imprimirDevagarMenu("  ║");
            System.out.println();

            mudarCor(AZUL);
            imprimirDevagarMenu("║  ");
            imprimirBordaBotao(larguraBotao, "└", "┘", indiceEsquerdo == opcaoSelecionada);
            mudarCor(AZUL);
            imprimirDevagarMenu("  ");
            imprimirBordaBotao(larguraBotao, "└", "┘", indiceDireito == opcaoSelecionada);
            mudarCor(AZUL);
            imprimirDevagarMenu("  ║");
            System.out.println();
        }
        mudarCor(AZUL);
        imprimirDevagarMenu("╚" + borda + "╝");
        System.out.println();
        System.out.println();
        System.out.println("Use A/D para os lados, W/S para cima/baixo e ENTER para selecionar.");

    }

    private static void imprimirBordaBotao(int largura, String esquerda, String direita, boolean selecionado) {
        mudarCorFundo(COR_PADRAO);
        if (selecionado) {
            mudarCor(VERMELHO);
        } else {
            mudarCor(AZUL);
        }
        imprimirDevagarMenu(esquerda + "─".repeat(largura - 2) + direita);
        mudarCor(COR_PADRAO);
    }

    private static void imprimirConteudoBotao(String texto, boolean selecionado, int largura) {
        String rotulo = texto.trim();
        int larguraConteudo = largura - 2;
        String indicador = "  ";
        if (selecionado) {
            indicador = "> ";
        }
        int espacos = larguraConteudo - indicador.length() - rotulo.length();

        mudarCorFundo(COR_PADRAO);
        if (selecionado) {
            mudarCor(VERMELHO);
        } else {
            mudarCor(AZUL);
        }
        imprimirDevagarMenu("│");
        if (selecionado) {
            mudarCor(VERMELHO);
        } else {
            mudarCor(COR_PADRAO);
        }
        imprimirDevagarMenu(indicador + rotulo);
        mudarCor(COR_PADRAO);
        imprimirDevagarMenu(" ".repeat(espacos));
        if (selecionado) {
            mudarCor(VERMELHO);
        } else {
            mudarCor(AZUL);
        }
        imprimirDevagarMenu("│");
        mudarCor(COR_PADRAO);
    }

    public static void executarAcaoMenu(int opcaoSelecionada) {
        
        limparConsole();

        switch (opcaoSelecionada) {
            case 0:
                mudarCor(AZUL);
                imprimirDevagar("\n\nComeçar novo jogo selecionado.\n\n");
                
                break;
            case 1:
                mudarCor(AZUL);
                imprimirDevagar("\n\nSobre o jogo selecionado.\n\n");           
                break;
            case 2:
                mudarCor(AZUL);
                imprimirDevagar("\n\nCréditos selecionado.\n\n");
                break;
            default:
                mudarCor(VERMELHO);
                imprimirDevagar("\n\nOpção inválida.\n\n");
                break;
        }
        esperarTecla();
        limparConsole();

    }// =============================FUNCOES MENU FIM====================================


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

     // Método que imprime o menu letra por letra
    public static void imprimirDevagarMenu(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            System.out.print(texto.charAt(i)); 
            System.out.flush();                
            
            try {
                Thread.sleep(0,5);
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
