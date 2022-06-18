// Задана натуральная степень maxDegree. 
// Сформировать случайным образом список коэффициентов (значения от 0 до 100)
// многочлена степени maxDegree. 
// Пример: maxDegree=2 => 2*x² + 4*x + 5 = 0 или x² + 5 = 0 или 10*x² = 0

import java.util.Scanner;
import java.util.Random;

public class program_lec1_task1 {

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
    public static void main(String[] args) {
        System.out.print("Введите число maxDegree: ");
        Scanner console = new Scanner(System.in);
        int maxDegree = console.nextInt();
        console.close();

        int[] arrOfCoefficients = new int[maxDegree+1];   // заполнение массива коэффициентов случайными числами
        Random rand = new Random();
        for (int i = 0; i < arrOfCoefficients.length; i++) {
            arrOfCoefficients[i] = rand.nextInt(100); 
        }
        for (int i = 0; i < arrOfCoefficients.length; i++) {   // печать массива коэффициентов
            System.out.printf("%d ", arrOfCoefficients[i]);
        }
        System.out.println();

        StringBuilder polinomBuilder = new StringBuilder();  // формирование строки многочлена с использованием StringBuilder
        for (int i = 0; i < maxDegree + 1; i++){
            String newMember = member (arrOfCoefficients[i],maxDegree-i);
            if (newMember != ""){
                if (i != maxDegree) polinomBuilder.append(newMember).append(" + ");
                else polinomBuilder.append(newMember).append(" = 0");
            }
        }
        String polinom = polinomBuilder.toString();    
        System.out.println(polinom);
    }
}
