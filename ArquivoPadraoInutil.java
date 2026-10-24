//

import java.util.Scanner;
import java.util.Locale;

public class ArquivoPadraoInutil{

    public int variavelGlobal;

    public final static Scanner TECLADO = new Scanner(System.in);
    static {
    TECLADO.useLocale(Locale.US);
    }
    
    public static void imprimir(String texto){
        System.out.println(texto);
    }

    public static void imprimirF(String texto){
        System.out.printf(texto);
    }

    public static int lerValorInteiro(){
        int valor;
        valor = TECLADO.nextInt();
        return valor;
    }

    public static double lerValorReal(){
        double valor;
        valor = TECLADO.nextDouble();
        return valor;
    }

    public static String LerTexto(){
        String valor;
        valor = TECLADO.nextLine();
        return valor;
    }

    public static char lerCaractere(){
        char valor;
        valor = TECLADO.next().charAt(0);
        return valor;
    }

    public static boolean lerBooleano(){
        boolean valor;
        valor = TECLADO.nextBoolean();
        return valor;
    }

    public static void Tempo()throws InterruptedException{
        Thread.sleep(1500);
    }   

    /*public static (){

        return 
    }*/

    public static void main(String[] args)throws InterruptedException{

        

        // entrada:



        // processamento:



        // saída:



    }
}
