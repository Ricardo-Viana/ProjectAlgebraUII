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
        int counter = 0;
        Scanner scan = new Scanner(System.in);
        int ordemMatriz = 0;
        while (ordemMatriz < 2) {
            System.out.println("Qual a ordem da matriz que deseja escalonar?(2,3,4)");
            ordemMatriz = Integer.parseInt(scan.nextLine());
        }
        double[][] matriz = new double[ordemMatriz][ordemMatriz];
        System.out.println(
                "Digite a matriz que deseja escalonar (Escreva por ordem, todos os elementos da primeira linha e depois da segunda, e assim em diante, com espaço entre eles)");
        String[] numerosMatriz = new String[ordemMatriz * ordemMatriz];
        numerosMatriz = scan.nextLine().split(" ");
        // double[][] matrizIdentidade = new double
        for (int i = 0, z = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = Double.parseDouble(numerosMatriz[z]);
                z++;
            }
        }
        if (determinanteSingular(matriz, ordemMatriz)) {
            System.out.println("A matriz é singular, logo não possui inversa");
        }
        if (matriz[0][0] == 0) {
            for (int i = 1; i < matriz.length; i++) {
                if (matriz[i][0] != 0) {
                    matriz = trocaLinhaDeZero(i, 0, matriz);
                    break;
                }
            }
        }
        matriz = divisaoLinha(0, 0, matriz); // Tornar o primeiro elemento da matriz == 1;
        while(counter < matriz[0].length){
            for(int i = 0; i < matriz.length; i++){

            }
        }
    }

    private static boolean determinanteSingular(double[][] matriz, int ordemMatriz) {
        switch (ordemMatriz) {
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
            // default:
            // if(determinanteOrdemQuatroOuMais(matriz, ordemMatriz) == 0){
            // return true;
            // }
            // return false;
        }
        return false;
    }

    // private static double determinanteOrdemQuatroOuMais(double[][] matriz, int
    // ordemMatriz) {
    // return false;
    // }

    private static double determinanteOrdemDois(double[][] matriz, int ordemMatriz) {
        double calculo = 0;
        calculo = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
        return calculo;
    }

    private static double determinanteOrdemTres(double[][] matriz, int ordemMatriz) {
        double calculo = 0;
        calculo = (matriz[0][0] * matriz[1][1] * matriz[2][2]) +
                (matriz[0][1] * matriz[1][2] * matriz[2][0]) +
                (matriz[0][2] * matriz[1][0] * matriz[2][1]) -
                (matriz[2][0] * matriz[1][1] * matriz[0][2]) -
                (matriz[2][1] * matriz[1][2] * matriz[0][0]) -
                (matriz[2][2] * matriz[1][0] * matriz[0][1]);

        return calculo;
    }

    private static double[][] trocaLinhaDeZero(int linha, int coluna, double[][] matriz) throws Exception {
        boolean trocou = false;
        for (int i = linha; i < matriz.length; i++) {
            if (matriz[i][0] != 0) {
                for (int j = coluna; j < matriz[i].length; j++) {
                    double tmp = matriz[0][j];
                    matriz[0][j] = matriz[i][j];
                    matriz[i][j] = tmp;
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

    private static double[][] divisaoLinha(int linha, int coluna, double[][] matriz) {
        double valor = matriz[linha][coluna];
        for (int j = 0; j < matriz[linha].length; j++) {
            matriz[linha][j] = matriz[linha][j] / valor;
        }
        return matriz;
    }
}