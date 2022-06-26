import java.io.*;
import java.util.Scanner;
import java.io.FileReader;


public class sortMergeArray {

    static int [] sortArray (int [] array) {

        if (array == null) return null;
        if (array.length < 2) return array;                                 // возврат из рекурсивного вызова

        int [] arrayLeft = new int [array.length/2];                       // создаем массив arrayL из левой половины исходного
        System.arraycopy(array, 0, arrayLeft, 0, array.length/2); 

        int [] arrayRight = new int [array.length - array.length/2];        // создание массива arrayRight из правой половины исходного
        System.arraycopy(array, array.length/2, arrayRight, 0, array.length-array.length/2);

        arrayLeft = sortArray (arrayLeft);                                  // рекурсивный вызов для левой половины
        arrayRight = sortArray(arrayRight);                                 // рекурсивный вызов для правой половины

        return mergeArray (arrayLeft, arrayRight);                          // вызов метода слияния отсортированных массивов
    }

    static int[] mergeArray(int[] B, int[] C) {                            // слияние двух отсортированных массивов B и C
        int[] A = new int[B.length + C.length];                            // в массив А 
        int i = 0, j = 0, k = 0;
        while (i < B.length && j < C.length) {
            A[k++] = B[i] < C[j] ? B[i++] : C[j++];
        }
        if (i < B.length) {
            System.arraycopy(B, i, A, k, B.length - i);                     // если остались элементы в массиве B, копируем их в А
        } else {                                                              // иначе
            if (j < C.length) {                                               // если остались элементы в массиве C, копируем их в А
                System.arraycopy(C, j, A, k, C.length - j);                 
            }
        }  
        return A;
    }

    static int [] arrReader (File f) throws Exception {   // чтение массива из файла. Первое число в файле - размер массива

        FileReader fr = new FileReader(f);
        Scanner scan = new Scanner(fr);
        int[] array = new int[scan.nextInt()];
        for (int i = 0; i < array.length; i++){
        array[i] = scan.nextInt();
        }
        fr.close();
        scan.close();
        return array;
    }

    static void printArray (int [] array) {             // вывод массива на печать
        
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {

        int [] array = new int [100];
        File f = new File("array.txt");
        array = arrReader (f);                      // чтение исходного массива из файла
        printArray(array);
        array = sortArray(array);
        printArray(array);
                
    }
}

