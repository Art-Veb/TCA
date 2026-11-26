public class Historia {
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
    public static final int COR_PADRAO = -1;
    public static final int LARANJA = 208;

    public static void part1(){
        TCA.limparTela();
        TCA.mudarCor(VERMELHO);
        System.out.println("Aperte esc para pular o dialogo e enter para prosseguir");
        TCA.mudarCor(COR_PADRAO);
        TCA.limparTela();
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        TCA.mudarCor(VERMELHO);
        TCA.imprimirDevagar("NARRADOR: ");
        TCA.mudarCor(COR_PADRAO);
        TCA.imprimirDevagar("Um aventureiro surge em uma cidade que estava sendo influenciada por monstros");
    }

    public static void partFinal(){
        TCA.mudarCor(LARANJA);
        TCA.imprimirDevagar("Parabéns jovem Aventureiro, você conseguiu superar os obstáculos e inimigos bravamente e também mostrou alta inteligência ao administar seus pontos de upgrade\nÉ uma vitória mais que merecida, obrigado por jogar meu jogo");
        TCA.mudarCor(COR_PADRAO);
    }

    public static void interacaoGoblinAnciaoInicio() {
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        if (entrada != 27) {
            System.out.println();
            TCA.mudarCor(VERDE);
            TCA.imprimirDevagar("Você realmente acha que vai escapar daqui com vida???");
            BibliotecaTCA.esperar(500);
            TCA.imprimirDevagar("Nós goblins temos ódio à raça humana");
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
            TCA.mudarCor(-1);
        }

    }

    public static void interacaoGoblinAnciaoFinal(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir (parte essencial para o puzzle 3)");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        TCA.mudarCor(VERDE);
        TCA.imprimirDevagar("Entendo entao nao existem raças superiores às outras. . .");
        TCA.mudarCor(COR_PADRAO);

    }

    public static void interacaoPirataInicio() {
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();

        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        if (entrada != 27) {
            TCA.mudarCor(AMARELO);
            TCA.imprimirDevagar("Hmm...");
            BibliotecaTCA.esperar(500);
            TCA.imprimirDevagar("Nao faço ideia de quem você é");
            BibliotecaTCA.esperar(500);
            TCA.imprimirDevagar("Mas nao vou deixar você levar esse tesouro tao fácil");
            BibliotecaTCA.esperar(500);
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*+%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%--=#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%+:+*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*:+*++%%%%%%%%%%%%%+-+%%%%%%%%%%%+%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%###=*%%%%%%%%%%%%%%%+**%%%%%%%%%*==%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**#%%%%%%%%%%%%#+*#+#%%%%%%%%#=%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*-.#%%%%%%%%%%%%%##%%%%%%%%%*=#%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%#%%%%%%%%%%%%%%%+:::*%%%%%%%%%%#-#-%%%%%%%%%#*#%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%#=:*%%%%%%%%%%%%#= :.+*+#%%%%#*-#:%#=#%%%%%%**+%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%*%=#%%%%%%%%%%-.   :*-+.#%:  .*:%%#-=+=*##%*+%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%#*=*#%%%%%%%%*.     .=+*.=%*:.:=.---.-=#%%%#:%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%##%%%%%%%%%%%%%*+:-+++=:-#:     ...:=+++**###*#****+=--+-%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%*-=%%%%%%%%%%%*...:#%**.--..=++**+-==*#-..-:=+*:-=*+=*+#*%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%#-#%%%%%%%%-.   :*==-+*#==:.--=++.=#:  -#**%--*#%%%++%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%#*#%%%%#**=..:+-**#*-.#+. =##**=:-#-..-++##=-**#%##-#%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%#=-=+#%-*=*-###-+##-:.*...:=-..:::=++**####*+-::+*=%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%*+%%#=-*+*. :.==##+.:. :*##=+**+-:.... ..*%%#*=#%*%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%*=+=*+=*#=..-===:-**+*=:...:.      .. ..:#%%+=*+%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%*+*+#%-*%=-=-*#%+:..      =.     ::... .*%%%:%=#%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%#*+*#%%-=-*##=.           +.   .:=*:  ..*%%#=%*=%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%+=#++:*#%%#=.           +.     .:.  .+%%%=+%#=%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%#+:**%=#%%%+.          -.         .=%%%#:%%#=%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%#*+%%%=%#*=-=..-=..  . .... ..--..--=*##:%%#=%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%####*:.       .++=:-   ::=+=..   .   :#*#*#%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%+%-.##*++-.-. ..:*#:-:**-.. .-.-++*##.-%#%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%##*.=*#%%%%%#**--::=###=::-:**#%%%%%#*+.=*%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*#+:#*#%%%%%%%%%+****#****+#%%%%%%%%%*#-*##%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%+*:=*%%%%%%%%%%%%%*=.+*%%%%%%%%%%%%%++-#*%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*#-.:#%%%%%%%%%%#*+-::*##%%%%%%%%%%#-.:#*%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#.-..:-++*##%%##=****%*++*-*#%%##*++-: :-.#%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%=:.-.::..++==:*.-+:#%#=+-.+:===+...:.-..-%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%=..::-=:  ....::.#%%%*....... .:=-::-.:#%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%=*-...:...:++-..-+%%%%%*-..:++:...:...=*#%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**+--:.:-:..-.-.*%%*%%#-+.-..:-:::=+***#%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*****#%###-.:+-=*-*=:=:.:*##%#****+#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%##**%+=-......=......:=+%#+##%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%##==#%#*+-:.:..::.:.--=+#%#==##%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%+#.=*%*=##*+=+-.-+=++##=+%#+:**%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%+*:..-+#=--+-#*=**:=:-+#+=:.:-=%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#+-.:-+-+++---.=.-:-=*=-==:.-=#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#*-..-+=:-:**=+-**---=+-..-+*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#+#=:..++=:-::::::-=*=:. +#*#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*#+.:::--=*+*+*+--:::.-#*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*+-.. :.=:*:-.: ..-=##%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*##*:   :=+=.   :*#++%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#**#*=.......-*##*#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#***********#%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n" + //
                                "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            TCA.mudarCor(-1);
        }
    }

    public static void interacaoPirataFinal(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir (parte essencial para o puzzle 3)");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        TCA.mudarCor(AMARELO);
        TCA.imprimirDevagar("Nem só de riquezas vive um homem hehehe");
        TCA.mudarCor(COR_PADRAO);
    }

    public static void interacaoReiPerdidoInicio() {
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        if (entrada != 27) {
            TCA.mudarCor(LARANJA);
            TCA.imprimirDevagar("Que sono!!!");
            BibliotecaTCA.esperar(500);
            TCA.imprimirDevagar("QUEM OUSA ME ACORDAR DE UM SONO DE MAIS DE 1000 ANOS");
            BibliotecaTCA.esperar(500);
            TCA.mudarCor(VERMELHO);
            TCA.imprimirDevagar("(A estátua começa a brilhar e rachar)");
            BibliotecaTCA.esperar(500);
            TCA.mudarCor(LARANJA);
            TCA.imprimirDevagar("Sinta a minha fúria");
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
            TCA.mudarCor(-1);
        }
    }

    public static void interacaoReiPerdidoFinal() {
        TCA.limparTela();
        System.out.println("Aperte ESC para pular o diálogo e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }
        BibliotecaTCA.esperar(2000);
        TCA.mudarCor(LARANJA);
        TCA.imprimirDevagar("Que sono . . . Acho que vou voltar a dormir");
        TCA.mudarCor(COR_PADRAO);
    }

    public static void instrucoesPuzzle2(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular as instruçoes e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }else if (entrada == 13) {
         TCA.mudarCor(AMARELO);
         TCA.imprimirDevagar("Caça Ao Tesouro:\nVocê terá 45 segundos para achar o Tesouro\nPressionar ENTER revelará se você está Quente, Frio ou Morno do Tesouro\nMas cuidado!!! Por todo o mapa estarao espalhadas areias movediças, pisar nelas resetará o mapa\nPressionar V irá revelar areias num raio de 1 quadrado\nPorém você só pode usar a habilidade Revelar a cada 5 segundos"); 
        
            
         TCA.mudarCor(COR_PADRAO);
        }
    }
    public static void instrucoesPuzzle1(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular as instruçoes e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }else if (entrada == 13) {
            TCA.mudarCor(VERMELHO);
            TCA.imprimirDevagar("Labirinto de Barreiras Invisíveis:\nChegue ao final do Labirinto para escapar!!!\nVocê tem um total de 4 vidas por tentativa\nE 5 tentativas\nBater em uma Barreira consumirá 1 vida\nSe a vida chegar a 0 você terá uma tentativa consumida\nConsumir todas as suas tentativas fará o mapa resetar");
            TCA.esperarTecla();
            TCA.mudarCor(COR_PADRAO);

        }
    }
    public static void instrucoesSistemaDeBatalha(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular as instruçoes e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }else if (entrada == 13) {
            TCA.mudarCor(VERMELHO);
            TCA.imprimirDevagar("Sistema de Batalha:\nRPG com pontos de açao\nA cada rodada você ganhara 3 pontos de açao, a rodada só prosseguirá se você gastar todos os pontos\nTabela Ataques:\nAtaque Pesado = 2 pontos (Causa 30 de dano base e carrega o Ataque Especial, porém tem 25% de chance de errar; errar nao carregara o Ataque Especial)\nAtaque Leve = 1 ponto (Causa 10 de dano base,porém tem 100% de chance de acertar)\nAtaque Especial (5 cargas/Ataques Pesados) = 1 ponto\nOBS: O Ataque Especial causa o dobro do dano do Ataque Pesado e tem 100% de chance de acerto\nTabela Defesas:\nDefesa Imparcial = 1 ponto (Defende 50% + pontos de Eficiência de Defesa)\nDefesa Completa: 3 pontos (Defende Totalmente)\nCura = 0 pontos, pode ser usada 3 vezes (Cura 30 por uso)");
            
            TCA.esperarTecla();
            TCA.mudarCor(COR_PADRAO);
        }
    }
    public static void instrucoesPuzzle3(){
        TCA.limparTela();
        System.out.println("Aperte ESC para pular as instruçoes e ENTER para prosseguir");
        int entrada = TCA.obtemTeclaPressionada();
        if (entrada == 27) {
            System.out.println();
            return;
        }else if (entrada == 13) {
            TCA.mudarCor(VERMELHO);
            TCA.imprimirDevagar("Questionario:\nUm quizz que você deve responder corretamente para ir para o ultimo boss\nSerá reduzido 10 pontos na sua vida por erro(Nao é permanente, apenas para o Boss, porém toda vez que voce entrar em batalha com o Boss será descontado de seus erros)\n(Ele irá medir o quanto você prestou a atençao no jogo)");
            TCA.esperarTecla();
            TCA.mudarCor(COR_PADRAO);
        }
    }
}
