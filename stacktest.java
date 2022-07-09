// Написать программу, определяющую правильность расстановки скобок в выражении.
// Пример 1: a+(d*3) - истина
// Пример 2: [a+(1*3) - ложь
// Пример 3: [6+(3*3)] - истина
// Пример 4: {a}[+]{(d*3)} - истина
// Пример 5: <{a}+{(d*3)}> - истина
// Пример 6: {a+]}{(d*3)} - ложь

import java.util.Stack;
public class stacktest {
       
    static boolean check (char[] arr){
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

    public static void main(String[] args) {
        var exp = "{a+]}{(d*3)}";
        char [] expArray = exp.toCharArray();
        
        if (check (expArray)){
            System.out.println("истина");
        } else 
            System.out.println("ложь");
    }
}
