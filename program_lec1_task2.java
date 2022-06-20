// Даны два файла, в каждом из которых находится запись многочлена.
// Сформировать файл содержащий сумму многочленов

import java.io.FileReader;
import java.util.Scanner;
import java.io.*;

public class program_lec1_task2 {

    static String readfile (FileReader fr) throws Exception {     // чтение строки полинома из файла
        
        Scanner scan = new Scanner(fr);
        StringBuilder polinomBuilder = new StringBuilder();
        while (scan.hasNextLine()) {
            polinomBuilder.append(scan.nextLine());
        }
        fr.close();
        scan.close ();
        String polinom = polinomBuilder.toString();
        return polinom;
    }

    static void writeFile (String str) throws Exception {
        FileWriter nFile = new FileWriter("polinomres.txt", false);
        nFile.write(str);
        nFile.close();
    }

    static int[] parseDigit (char [] array, int index){  // сборка числа из идущих подряд цифр

        int [] num = new int [2];
        int j = index;
        StringBuilder sb = new StringBuilder("");
        while (j < array.length && Character.isDigit(array[j])) {
            sb.append(array[j]);
            j++;
        }
        num[0] = j;                   // возвращаемый индекс следующего элемента массива
        String str = sb.toString();
        num[1] = Integer.valueOf(str); // возвращаемое число
        sb.setLength(0);
        return num;
    }    

    static String member (int coef, int degree) {    //формирование элемента многочлена в зависимости от коэффициента и степени
        if (coef == 0) return "";
        if (degree == 0) return String.format("%d",coef);
        if (degree == 1){
            if (coef == 1) return "x";
            else return String.format("%d*x", coef);
        }
        if (degree > 1 && coef == 1) return String.format("x^%d", degree);
        else return String.format("%d*x^%d", coef, degree);
    }

    public static void main(String args[]) throws Exception {
        FileReader fr1 = new FileReader("polinom1.txt");
        FileReader fr2 = new FileReader("polinom2.txt");
        String [] polinoms = new String [2];
        polinoms [0] = readfile (fr1);
        polinoms [1] = readfile (fr2);
        int [] coefficients = new int [10];               // создаем массив коэффициентов
        for (String polinom : polinoms){                  // в цикле сначала один полином, потом другой
            System.out.println(polinom);
            //polinom = polinom.replaceAll(" ","");    // убираем пробелы
            char [] polinomArr = polinom.toCharArray();                 // превращаем строку полинома в массив символов
            //int [] coefficients = new int [10];                         
            int i = 0;
            
            while (i < polinomArr.length && polinomArr[i] != '=') {    // парсим в цикле член полинома
                int coef = 1;
                int degree = 0;
                while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++; // пропускаем пробелы и т.п.
                if (i < polinomArr.length && (polinomArr[i]=='-' || polinomArr[i]=='+')){        // если '-'' меняем знак коэфф-та
                    if (polinomArr[i]=='-') coef = -1;
                    i++;
                    while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++;   //пропускаем пробелы и т.п.
                }    
                if (i<polinomArr.length && Character.isDigit(polinomArr[i])){            // если число, вызываем метод его сборки parseDigit
                    int [] digitCoef = parseDigit(polinomArr,i);                         // получаем коэффициент
                    coef *= digitCoef [1]; 
                    i = digitCoef [0];
                    while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++;
                }
                if (i<polinomArr.length && polinomArr[i] == '*'){                         // если знак умножения, 
                    i++;
                    while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++; 
                    if (i<polinomArr.length && polinomArr[i] == 'x'){                     // если после умножения x, пропускаем 
                        i++;                                                        
                        while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++; 
                    } else System.out.println ("сорри, у вас после * отсутствует x");   // если x нет, ошибка
                    if (i<polinomArr.length && polinomArr[i] == '^'){                      // если знак степени, пропускаем  
                        i++;
                        while  (i<polinomArr.length && (polinomArr[i]==' ' || polinomArr[i]=='\t')) i++;
                        if (i<polinomArr.length && Character.isDigit(polinomArr[i])){      // и проверяем есть ли далее число 
                            int [] digitDegree = parseDigit(polinomArr,i);                 // считываем показатель степени 
                            degree = digitDegree [1];                                      
                            i = digitDegree [0];                                                
                            if (degree > coefficients.length - 1) System.out.println ("сорри, cтепень превышает допустимую");
                        } else System.out.println ("сорри, отсутствует показатель степени"); 
                    } else degree = 1;
                }
                if (degree < coefficients.length - 1) coefficients[degree] += coef;
            }
            for (int n = 0; n < coefficients.length; n++){
                System.out.print(coefficients [n] + " "); 
            } 
            System.out.println(); 
        }
        StringBuilder polinomBuilder = new StringBuilder();  // формирование строки многочлена с использованием StringBuilder
        for (int i = coefficients.length-1; i >= 0 ; i--){
            String newMember = member (coefficients[i], i);
            if (newMember != ""){
                if (newMember.charAt(0) != '-' ){
                    polinomBuilder.append("+").append(newMember);
                } else  polinomBuilder.append(newMember);   
            }
        }
        polinomBuilder.append(" = 0");                // добавляем "= 0"
        if (polinomBuilder.charAt(0) == '+') polinomBuilder.deleteCharAt(0);  // убрать "+" если он есть в начале
        String polinomResult = polinomBuilder.toString();    
        System.out.println(polinomResult); 
        writeFile(polinomResult);
    }  
}
 

 