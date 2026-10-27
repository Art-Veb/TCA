import java.util.Random;
import java.util.Scanner;
import com.sun.jna.Library;
import com.sun.jna.Native;

//teste
public class Funcoes {
    public final static Scanner TECLADO = new Scanner(System.in);

    // =============================FUNCOES MENU
    // INICIO====================================
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

    public static void informativoInimigo(int ataqueInimigo, int vidaInimiga) {
        System.out.println("Vida: " + vidaInimiga);
        System.out.println("Ataque: " + ataqueInimigo);
    }

    public static void informativoJogador(int ataque, int vida,int pontosDeAcao) {
        System.out.println("          Vida: " + vida);
        System.out.println("          Ataque: " + ataque);
        System.out.println("          Pontos de acao: " + pontosDeAcao);
    }
    public static boolean sistemaDBatalha(int vidaInimiga, int ataqueInimigo) {
        boolean lutaVencida = false;
        int[] ataque = { 30, 10 };
        int cargaEspecialPersonagem = 0;
        int cargaEspecialInimigo = 0;
        int vida = 100;
        int cura = 30;
        int ataqueBaseInimigo = ataqueInimigo;
        String[] opcoes = { "Ataque", "Defesa", "Cura", "Status" };
        String[] ataques = { "Ataque pesado", "Ataque leve" };
        String[] defesas = { "Defesa imparcial", "Defesa completa" };
        String[] espaco = {"   "};
        while (true) {
            if (vida <= 0) {
                System.out.println("Voce perdeu clique enter para voltar ao checkpoint");
                int restart = BibliotecaTCA.escolherNoMenu("Voce morreu", espaco);
                if (restart == 13) {
                    return lutaVencida = false;
                }
            }
            int pontosDeAcao = 3;
            while (pontosDeAcao != 0) {
                int escolha = BibliotecaTCA.escolherNoMenu("   Batalha   ", opcoes);
                int subescolha = 0;
                if (escolha == 0) {
                    subescolha = BibliotecaTCA.escolherNoMenu("   Ataques   ", ataques);
                    if (subescolha == 0) {
                        if (pontosDeAcao >= 2) {
                            int sorteio = SORTEIO.nextInt(3);
                            if (sorteio == 2) {
                                vidaInimiga -= ataque[0];
                                pontosDeAcao -= 2;
                                if (vidaInimiga <= 0) {
                                    return lutaVencida = true;
                                }
                            } else {
                                pontosDeAcao -= 2;
                            }

                        } else {
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            continue;
                        }
                    } else if (subescolha == 1) {
                        if (pontosDeAcao >= 1) {
                            vidaInimiga -= ataque[1];
                            pontosDeAcao--;
                            if (vidaInimiga <= 0) {
                                    return lutaVencida = true;
                                }
                        } else {
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            continue;
                        }
                    } else if (subescolha == 27) {
                        continue;
                    }
                } else if (escolha == 1) {
                    subescolha = BibliotecaTCA.escolherNoMenu("   Defesas   ", defesas);
                    if (subescolha == 0) {
                        if (pontosDeAcao >= 1) {
                            ataqueInimigo /= 2;
                            pontosDeAcao--;
                        } else {
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            continue;
                        }
                    } else if (subescolha == 1) {
                        if (pontosDeAcao == 3) {
                            ataqueInimigo = 0;
                            pontosDeAcao-=3;
                        } else {
                            System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                            continue;
                        }
                    } else if (subescolha == 27) {
                        continue;
                    }
                } else if (escolha == 2) {
                    if (vida > 70) {
                        System.out.println("Voce n esta machucado o suficiente para usar a pocao");
                    } else if (pontosDeAcao == 3) {
                        vida += cura;
                        pontosDeAcao -= 3;
                    } else {
                        System.out.println("Voce nao tem PA (pontos de acao) suficiente");
                        continue;
                    }
                } else if (escolha == 3) {
                    informativoInimigo(ataqueInimigo, vidaInimiga);
                    if (subescolha == 27) {
                        continue;
                    }
                }

            }
            vida -= ataqueInimigo;
            ataqueInimigo = ataqueBaseInimigo;
        }

    }

    public static int[] goblinAnciao(){
        int[] status = new int[2];
        int dano = 50;
        int vida = 75;
        status[0] = vida;
        status[1] = dano; 
        return status;
    }

    public static int[] pirata(){
        int[] status = new int[2];
        int dano = 25;
        int vida = 120;
        status[0] = vida;
        status[1] = dano; 
        return status;
    }

    public static int[] reiPerdido(){
        int[] status = new int[2];
        int dano = 40;
        int vida = 150;
        status[0] = vida;
        status[1] = dano; 
        return status;
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
                { ". ", ". ", ". ", "# ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". ", ". " },
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
        int areasExploradas = 0;
        String[] areas = {"Aldeia dos Goblins", "Ilha do pirata", "Tumba do Rei Perdido"};
        String[] senhas = { "maca", "banana", "uva" };
        boolean terminou = false;
        int[] statusInimigo = new int[2];
        while (areasExploradas != 3) {
            System.out.println("Escolha a area que deseja explorar");
            int area = BibliotecaTCA.escolherNoMenu("   Areas   ", areas);
            if (area == 0) {
                do{
                    puzzle1(mat1, matcop1);
                    System.out.println("E entao voce acha o boss");
                    statusInimigo = goblinAnciao();
                    terminou = sistemaDBatalha(statusInimigo[0], statusInimigo[1]);
                }while(terminou == false);
                terminou = false;
                continue;
            }else if (area == 1) {
                do{
                    puzzle2(mat2, matcop2);
                    System.out.println("E entao voce acha o boss");

                    statusInimigo = pirata();
                    terminou = sistemaDBatalha(statusInimigo[0], statusInimigo[1]);
                }while(terminou == false);
                terminou = false;
                continue;
            }else if (area == 2) {
                puzzle3(senhas);
                do{
                    System.out.println("E entao voce acha o boss");
                    statusInimigo = reiPerdido();
                    terminou = sistemaDBatalha(statusInimigo[0], statusInimigo[1]);
                }while(terminou == false);
                terminou = false;
                continue;
            }
        }
        
        
        // puzzle3(senhas);
        // puzzle2(mat2, matcop2);

    }
}
