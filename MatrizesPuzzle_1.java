public class MatrizesPuzzle_1 {
    
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
            { "# ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", "[] " }
        };
         boolean[][] matcop2 = {
                { true, false, true, true, true, true },
                { true, false, true, false, true, true },
                { true, false, true, false, true, true },
                { true, true, true, false, true, true },
                { true, false, true, true, false, true },
                { true, true, false, true, false, true }
        };
        String[][] mat3 = {
                { "# ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", ". " },
                { ". ", ". ", ". ", ". ", ". ", "[] " }
        };
        boolean[][] matcop3 = {
                { true, true, true, true, true, true },
                { true, false, true, false, true, false },
                { true, true, true, true, true, true },
                { true, false, true, false, true, true },
                { true, true, true, true, false, true },
                { true, false, false, false, false, true }
        };

        public static boolean[][] retornarMatriz1Boolean(boolean[][] matcop1){
            return matcop1;
        }
        public static boolean[][] retornarMatriz2Boolean(boolean[][] matcop2){
            return matcop2;
        }
        public static boolean[][] retornarMatriz3Boolean(boolean[][] matcop3){
            return matcop3;
        }
}
