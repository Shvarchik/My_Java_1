//Написать программу вычисления n-ого треугольного числа.
//1/2 * n * (n+1)

import java.util.Scanner;
public class task2 {
    public static void main(String[] args) {
        System.out.print("Введите число n: ");
        Scanner console = new Scanner(System.in);
        double n = console.nextDouble();
        console.close();
        double triangularNumber = (n/2)*(n+1);
        System.out.printf("Тn = %.0f ",triangularNumber);
    }
}
