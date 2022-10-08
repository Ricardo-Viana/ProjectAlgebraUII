// for(int i = 0, z = 0; i < matriz.length; i++){
//     for(int j = 0; j < matriz[i].length; j++){
//         System.out.println(matriz[i][j]);
//         
//     }
// } 
//Para iterar a matriz, sempre que quiser

import java.util.Scanner;

public class app {
    public static void main(String[] args) throws Exception {
        int rowCounter = 0;
        int columnCounter = 0;
        Scanner scan = new Scanner(System.in);
        int ordemMatriz = 0;
        while (ordemMatriz < 1) {
            System.out.println("Qual a ordem da matriz que deseja escalonar?(Ex:1,2,3,4)");
            ordemMatriz = Integer.parseInt(scan.nextLine());
        }
        double[][] matriz = new double[ordemMatriz][ordemMatriz]; double[][] matrizIdentidade = new double[ordemMatriz][ordemMatriz];
        System.out.println(
                "Digite a matriz que deseja escalonar (Escreva por ordem, todos os elementos da primeira linha e depois da segunda, e assim em diante, com espaço entre eles)");
        String[] numerosMatriz = new String[ordemMatriz * ordemMatriz];
        numerosMatriz = scan.nextLine().split(" ");
        for (int i = 0, z = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = Double.parseDouble(numerosMatriz[z]);
                z++;
                if(i == j){
                    matrizIdentidade[i][j] = 1;
                }
                else{
                    matrizIdentidade[i][j] = 0;
                }
            }
        }
        if (determinanteSingular(matriz, ordemMatriz)) {
            System.out.println("A matriz é singular, logo não possui inversa");
            return;
        }
        if (matriz[0][0] == 0) {
            for (int i = 1; i < matriz.length; i++) {
                if (matriz[i][0] != 0) {
                    matriz = trocaLinhaDeZero(i, 0, matriz, matrizIdentidade);
                    break;
                }
            }
        }
        // Enquanto um contador não for igual a ordem da matriz fazer 
        // com que os elementos matriz[contador][contador] sejam iguais a 1 (Matriz identidade)
        while(columnCounter < matriz[rowCounter].length){
                for(int i = columnCounter; i < matriz.length; i++){
                    if(matriz[i][columnCounter] != 0){
                        matriz = divisaoLinha(i, columnCounter, matriz, matrizIdentidade);
                    }
                }
                for(int i = columnCounter+1; i < matriz.length; i++){
                    matriz = multiplicaoESubtracaoLinha(i,columnCounter,matriz, matrizIdentidade);
                }
                if(columnCounter == matriz.length - 1){
                    matriz = multiplicaoESubtracaoLinha(columnCounter,columnCounter,matriz, matrizIdentidade);
                }
            columnCounter += 1;
        }
        printMatriz(matrizIdentidade, matrizIdentidade.length);
        System.out.println("\n");
        printMatriz(matriz, matriz.length);
        scan.close();
        return;
} 

    private static boolean determinanteSingular(double[][] matriz, int ordemMatriz) {
        switch (ordemMatriz) {
            case 1:
                if(matriz[0][0] != 0){
                    return false;
                }
                return true; 
            case 2:
                if (determinanteOrdemDois(matriz, ordemMatriz) == 0) {
                    return true;
                }
                return false;
            case 3:
                if (determinanteOrdemTres(matriz, ordemMatriz) == 0) {
                    return true;
                }
                return false;
            default:
                if(determinanteOrdemQuatroOuMais(matriz, ordemMatriz) == 0){
                    return true;
                }
                return false;
        }
    }

    private static double determinanteOrdemQuatroOuMais(double[][] matriz, int
    ordemMatriz) {
        double calculo = 0;
        if (ordemMatriz < 4){
            return determinanteOrdemTres(matriz, ordemMatriz);
        }
        else{
            int row = 0;
            for(int i = 0; i < matriz.length; i++){
                calculo += matriz[row][i] * Math.pow(-1, (i+1) + (row + 1)) * determinanteOrdemQuatroOuMais(
                reparteMatriz(matriz, row, i, ordemMatriz - 1), ordemMatriz - 1);
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

    private static double[][] trocaLinhaDeZero(int linha, int coluna, double[][] matriz, double[][] matrizIdentidade) throws Exception {
        boolean trocou = false;
        for (int i = linha; i < matriz.length; i++) {
            if (matriz[i][0] != 0) {
                for (int j = coluna; j < matriz[i].length; j++) {
                    double tmp = matriz[0][j];
                    matriz[0][j] = matriz[i][j];
                    matriz[i][j] = tmp;
                    tmp = matrizIdentidade[0][j];
                    matrizIdentidade[0][j] = matrizIdentidade[i][j];
                    matrizIdentidade[i][j] = tmp;
                }
                trocou = true;
                break;
            }
        }
        if (!trocou) {
            throw new Exception("Matriz impossível de ser realizada");
        }
        return matriz;
    }

    private static double[][] divisaoLinha(int linha, int coluna, double[][] matriz, double[][] matrizIdentidade) {
        double valor = matriz[linha][coluna];
        for (int j = 0; j < matriz[linha].length; j++) {
            matriz[linha][j] = matriz[linha][j] / valor;
            matrizIdentidade[linha][j] = matrizIdentidade[linha][j] / valor;
        }
        return matriz;
    }

    private static double[][] multiplicaoESubtracaoLinha(int linha,int verificadorUm,double[][] matriz, double[][] matrizIdentidade) {
            double valor = matriz[linha][verificadorUm];
            int counter = 0;
            if(verificadorUm != matriz.length-1){
                for(int j = 0; j < matriz[linha].length; j++) {
                    matriz[linha][j] = matriz[linha][j] -(valor*matriz[verificadorUm][j]);
                    matrizIdentidade[linha][j] = matrizIdentidade[linha][j] -(valor*matrizIdentidade[verificadorUm][j]);
                }
            }
            // Pego os valores da coluna do verificadorUm que estão acima do linha do valor do verificadorUm.
            // valor tem que ser igual ao valor de cima
            while(counter < verificadorUm){
                counter += 1;
                valor = matriz[verificadorUm - counter][verificadorUm];
                for(int j = 0; j < matriz[linha].length; j++) {
                    matriz[verificadorUm-counter][j] = matriz[verificadorUm-counter][j] - (valor*matriz[verificadorUm][j]);
                    matrizIdentidade[verificadorUm-counter][j] = matrizIdentidade[verificadorUm-counter][j] - (valor*matrizIdentidade[verificadorUm][j]);
                }
            }    
        return matriz;    
    }

    private static double[][] reparteMatriz(double[][] matriz, int linhaCorte, int colunaCorte,int ordemMatriz){
        double[][] novaMatriz = new double[ordemMatriz][ordemMatriz];
        int rowCounter = 0;
        int columnCounter = 0;
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
                if(matriz.length != 1 && j % (quebra - 1) == 0 && j > 0){
                    System.out.println("\n");
                }
                else if(matriz.length == 1){
                    System.out.println("\n");
                }
            }
        }
        System.out.println("--------------------------------------");
    }
}