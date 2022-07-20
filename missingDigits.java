// Задано уравнение вида q + w = e, где q, w, e >= 0.
// Некоторые цифры могут быть заменены знаком вопроса, например 2? + ?5 = 69. 
// Требуется восстановить выражение до верного равенства.
// Предложить хотя бы одно решение или сообщить, что его нет.

public class missingDigits {

    static String[] parseNums(String exp, String[] expArray) { // метод возвращает массив строк [слагаемое1, слагаемое2,
                                                               // сумма]
        exp = exp.replaceAll("\\s+", "");
        int i = 0;
        String term = "";
        for (int count = 0; count <= 2; count++) {
            while (i < exp.length() && (exp.charAt(i) == '?' || Character.isDigit(exp.charAt(i)))) {
                term = term + exp.charAt(i);
                i++;
            }
            expArray[count] = term;
            term = "";
            if (i < exp.length() && exp.charAt(i) != '+' && count == 0)
                System.out.println("некорректная запись, отсутствует + после первого слагаемого");
            if (i < exp.length() && exp.charAt(i) != '=' && count == 1)
                System.out.println("некорректная запись, отсутствует = после второго слагаемого");
            if (i < exp.length() && exp.charAt(i) != '=' && count == 2)
                System.out.println("некорректная запись, проверьте сумму");
            i++;
        }
        while (expArray[0].length() < expArray[1].length())     // приводим запись слагаемых и суммы к одинаковому числу
                                                                // символов
            expArray[0] = '0' + expArray[0];                    // при необходимости добавляем нули слева
        while (expArray[1].length() < expArray[0].length())
            expArray[1] = '0' + expArray[1];
        if (expArray[2].length() - expArray[1].length() == 1) {
            expArray[0] = '0' + expArray[0];
            expArray[1] = '0' + expArray[1];
        }
        return expArray;
    }

                                                                        // метод возвращает массив символов, стоящих на заданной позиции в
    static char[] getPlacedDigit(String[] array, int poz) {             // каждой из строк массива строк
        char[] symbols = new char[] { array[0].charAt(poz), array[1].charAt(poz), array[2].charAt(poz) };
        return symbols;
    }

    static boolean solve(String[] expArray, char t0, char t1, char sum, String tStr1, String tStr2, String sumStr,
            int flag, int place) {
        final int RADIX = 10;
        if (t0 != Character.MIN_VALUE && t1 != Character.MIN_VALUE && sum != Character.MIN_VALUE) {
            tStr1 = t0 + tStr1;                                             // cтрока сборки первого слагаемого
            tStr2 = t1 + tStr2;                                             // cтрока сборки второго слагаемого
            sumStr = sum + sumStr;                                          // cтрока сборки суммы
            place++;
        }
        if (place == expArray[0].length() && flag == 0
                && (Integer.parseInt(tStr1) + Integer.parseInt(tStr2)) == Integer.parseInt(sumStr)) {
            System.out.println(tStr1 + " + " + tStr2 + " = " + sumStr);
            return true;
        } else {

            if (place < expArray[0].length() && place >= 0) {
                char[] t = getPlacedDigit(expArray, expArray[0].length() - 1 - place);
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                int f= flag;
                if ((t0 == '?' ^ t1 == '?') && sum != '?') {            // если неизвестно одно из слагаемых
                    if (t0 == '?') {
                        int x = Character.getNumericValue(sum) - flag - Character.getNumericValue(t1);
                        if (x < 0) {
                            x = x + 10;
                            flag = 1;
                        } else
                            flag = 0;
                        t0 = Character.forDigit(x, RADIX);
                    } else {
                        int x = Character.getNumericValue(sum) - flag - Character.getNumericValue(t0);
                        if (x < 0) {
                            x = x + 10;
                            flag = 1;
                        } else
                            flag = 0;
                        t1 = Character.forDigit(x, RADIX);
                    }
                    if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                        return true;
                }
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag=f;

                if (t0 == '?' && t1 == '?' && sum != '?') {             // если неизвестны оба слагаемых
                    for (int y = 0; y < (int) sum; y++) {               // вариант без переноса в след. разряд
                        t0 = Character.forDigit(y, RADIX);
                        t1 = Character.forDigit(Character.getNumericValue(sum) - flag - y, RADIX);
                        flag = 0;
                        if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                            return true;
                    }        
                    for (int y = 0; y <= 9; y++) {                      // вариант с переносом в следующий разряд
                        t1 = Character.forDigit(Character.getNumericValue(sum) - flag + 10 - y, RADIX);
                        t0 = Character.forDigit(y, RADIX);
                        flag = 1;
                        if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                            return true;
                    }
                }
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag = f;
                if (t0 == '?' && t1 != '?' && sum == '?') {             // если неизвестно первое слагаемое и сумма
                    int summ;
                    for (int x = 0; x <= 9; x++) {                       // перебором ищем его 
                        t0 = Character.forDigit(x, RADIX);               // и считаем сумму
                        summ = Character.getNumericValue(t1) + x + flag;
                        if (summ < 10) {                                // если сумма меньше 10, флаг переноса 0 
                            sum = Character.forDigit(summ, RADIX);
                            flag = 0;
                        } else {                                        // если сумма >= 10, берем последнюю цифру, флаг 1
                            summ = summ % 10;
                            flag = 1;
                            sum = Character.forDigit(summ, RADIX);
                        }
                        if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                            return true;
                    }
                }
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag = f;
                if (t0 != '?' && t1 == '?' && sum == '?') {             // если неизвестно второе слагаемое и сумма
                    int summ;
                    for (int x = 0; x <= 9; x++) {                       // перебором ищем его 
                        t1 = Character.forDigit(x, RADIX);               // и считаем сумму
                        summ = Character.getNumericValue(t1) + x + flag;
                        if (summ < 10) {                                // если сумма меньше 10, флаг переноса 0 
                            sum = Character.forDigit(summ, RADIX);
                            flag = 0;
                        } else {                                        // если сумма >= 10, берем последнюю цифру, флаг 1
                            summ = summ % 10;
                            flag = 1;
                            sum = Character.forDigit(summ, RADIX);
                        }
                        if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                            return true;
                    }
                }
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag = f;
                if (t0 == '?'&& t1 == '?' && sum == '?'){               // если неизвестны оба слагаемых и сумма
                    for (int x = 0; x<=9; x++){                         // перебором подбираем пару слагаемых
                        for (int y = 0; y<=9; y++){
                            t0 = Character.forDigit(x, RADIX);
                            t1 = Character.forDigit(y, RADIX);
                            int summ = x + y + flag;                    // для них считаем сумму
                            if (summ<10){
                                sum = Character.forDigit(summ, RADIX);
                                flag = 0;
                            } else {
                                sum = Character.forDigit(summ%10, RADIX);
                                flag=1;
                            }
                            if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                                return true;
                        } 
                    }
                }
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag = f;
                if (t0 != '?' && t1 != '?' && sum =='?'){           // если известны оба слагаемых, а сумма неизвестна
                    int summ = Character.getNumericValue(t0) + Character.getNumericValue(t1) + flag;
                    if (summ < 10) {                                // если сумма меньше 10, флаг переноса 0 
                        sum = Character.forDigit(summ, RADIX);
                        flag = 0;
                    } else {                                        // если сумма >= 10, берем последнюю цифру, флаг 1
                        summ = summ % 10;
                        flag = 1;
                        sum = Character.forDigit(summ, RADIX);
                    }
                    if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                        return true;    
                }                

                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                flag = f;
                if (t0 != '?' && t1 != '?' && sum !='?'){               // если известны оба слагаемых и сумма
                    if (Character.getNumericValue(t0) + Character.getNumericValue(t1) + flag == Character.getNumericValue(sum)){
                        flag = 0;  
                        if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                                return true;  
                    } else {
                        if (Character.getNumericValue(t0) + Character.getNumericValue(t1) + flag == Character.getNumericValue(sum) + 10){
                            flag = 1;
                            if (solve(expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, place))
                                return true;
                        }  
                    }      
                }
            }
        }
        place--;
        return false;
    }
    public static void main(String[] args) {
        String exp = "2?6+??2=35?";                // "2?4+9?=35?";   "9? + ?5 = 69";    "2??+14?=35?";   "2?4+9?=35?";  "?4 + 5? = 93";
        String[] expArray = new String[3];
        expArray = parseNums(exp, expArray);
        for (int i = 0; i < expArray.length; i++)
            System.out.println(expArray[i]);
        if (expArray[2].length() - expArray[1].length() > 1 || expArray[2].length() - expArray[1].length() < 0)
            System.out.println("нет решения");
        else {
            String termStr1 = "";
            String termStr2 = "";
            String sumStr = "";
            int flag = 0;
            int placement = 0;
            Character t0 = Character.MIN_VALUE;
            Character t1 = Character.MIN_VALUE;
            Character sum = Character.MIN_VALUE;
            if (!solve(expArray, t0, t1, sum, termStr1, termStr2, sumStr, flag, placement)) {
                System.out.println("решения нет");
            }
        }
    }
}
