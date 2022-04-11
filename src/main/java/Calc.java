import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Calc {

    boolean delim(char c) {
        return c == ' ';
    }

    boolean is_op(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    int priority(char op) {
        return
                op == '+' || op == '-' ? 1 :
                        op == '*' || op == '/' || op == '%' ? 2 :
                                -1;
    }

    void process_op(Vector<Integer> st, char op) {
        int r = st.lastElement();
        st.remove(st.size() - 1);
        int l = st.lastElement();
        st.remove(st.size() - 1);
        switch (op) {
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                st.add(l / r);
                break;
            case '%':
                st.add(l % r);
                break;
        }
    }

    public int calc(String s) {
        Vector<Integer> st = new Vector<Integer>();
        Vector<Character> op = new Vector<Character>();
        for (int i = 0; i < s.length(); ++i)
            if (!delim(s.charAt(i)))
                if (s.charAt(i) == '(')
                    op.add('(');
                else if (s.charAt(i) == ')') {
                    while (op.lastElement() != '(') {
                        process_op(st, op.lastElement());
                        op.remove(op.size() - 1);
                    }
                    op.remove(st.size() - 1);
                } else if (is_op(s.charAt(i))) {
                    char curop = s.charAt(i);
                    while (!op.isEmpty() && priority(op.lastElement()) >= priority(s.charAt(i))) {
                        process_op(st, op.lastElement()); op.remove(st.size() - 1);
                    }
                    op.add(curop);
                } else {
                    String operand = "";
                    while (i < s.length() && (Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i))))
                        operand += s.charAt(i++);
                    --i;
                    if (Character.isDigit(operand.charAt(0)))
                        st.add(Integer.valueOf(operand));
                    else
                        st.add(getVariable(operand,s));
                }
        while (!op.isEmpty()) {
            process_op(st, op.lastElement());
            op.remove(st.size() - 1);
        }
        return st.lastElement();

    }

    private Integer getVariable(String operand,String s) {
        System.out.println("Введите значение "+ operand + " :");

        Scanner scanner = new Scanner((System.in));
        String newVal=scanner.nextLine();
        s.replace(operand,newVal);
        return Integer.valueOf(newVal);
    }
}

