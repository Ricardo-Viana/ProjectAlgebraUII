// for(int i = 0, z = 0; i < matriz.length; i++){
//     for(int j = 0; j < matriz[i].length; j++){
//         System.out.println(matriz[i][j]);
//         
//     }
// } 
//Para iterar a matriz, sempre que quiser

import java.util.Scanner;

public class app {
    
    private static boolean matrizQuadrada;
    private static int pivoColuna;
    private static int pivoLinha;

    public app(){
        matrizQuadrada = false;
        pivoColuna= 0;
        pivoLinha = 0;
    }
    public static void setMatrizQuadrada(boolean valor){
        matrizQuadrada = valor;
    }

    public static boolean getMatrizQuadrada(){
        return matrizQuadrada;
    }

    public static void setPivoColuna(int coluna){
        pivoColuna = coluna;
    }

    public static int getPivoColuna(){
        return pivoColuna;
    }

    public static void setPivoLinha(int linha){
        pivoLinha = linha;
    }

    public static int getPivoLinha(){
        return pivoLinha;
    }
    public static void main(String[] args) throws Exception {
        int rowCounter = 0;
        int columnCounter = 0;
        Scanner scan = new Scanner(System.in);
        String[] ordemMatriz = new String[2];
        System.out.println("Qual a ordem da matriz que deseja escalonar?(Ex:1x1,2x2,3x3,4x4)");
        ordemMatriz = scan.nextLine().split("x");
        if(Integer.parseInt(ordemMatriz[0]) == Integer.parseInt(ordemMatriz[1])){
            setMatrizQuadrada(true);
        }
        double[][] matriz = new double[Integer.parseInt(ordemMatriz[0])][Integer.parseInt(ordemMatriz[1])]; 
        double[][] matrizIdentidade = new double[Integer.parseInt(ordemMatriz[0])][Integer.parseInt(ordemMatriz[1])];
        System.out.println(
                "Digite a matriz que deseja escalonar (Escreva por ordem, todos os elementos da primeira linha e depois da segunda, e assim em diante, com espaço entre eles)");
        String[] numerosMatriz = new String[Integer.parseInt(ordemMatriz[1]) * Integer.parseInt(ordemMatriz[0])];
        numerosMatriz = scan.nextLine().split(" ");
        for (int i = 0, z = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = Double.parseDouble(numerosMatriz[z]);
                z++;
                if(i == j && getMatrizQuadrada() == true){
                    matrizIdentidade[i][j] = 1;
                }
                else if(i !=j && getMatrizQuadrada() == true){
                    matrizIdentidade[i][j] = 0;
                }
            }
        }
        System.out.println( "\n Matriz de Entrada \n");
        printMatriz(matriz, Integer.parseInt(ordemMatriz[1]));
        boolean singular = true;
        if (getMatrizQuadrada() == true) {
            singular = determinanteSingular(matriz, Integer.parseInt(ordemMatriz[0]));
        }
        else{
            System.out.println("Matriz não é quadrada, logo não tem determinante \n");
        }
        if (matriz[0][0] == 0) {
            trocaLinhaDeZero(matriz, matrizIdentidade);
        }
        // Enquanto um contador não for igual a ordem da matriz fazer 
        // com que os elementos matriz[contador][contador] sejam iguais a 1 (Matriz identidade)
        int contador = 0;
        while(contador < matriz[rowCounter].length){
                for(int i = contador; i < matriz.length; i++){
                    for(int j = getPivoColuna(); j < matriz[0].length; j++){
                        if(matriz[i][j] != 0){
                            matriz = divisaoLinha(i, j, matriz, matrizIdentidade);
                            break;
                        }
                    }
                }
                for(int i = getPivoLinha()+1; i < matriz.length; i++){
                    matriz = multiplicaoESubtracaoLinha(i,getPivoColuna(),matriz, matrizIdentidade, 0);
                }
                if(getPivoColuna() < matriz[0].length && matriz[0].length > 1 ){
                    matriz = multiplicaoESubtracaoLinha(getPivoLinha(),getPivoColuna(),matriz, matrizIdentidade, 1);
                }
            if(getPivoLinha() < matriz.length - 1 && getPivoColuna() < matriz[0].length - 1){
                setPivoLinha(getPivoLinha() + 1);
                setPivoColuna(getPivoColuna() + 1);
            }
            if(getPivoColuna() < matriz[0].length && getPivoLinha() < matriz.length && matriz[getPivoLinha()][getPivoColuna()] == 0){
                trocaLinhaDeZero(matriz, matrizIdentidade);
            }
            contador += 1;
        }
        if(getMatrizQuadrada()== true && singular == false){
            System.out.println("\n Matriz Inversa \n");
            printMatriz(matrizIdentidade, Integer.parseInt(ordemMatriz[1]));
        }
        System.out.println("\n Matriz Escalonada \n");
        printMatriz(matriz, Integer.parseInt(ordemMatriz[1]));
        scan.close();
        return;
} 

    private static boolean determinanteSingular(double[][] matriz, int ordemMatriz) {
        switch (ordemMatriz) {
            case 1:
                System.out.println("\n Determinante = " + matriz[0][0] + "\n");
                if(matriz[0][0] != 0){
                    return false;
                }
                return true; 
            case 2:
                double calculo1 = determinanteOrdemDois(matriz, ordemMatriz);
                System.out.println("\n Determinante = "+ calculo1 + "\n");
                if (calculo1 == 0) {
                    return true;
                }
                return false;
            case 3:
                double calculo2 = determinanteOrdemTres(matriz, ordemMatriz);
                System.out.println("\n Determinante = "+ calculo2 + "\n");
                if (calculo2 == 0) {
                    return true;
                }
                return false;
            default:
                double calculo3 = determinanteOrdemQuatroOuMais(matriz, ordemMatriz);
                System.out.println("\n Determinante = "+ calculo3 + "\n");
                if(calculo3 == 0){
                    return true;
                }
                return false;
        }
    }

    private static double determinanteOrdemQuatroOuMais(double[][] matriz, int ordemMatriz) {
        double calculo = 0;
        if (ordemMatriz < 4){
            return determinanteOrdemTres(matriz, ordemMatriz);
        }
        else{
            int linha = 0;
            for(int i = 0; i < matriz.length; i++){
                calculo += matriz[linha][i] * Math.pow(-1, (i+1) + (linha + 1)) * determinanteOrdemQuatroOuMais(
                reparteMatriz(matriz, linha, i, ordemMatriz - 1), ordemMatriz - 1);
            }
            return calculo;
        }
    }

    private static double determinanteOrdemDois(double[][] matriz, int ordemMatriz) {
        double calculo = 0;
        calculo = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
        return calculo;
    }

    private static double determinanteOrdemTres(double[][] matriz, int ordemMatriz) {
        double calculo = 0;
        calculo = (matriz[0][0] * matriz[1][1] * matriz[2][2])+ 
               (matriz[0][1] * matriz[1][2] * matriz[2][0]) +
               (matriz[0][2] * matriz[1][0] * matriz[2][1])-
               (matriz[2][0] * matriz[1][1] * matriz[0][2])-
               (matriz[2][1] * matriz[1][2] * matriz[0][0])-
               (matriz[2][2] * matriz[1][0] * matriz[0][1]);
        return calculo;
    }

    private static void trocaLinhaDeZero(double[][] matriz, double[][] matrizIdentidade) throws Exception {
        int marcadorDeLinha = 0; // Valor não altera, só feito para marcar a quantidade de colunas matriz
        int verificador = 0;
        boolean trocado = false;
        for (int i = getPivoColuna(); i < matriz[marcadorDeLinha].length; i++) {    
            for (int j = getPivoLinha(); j < matriz.length;j++) {
                if(matriz[j][i]!= 0){
                    verificador++;
                    if(verificador == 1){
                        setPivoColuna(i);
                    }
                    for(int z = 0; z < matriz[marcadorDeLinha].length; z++){
                        for(int w = j; w < matriz.length; w++){
                            trocado = true;
                            double tmp = matriz[getPivoLinha()][z];
                            matriz[getPivoLinha()][z] = matriz[w][z];
                            matriz[w][z] = tmp;
                            if(getMatrizQuadrada() == true){
                                tmp = matrizIdentidade[getPivoLinha()][z];
                                matrizIdentidade[getPivoLinha()][z] = matrizIdentidade[w][z];
                                matrizIdentidade[w][z] = tmp;
                            }
                        }
                    }
                    break;
                }
            }
            if(trocado == true) break;
        }
    }

    private static double[][] divisaoLinha(int linha, int coluna, double[][] matriz, double[][] matrizIdentidade) {
        double valor = matriz[linha][coluna];
        for (int j = 0; j < matriz[linha].length; j++) {
            matriz[linha][j] = matriz[linha][j] / valor;
            if(getMatrizQuadrada() == true){
                matrizIdentidade[linha][j] = matrizIdentidade[linha][j] / valor;
            }
        }
        return matriz;
    }

    private static double[][] multiplicaoESubtracaoLinha(int linha,int verificadorUm,double[][] matriz, double[][] matrizIdentidade, int eliminar) {
            int counter = 0;
            if(verificadorUm <= matriz[counter].length-1 && linha <= matriz.length-1 && eliminar == 0){
                double valor = matriz[linha][verificadorUm];
                for(int j = 0; j < matriz[linha].length; j++) {
                    matriz[linha][j] = matriz[linha][j] -(valor*matriz[getPivoLinha()][j]);
                    if(getMatrizQuadrada() == true){
                    matrizIdentidade[linha][j] = matrizIdentidade[linha][j] -(valor*matrizIdentidade[verificadorUm][j]);
                    }
                }
            }
            // Pego os valores da coluna do verificadorUm que estão acima do linha do valor do verificadorUm.
            // valor tem que ser igual ao valor de cima
            while(counter < getPivoLinha()){
                counter += 1;
                double valor = matriz[getPivoLinha() - counter][getPivoColuna()];
                for(int j = 0; j < matriz[0].length; j++) {
                    matriz[getPivoLinha()-counter][j] = matriz[getPivoLinha()-counter][j] - (valor*matriz[getPivoLinha()][j]);
                    if(getMatrizQuadrada() == true){
                    matrizIdentidade[getPivoLinha()-counter][j] = matrizIdentidade[getPivoLinha()-counter][j] - (valor*matrizIdentidade[getPivoLinha()][j]);
                    }
                }
            }    
        return matriz;    
    }

    private static double[][] reparteMatriz(double[][] matriz, int linhaCorte, int colunaCorte,int ordemMatriz){
        double[][] novaMatriz = new double[ordemMatriz][ordemMatriz];
        int rowCounter = 0; // Contador de linha
        int columnCounter = 0; // Contador de coluna
        boolean entrou;
        for(int i = 0; i < matriz.length; i++){
            columnCounter = 0;
            entrou = false;
            for(int j = 0; j < matriz[i].length; j++){
                if(i != linhaCorte && j != colunaCorte){
                    novaMatriz[rowCounter][columnCounter] = matriz[i][j];
                    columnCounter++;
                    entrou = true;
                }
            }
            if(entrou){
                rowCounter++;
            }
        }
        return novaMatriz;
    }

    private static void printMatriz(double[][] matriz, int quebra){
        System.out.println("--------------------------------------");
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[i].length; j++){
                System.out.print(String.format("%.4f", matriz[i][j]) + " "); 
                if(quebra != 1 && j % (quebra - 1) == 0 && j > 0){
                    System.out.println("\n");
                }
                else if(matriz.length == 1 || quebra == 1){
                    System.out.println("\n");
                }
            }
        }
        System.out.println("--------------------------------------");
    }
}