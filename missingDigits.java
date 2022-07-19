// Задано уравнение вида q + w = e, где q, w, e >= 0.
// Некоторые цифры могут быть заменены знаком вопроса, например 2? + ?5 = 69. 
// Требуется восстановить выражение до верного равенства.
// Предложить хотя бы одно решение или сообщить, что его нет.

public class missingDigits {

    static String[] parseNums(String exp, String[] expArray) {  // метод возвращает массив строк [слагаемое1, слагаемое2, сумма]
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
        while (expArray[0].length() < expArray[1].length())        //приводим запись слагаемых и суммы к одинаковому числу символов
            expArray[0] = '0' + expArray[0];                       // в строке (добавляем нули слева) 
        while (expArray[1].length() < expArray[0].length())
            expArray[1] = '0' + expArray[1];
        if (expArray[2].length()-expArray[1].length() == 1){
            expArray[0] = '0' + expArray[0];
            expArray[1] = '0' + expArray[1];
        }
        return expArray;
    }
                                                                    // метод возвращает массив символов, стоящих на заданной позиции в     
    static char[] getPlacedDigit (String [] array, int poz){        // каждой из строк массива            
        char [] symbols = new char []{array[0].charAt(poz), array[1].charAt(poz), array[2].charAt(poz)};      
        return symbols;                                                                 
    }

    static boolean solve (String[] expArray, char t0, char t1, char sum, String tStr1, String tStr2, String sumStr, int flag, int place){
        final int RADIX = 10;
        if (t0 != Character.MIN_VALUE && t1 != Character.MIN_VALUE && sum != Character.MIN_VALUE){
            tStr1 = t0 + tStr1;
            tStr2 = t1 + tStr2;
            sumStr = sum + sumStr;
        }
        if (place == expArray[0].length() && flag == 0 && (Integer.parseInt(tStr1)+Integer.parseInt(tStr2)) == Integer.parseInt(sumStr)){
            System.out.println(tStr1 + " + " + tStr2 + " = " + sumStr);
            return true;
        }
        else {
            
            if (place < expArray[0].length()){
                char [] t = getPlacedDigit(expArray, expArray[0].length()-1-place);
                t0 = t[0];
                t1 = t[1];
                sum = t[2];
                
                if ((t0 == '?' ^ t1 == '?') && sum !='?'){          // если неизвестно одно из слагаемых
                    if (t0 == '?'){
                        int x = (int)sum - flag - (int)t1; 
                        if (x<0){
                            x = x+10;
                            flag = 1;
                        }
                        else flag = 0;
                        t0 = Character.forDigit(x, RADIX); 
                    } else {
                        int x = (int) sum - flag - (int)t0;
                        if (x<0){
                            x = x+10;
                            flag = 1;
                        }
                        else flag = 0;
                        t1 = Character.forDigit(x, RADIX); 
                    }
                    if (solve (expArray, t0, t1, sum, tStr1, tStr2, sumStr, flag, ++place))
                        return true;
                }
            }    
        }
        place--;  // удалить первые символы в tStr1,tStr2,sumStr
        return false;
    }

    public static void main(String[] args) {
        String exp = "?4 + 5? = 93";
        String[] expArray = new String[3];
        expArray = parseNums(exp, expArray);
        for (int i = 0; i < expArray.length; i++)
            System.out.println(expArray[i]);
        if (expArray[2].length()-expArray[1].length() > 1 || expArray[2].length()-expArray[1].length() < 0)
            System.out.println ("нет решения");
        else {
            String termStr1 = ""; 
            String termStr2 = "";
            String sumStr = "";
            int flag = 0;
            int placement = 0;
            Character t0 = Character.MIN_VALUE;
            Character t1 = Character.MIN_VALUE;
            Character sum = Character.MIN_VALUE;
            if (! solve(expArray, t0, t1, sum, termStr1, termStr2, sumStr, flag, placement)){
                System.out.println("решения нет");
            }
        }
    }
}
