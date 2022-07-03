// задача о нахождении маршрута шахматного коня, проходящего через все поля доски по одному разу

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;

public class knightMove {

    // метод поиска возможных ходов из точки (x,y)

    static ArrayList<ArrayList<Integer>> getPossibleMoves (int[][] chess, int n, int x, int y) {   
        int[] d1 = new int[] { 2, -2 };
        int[] d2 = new int[] { 1, -1 };
        ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<>();
                
        for (int i : d1) {
            for (int j : d2) {
                if (x + i >= 0 && x + i < n && y + j >= 0 && y + j < n && chess[x + i][y + j] == 0) {
                    ArrayList<Integer> coordinates = new ArrayList<>(2);
                    Collections.addAll(coordinates, x+i,y+j);
                    possibleMoves.add(coordinates);
                }
                if (x + j >= 0 && x + j < n && y + i >= 0 && y + i < n && chess[x + j][y + i] == 0) {
                    ArrayList<Integer> coordinates = new ArrayList<>(2);
                    Collections.addAll(coordinates, x+j,y+i);
                    possibleMoves.add(coordinates);
                }
            }
        }
        possibleMoves.trimToSize();
        return possibleMoves;
    }

    // метод создает массив возможных ходов, где каждой паре координат сопоставлено количество возможных продолжений хода
    
    static int [][] choiceNextStep (int [][] chess, int n, int x,  int y) {  
                                                                                      //для полученной пары координат x,y
        ArrayList<ArrayList<Integer>> nextMoves = getPossibleMoves (chess, n, x, y);  // вызываем метод поиска возможных ходов
        int [][] nextMovesArray = new int [nextMoves.size()][3];    
        int count = 0;
        for (ArrayList<Integer> coordinate : nextMoves){           // для каждой полученной пары координат из nextMoves
            int i = coordinate.get(0);
            int j = coordinate.get(1);
            ArrayList<ArrayList<Integer>> nextPossibleMoves = new ArrayList<>();
            nextPossibleMoves = getPossibleMoves (chess, n, i, j);   // вызываем метод поиска возможных ходов
            nextMovesArray [count][0] = nextPossibleMoves.size();    //  их количество записываем в
            nextMovesArray [count][1] = i;                           // массив вместе с соответствующей парой координат
            nextMovesArray [count][2] = j;
            count++;
        }                                                                 // сортируем полученный массив по возрастанию
        Arrays.sort(nextMovesArray, Comparator.comparingInt(o -> o[0]));  // количества возможных ходов
        return nextMovesArray;
    }

    // печать доски с результатом

    static void printBoard (int[][]chess){                                  

        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[0].length; j++) {
                System.out.printf ( "%4d", chess[i][j]);
            }
            System.out.println();
        }
    }

    // метод, реализующий поставленную задачу с оптимизацией по правилу Варнсдорфа

    static boolean setKnight (int [][] chess, int n, int x, int y, int count){

        count++;                                                    // ставим следующий ход
        chess[x][y] = count;
        if (count == n*n) return true;

        int[][] nextPossibleStep = choiceNextStep(chess, n, x, y);  // вызываем метод получения упорядоченного массива
                                                                    // возможных ходов с количеством вариантов продолжения хода
        for (int [] step : nextPossibleStep){
            if (step[0] != 0){                       // выбираем точку с минимальным ненулевым количеством дальнейших ходов
                if (setKnight (chess,n,step[1],step[2], count)) return true;
            }
            if (count == n*n-1) {                    // из предпоследней точки берем что есть  
                if (setKnight (chess,n,step[1],step[2], count)) return true;
            }
        }
        count--;                                     // если ходов с возможностью продолжать нет
        chess [x][y] = 0;                            // откатываем на шаг назад  
        return false;
    }

    public static void main(String[] args) {
        int n = 8;
        int[] chessBoard[] = new int[n][n];
        int x = 0;
        int y = 0;
        int count = 0;
        if (setKnight (chessBoard, n, x, y, count)){
            printBoard(chessBoard);
        }
        else System.out.println ("решений нет");
    }
}





