// Реализовать алгоритм перевода из инфиксной записи в постфиксную для арифметического выражения.
// Вычислить запись если это возможно
// *Написать программу вычисляющую значение сложного арифметического выражения. Для простоты - выражение всегда вычисляемое
// Пример: (2^3 * (10 / (5 - 3)))^(Sin(Pi)) ответ - 1

import java.util.Stack;
import java.util.*;

public class infixToPostfix {

    static boolean check (char[] arr){                        // метод проверки согласования скобок      
        Stack<Character> st = new Stack<>();
        for (int i = 0; i<arr.length; i++){
            switch (arr[i]){
                case '(' :
                    st.addElement(arr[i]);
                    break;
                case '[' :
                    st.addElement(arr[i]);
                    break;
                case '{' :
                    st.addElement(arr[i]);
                    break;
                case '<' :
                    st.addElement(arr[i]);
                    break;
                case ')' :
                    if (st.empty() || st.pop() != '(') return false ;
                    break;
                case ']' :
                    if (st.empty() || st.pop() != '[') return false ;
                    break;
                case '}' :
                    if (st.empty() || st.pop() != '{') return false ;
                    break;
                case '>' :
                    if (st.empty() || st.pop() != '<') return false ;
                    break;
            }
        }
        if (st.empty()) return true;
            else return false;
    }

    static int[] parseNumber(char[] array, int index) {                     // парсинг числа из идущих подряд цифр в массиве символов,
                                                                            // возвращает массив, где 0-й элемент - индекс последней цифры, 
        StringBuilder sb = new StringBuilder();                             // 1-й элемент - само число
        int[] num = new int[2];
        while (index < array.length && Character.isDigit(array[index])) {
            sb.append(array[index]);
            index++;
        }
        num[0] = index - 1;
        num[1] = Integer.parseInt(sb.toString());
        sb.setLength(0);
        return num;
    }

    static String [] parseWord(char[] array, int index) {                   // парсинг слова из идущих подряд букв в массиве символов,
                                                                            // возвращает массив, где 0-й элемент - индекс последней буквы
        StringBuilder sb = new StringBuilder();                             // 1-й элемент - само слово
        String [] word = new String [2];
        while (index < array.length && Character.isLetter(array[index])) {
            sb.append(array[index]);
            index++;
        }
        word[0] = Integer.toString(index - 1);
        word[1] = sb.toString();
        sb.setLength(0);
        return word;
    }

    static ArrayList <String> toPostfix (char[] arr) {

        Map <String, Integer> operators = new HashMap <String, Integer>();
        operators.put("+", 0);
        operators.put("-", 0);
        operators.put("*", 1);
        operators.put("/", 1);
        operators.put("^", 2);
        List<String> functions = List.of("Sin", "Cos", "Tg");
        List<String> constants = List.of("Pi");
        ArrayList <String> result = new ArrayList <String>();
        Stack <String> st = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(')                                              // Если символ является открывающей скобкой, помещаем его в стек.
                st.addElement(Character.toString(arr[i]));

            if (arr[i] == '!')                                              // Если символ ! (факториал), добавляем его к выходной строке.
                result.add(Character.toString(arr[i]));

            if (Character.isDigit(arr[i])) {                                // Если символ является числом, парсим его и добавляем к вых.строке
                int[] num = parseNumber(arr, i);
                result.add(Integer.toString(num[1]));
                i = num[0];
            }
            if (Character.isLetter(arr[i])) {                               // Если символ - буква, парсим слово до конца    
                String [] word = parseWord(arr, i);
                if (functions.contains(word[1])) st.addElement(word[1]);    // если слово - префиксная функция, добавляем ее в стек    
                if (constants.contains(word[1])) result.add(word[1]);       // если слово - название константы - добавляем к вых.строке
                i = Integer.parseInt(word[0]);
            }
            if (arr[i] == ')') {                                            // Если символ является закрывающей скобкой:
                String s = "(";                                      
                while (!(st.empty()) && ! (s.equals(st.peek()))) {          // Пока верхний элемент не открывающая скобка,
                    result.add(st.pop());                                   // выталкиваем элементы из стека в выходную строку
                }
                st.pop();                                                   //  открывающая скобка удаляется из стека  
            }
                                                                              
            if (operators.containsKey(Character.toString(arr[i]))) {        // Если символ является бинарной операцией O, тогда:  пока на вершине стека префиксная функция ИЛИ
                while ( !(st.empty()) &&                                                     
                        (functions.contains(st.peek()) ||                   // пока на вершине стека префиксная функция ИЛИ
                        (operators.containsKey(st.peek()) &&                // операция приоритетнее или такого же уровня приоритета как O
                         operators.get(st.peek()) >= operators.get(Character.toString(arr[i]))))){
                    result.add(st.pop());                                   // выталкиваем верхний элемент стека в выходную строку;
                }
                st.addElement(Character.toString(arr[i]));   // помещаем операцию O в стек.
            }
        }
        while (!(st.empty())){                               // выталкиваем оставшиеся в стеке элементы в выходную строку   
            result.add(st.pop());
        }
        result.trimToSize();
        return result;    
    }

    public static void main(String[] args) {
        String exp = "(2^3 * (10 / (5 - 3)))^(Sin(Pi))";          
        exp = exp.replaceAll("\\s+", "");
        char[] expArray = exp.toCharArray();
        if (check (expArray)){
            ArrayList <String> postFix = toPostfix (expArray);
            String result = postFix.toString ();
            System.out.println(result);
        } else 
            System.out.println("Несогласованы скобки");
    }
}