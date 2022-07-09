// Реализовать алгоритм перевода из инфиксной записи в постфиксную для арифметического выражения.
// Вычислить запись если это возможно
// *Написать программу вычисляющую значение сложного арифметического выражения. Для простоты - выражение всегда вычисляемое
// Пример: (2^3 * (10 / (5 - 3)))^(Sin(Pi)) ответ - 1

import java.util.Stack;
import java.util.*;
import java.lang.Math;

public class infixToPostfix {

    static boolean check(char[] arr) {                          // метод проверки согласования скобок
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case '(':
                    st.addElement(arr[i]);
                    break;
                case '[':
                    st.addElement(arr[i]);
                    break;
                case '{':
                    st.addElement(arr[i]);
                    break;
                case '<':
                    st.addElement(arr[i]);
                    break;
                case ')':
                    if (st.empty() || st.pop() != '(')
                        return false;
                    break;
                case ']':
                    if (st.empty() || st.pop() != '[')
                        return false;
                    break;
                case '}':
                    if (st.empty() || st.pop() != '{')
                        return false;
                    break;
                case '>':
                    if (st.empty() || st.pop() != '<')
                        return false;
                    break;
            }
        }
        if (st.empty())
            return true;
        else
            return false;
    }

    static int[] parseNumber(char[] array, int index) {          // парсинг числа из идущих подряд цифр в массиве символов,
                                                                 // возвращает массив, где 0-й элемент - индекс последней цифры,
        StringBuilder sb = new StringBuilder();                  // 1-й элемент - само число
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

    static String[] parseWord(char[] array, int index) {        // парсинг слова из идущих подряд букв в массиве символов,
                                                                // возвращает массив, где 0-й элемент - индекс последней буквы
        StringBuilder sb = new StringBuilder();                 // 1-й элемент - само слово
        String[] word = new String[2];
        while (index < array.length && Character.isLetter(array[index])) {
            sb.append(array[index]);
            index++;
        }
        word[0] = Integer.toString(index - 1);
        word[1] = sb.toString();
        sb.setLength(0);
        return word;
    }

    static ArrayList<String> toPostfix(char[] arr) {            // перевод из инфиксной записи в постфиксную

        Map<String, Integer> operators = new HashMap<String, Integer>();
        operators.put("+", 0);
        operators.put("-", 0);
        operators.put("*", 1);
        operators.put("/", 1);
        operators.put("^", 2);
        operators.put("~", 3);                      // знак для унарного '-'

        List<String> functions = List.of("Sin", "Cos", "Tg");
        List<String> constants = List.of("Pi");
        ArrayList<String> result = new ArrayList<String>();
        Stack<String> st = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '(')                                  // Если символ является открывающей скобкой, помещаем его в стек.
                st.addElement(Character.toString(arr[i]));

            if (arr[i] == '!')                                  // Если символ ! (факториал), добавляем его к выходной строке.
                result.add(Character.toString(arr[i]));

            if (Character.isDigit(arr[i])) {                    // Если символ является числом, парсим его и добавляем к вых.строке
                int[] num = parseNumber(arr, i);
                result.add(Integer.toString(num[1]));
                i = num[0];
            }
            if (Character.isLetter(arr[i])) {                       // Если символ - буква, парсим слово до конца
                String[] word = parseWord(arr, i);
                if (functions.contains(word[1]))
                    st.addElement(word[1]);                         // если слово - префиксная функция, добавляем ее в стек
                if (constants.contains(word[1]))
                    result.add(word[1]);                            // если слово - название константы - добавляем к вых.строке
                i = Integer.parseInt(word[0]);
            }
            if (arr[i] == ')') {                                    // Если символ является закрывающей скобкой:
                String s = "(";
                while (!(st.empty()) && !(s.equals(st.peek()))) {   // Пока верхний элемент не открывающая скобка,
                    result.add(st.pop());                           // выталкиваем элементы из стека в выходную строку
                }
                st.pop();                                           // открывающая скобка удаляется из стека
            }

            if (operators.containsKey(Character.toString(arr[i]))) {        // Если символ является операцией O, тогда:
                if (arr[i] == '-' && (i == 0 || arr[i - 1] == '(')) {       // если это унарный минус, то помещаем в стек ~
                    st.addElement("~");
                } else {                                                    // если это бинарная операция O, то
                    while (!(st.empty()) &&
                            (functions.contains(st.peek()) ||               // пока на вершине стека префиксная функция ИЛИ
                                    (operators.containsKey(st.peek()) &&    // (операция И ее приоритет >= приоритету O
                                    operators.get(st.peek()) >= operators.get(Character.toString(arr[i]))))) { 
                        result.add(st.pop());                               // выталкиваем верхний элемент стека в выходную строку;
                    }
                    st.addElement(Character.toString(arr[i]));              // помещаем операцию O в стек.
                }
            }
        }
        while (!(st.empty())) {                                             // выталкиваем оставшиеся в стеке элементы в выходную строку
            result.add(st.pop());
        }
        result.trimToSize();
        return result;
    }

    static double getFactorial(double f) {                                  // вычисление факториала числа
        double result = 1;
        for (int i = 1; i <= f; i++) {
            result = result * i;
        }
        return result;
    }

    static boolean isNumeric(String str) {                                  // метод, проверяющий, является ли строка числом  
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static void printExp (ArrayList<String> arList){                        // печать постфиксной записи
        StringBuilder sb = new StringBuilder();
        for (String s : arList) {
            sb.append(s);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    static double calculatePostFix (ArrayList<String> postFix) {            // вычисление постфиксной записи

        List<String> unarOperations = List.of("Sin", "Cos", "Tg", "~", "!");
        List<String> constants = List.of("Pi");

        String[] postFixArray = new String[postFix.size()];
        postFix.toArray(postFixArray);                                      // постфиксная запись в массив
        Deque <Double> deque = new ArrayDeque<>();                          // стек
        for (int i = 0; i < postFixArray.length; i++) {
            String sym = postFixArray[i];
            if (constants.contains(sym)) {                                  // константа вычисляется и помещается в стек
                if (sym.equals("Pi"))
                    deque.push(Math.PI);
            }
            if (isNumeric(sym)) {                                           // число в стек
                deque.push(Double.parseDouble(sym));
            } else {                                                
                if (unarOperations.contains(sym)) {                         // унарный оператор применяется к последнему
                    if (deque.peek() != null) {                             // элементу в стеке, результат обратно в стек
                        double operand = deque.pop();
                        if (sym.equals("Sin"))
                            deque.push(Math.sin(operand));
                        if (sym.equals("Cos"))
                            deque.push(Math.cos(operand));
                        if (sym.equals("Tg"))
                            deque.push(Math.tan(operand));
                        if (sym.equals("~"))
                            deque.push(-1 * operand);
                        if (sym.equals("!"))
                            deque.push(getFactorial(operand));
                    } else
                        System.out.println("нет операнда для унарного оператора, проверьте запись");
                }
                if (sym.equals("+") && deque.size() >=2) {          // бинарные операции выполняются на двух последних 
                        double res = deque.pop() + deque.pop();               // элементах стека. Результат обратно в стек  
                        deque.push(res);
                } 
                if (sym.equals("*") && deque.size() >=2) {
                        double res = deque.pop() * deque.pop();
                        deque.push(res);
                }
                if (sym.equals("^") && deque.size() >=2) {
                        double secondOp = deque.pop();
                        double res = Math.pow (deque.pop(),secondOp);
                        deque.push(res);
                } 
                if (sym.equals("-") && deque.size() >=2) {
                        double secondOp = deque.pop();
                        double res = deque.pop() - secondOp;
                        deque.push(res);
                } 
                if (sym.equals("/") && deque.size() >=2) {
                        double secondOp = deque.pop();
                        double res = deque.pop() / secondOp;
                        deque.push(res);
                } 
            }
        }
        return deque.pop();                                             // последний элемент стека - результат
    }

    public static void main(String[] args) {
        String exp = "(2^3 * (10 / (5 - 3)))^(Sin(Pi))";                //     "(-2^3 * (10 / (5 - 3)))";                                
        exp = exp.replaceAll("\\s+", "");
        System.out.println("Исходное выражение: " + exp);
        char[] expArray = exp.toCharArray();
        if (check(expArray)) {
            ArrayList<String> postFix = toPostfix(expArray);
            System.out.print("Постфиксная запись: ");
            printExp (postFix);
            System.out.printf("Ответ = %.2f", calculatePostFix (postFix) );
        } else
            System.out.println("Несогласованы скобки");
    }
}