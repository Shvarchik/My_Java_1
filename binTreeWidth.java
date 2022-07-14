//На вход некоторому исполнителю подаётся два числа (a, b). У исполнителя есть две команды
//- команда к1: увеличить в с раз, а умножается на c
//- команда к2: увеличить на d, к a прибавляется d
//написать программу, которая выдаёт набор команд, позволяющий число a превратить в число b или сообщить, что это невозможно
//Пример 1: а = 1, b = 7, c = 2, d = 1
//ответ: к1, к2, к1, к2 или к2, к2, к1, к2. 
//Пример 2: а = 11, b = 7, c = 2, d = 1
//ответ: нет решения. 
//*Подумать над тем, как сделать минимальное количество команд

import java.util.*;

public class binTreeWidth {
    static int findElement(int a, int b, int c, int d) {
        Deque < int [] > deque = new ArrayDeque<>();
        int [] element = new int [2];
        deque.addFirst(new int [] {0,a});
        int parent = a;
        int i = 0;
        int parentIndex = 0;
        int childIndex = 0;
        int child = 0;
        while (deque.size() != 0 && child != b) {
            if (i %2 == 0) {                                    // если индекс предыдущего элемента четный
                element = deque.getFirst();                     // меняем родителя на первый элемент в очереди
                parentIndex = element[0];  
                parent = element [1];
                deque.removeFirst();                            // удаляем из очереди первый элемент
                child = parent*c;                               // вычисляем значение левого потомка
                childIndex = parentIndex*2 + 1;                 // вычисляем индекс левого потомка           
            } else {
                child = parent+d;                               // иначе вычисляем значение и индекс правого потомка
                childIndex = parentIndex*2 + 2;               
            }
            if (child <= b) {                                   // если значение потомка меньше или равно искомому b,
                deque.addLast(new int [] {childIndex,child});   // добавляем массив из его индекса и значения последним в очередь
            }
            i++;                                                // номер последнего вычисленного потомка 
        }                                                       // (не важно, добавленного в очередь или нет) 
        if (deque.isEmpty())                        
            return 0;                                           // если очередь пуста, возвращаем 0
        else
            return childIndex;                                  // иначе возвращаем индекс потомка, равного искомому b 
    }

    static ArrayList<String> restorePath (int index){           // восстановление пути до искомого индекса
        ArrayList <String> path = new ArrayList<>();
        while (index != 0){                                 
            if (index %2 != 0){                                 // если индекс нечетный, то это левый потомок
                index = (index-1)/2;                            // вычисляем индекс родителя
                path.add("k1");                              // записываем команду, ведущую к левому потомку 
            } else {                                            // иначе 
                index = (index-2)/2;                            // те же действия для правого потомка
                path.add("k2");
            }
        }
        path.trimToSize();
        return path;
    }

    static void printPath (ArrayList<String> arList){           // печать ответа
        StringBuilder sb = new StringBuilder();                 // переданный список выводим в обратном порядке
        for (int i = arList.size()-1; i >=0; i--) {
            sb.append(arList.get(i));
            sb.append(", ");
        }
        sb.trimToSize();
        sb.deleteCharAt(sb.capacity()-2);
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 7;
        int c = 2;
        int d = 1;
        int index = findElement(a, b, c, d);
        if (index == 0){
            System.out.println("нет решения");
        } else {
            printPath(restorePath(index));
        }
    }
}