﻿//Реализовать функцию возведения числа а в степень b. a, b ∈ Z. Сводя количество выполняемых действий к минимуму. 
//Пример 1: а = 3, b = 2, ответ: 9 
//Пример 2: а = 2, b = -2, ответ: 0.25
//Пример 3: а = 3, b = 0, ответ: 1
//Пример 4: а = 0, b = 0, ответ: не определено
//Пример 5
//входные данные находятся в файле input.txt в виде
//b 3
//a 10
//Результат нужно сохранить в файле output.txt
//1000

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class task1 {
    // public static  double pow (int value,  int powvalue) {
    //     return (double) Math.pow(value, powvalue);
    // }
    public static void main(String [] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String str;
        int a , b;
        a = b = 0;
      
        while ((str = br.readLine()) != null) {
            System.out.printf("%s\n", str);
            char c = str.charAt(0);
            if (c == 'a') {
                String substr1 = str.substring(2);
                System.out.println(substr1);
                a = Integer.parseInt(substr1);
            } 
            else {
                String substr1 = str.substring(2);
                System.out.println(substr1);
                b = Integer.parseInt(substr1);
            }
        }
           
        br.close();
        String res;
        if (a == 0 && b == 0) {
            res = "не определено";
        } else {
            double result = Math.pow(a, b);
            res = Double.toString(result); 
        }

        try (FileWriter fw = new FileWriter("output.txt", false)) {
            fw.write(res);
            fw.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
    }