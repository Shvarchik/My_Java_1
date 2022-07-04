import java.util.Stack;

// Написать программу, определяющую правильность расстановки скобок в выражении.
// Пример 1: a+(d*3) - истина
// Пример 2: [a+(1*3) - ложь
// Пример 3: [6+(3*3)] - истина
// Пример 4: {a}[+]{(d*3)} - истина
// Пример 5: <{a}+{(d*3)}> - истина
// Пример 6: {a+]}{(d*3)} - ложь

import java.util.Stack;
public class stacktest {
       
    static boolean check (Stack<Character> st, char[] bee){
        for (int i = 0; i<bee.length; i++){
            switch (bee[i]){
                case '(' :
                    st.addElement(bee[i]);
                    break;
                case '[' :
                    st.addElement(bee[i]);
                    break;
                case '{' :
                    st.addElement(bee[i]);
                    break;
                case '<' :
                    st.addElement(bee[i]);
                    break;
                case ')' :
                    if (st.pop() != '(') return false ;
                    break;
                case ']' :
                    if (st.pop() != '[') return false ;
                    break;
                case '}' :
                    if (st.pop() != '{') return false ;
                    break;
                case '>' :
                    if (st.pop() != '<') return false ;
                    break;
            }
        }
        if (st.empty()) return true;
            else return false;
    }

    public static void main(String[] args) {
        var exp = "a+(d*3)";
        char [] bee = exp.toCharArray();
        Stack<Character> st = new Stack<>();
        if (check (st,bee)){
            System.out.println("истина");
        } else 
            System.out.println("ложь");
    }
}
