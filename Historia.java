import java.util.Scanner;
import java.util.Locale;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Historia{
    public static void main(String[] args){

        Funcoes.imprimirDevagar("tuff");

        String [] opcoes = {            
            "Começar          ",
            "Sobre o jogo     ",
            "Créditos     ",
            "Sair             "
        };

        int opcaoSelecionada = 0;
        Funcoes.atualizarMenu(  opcaoSelecionada, opcoes);
        
        while (true) {   
            BibliotecaTCA.esconderCursor();         
            if ( Funcoes.pressionouTecla()) {
                int ch = Funcoes.obtemTeclaPressionada();     
                
                switch (ch) {
                    case 'w':
                    case 'W':
                        opcaoSelecionada = moverLinhaMenu(opcaoSelecionada, opcoes.length, -1);
                        Funcoes.atualizarMenu(opcaoSelecionada, opcoes);

                        break;  

                    case 's':
                    case 'S':
                        opcaoSelecionada = moverLinhaMenu(opcaoSelecionada, opcoes.length, 1);
                        Funcoes.atualizarMenu(opcaoSelecionada, opcoes);
                        break;

                    case 'a':
                    case 'A':
                        if (opcaoSelecionada % 2 == 1) {
                            opcaoSelecionada--;
                            Funcoes.atualizarMenu(opcaoSelecionada, opcoes);
                        }
                        break;

                    case 'd':
                    case 'D':
                        if (opcaoSelecionada % 2 == 0 && opcaoSelecionada + 1 < opcoes.length) {
                            opcaoSelecionada++;
                            Funcoes.atualizarMenu(opcaoSelecionada, opcoes);
                        }
                        break;

                    case 13: //Código ASCII/Decimal para a tecla ENTER (Carriage Return)
                        if (opcaoSelecionada == 3) {
                            return;
                        }
                        Funcoes.executarAcaoMenu(opcaoSelecionada);
                        Funcoes.atualizarMenu(opcaoSelecionada, opcoes);
                        break;
                }
                
            }
            
        }        

    }

    private static int moverLinhaMenu(int opcaoSelecionada, int quantidadeOpcoes, int direcao) {
        int quantidadeLinhas = (quantidadeOpcoes + 1) / 2;
        int coluna = opcaoSelecionada % 2;
        int linha = opcaoSelecionada / 2;
        linha = (linha + direcao + quantidadeLinhas) % quantidadeLinhas;

        int destino = linha * 2 + coluna;
        return Math.min(destino, quantidadeOpcoes - 1);
    }

    
}