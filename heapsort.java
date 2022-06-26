import java.io.*;
import java.util.Scanner;
import java.io.FileReader;

public class heapsort {

    static void sort (int [] array){

        int n = array.length;
        for (int i = n/2-1; i>=0; i--){         // начиная с последнего, имеющего листья элемента, идем к корню и
            heapsift (array, n,i);              // вызываем метод, перегруппирующий поддерево с корнем c индексом i
        } 
        for (int i=n-1; i >= 0; i--){            // перемещаем корневой (максимальный) элемент в конец массива
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapsift (array, i, 0);               // вызываем перегруппировку оставшейс части массива с корневого элемента 
        }
    }

    static void heapsift(int [] array, int size, int root){     // метод перегруппировки поддерева (максимальный элемент вверх)
        int leftChild = 2*root +1;
        int rightChild = 2*root + 2;
        int max = root;                                             // присваиваем max индекс корня
        if (leftChild < size && array [leftChild] > array [max])    // если левый элемент больше корня, 
            max = leftChild;                                        // присваиваем max индекс левого
        if (rightChild < size && array [rightChild] > array [max])  // если правый элемент больше максимального
            max = rightChild;                                       // присваиваем max индекс правого
        if (max != root){                                           // если максимальный элемент не корень
            int temp = array[max];                                  // меняем его с корневым элементом местами
            array[max] = array[root];
            array[root] = temp;
            heapsift(array, size, max);                             // вызов метод перегруппировки затронутого поддерева
        }
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
        sort(array);
        printArray(array);
    }
}