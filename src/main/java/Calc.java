import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Calc {

    private Scanner scanner;
    public Calc()
    {
        scanner= new Scanner(System.in);
    }
    public Calc(InputStream inputStream)
    {
        this.scanner=new Scanner(inputStream);
    }


    boolean delim(char c) {
        return c == ' ';
    }

    boolean is_op(char c) { //true, если знак операции
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    int priority(char op) { //приоритет знаков операции
        return
                op == '+' || op == '-' ? 1 :
                        op == '*' || op == '/' || op == '%' ? 2 :
                                -1;
    }

    void process_op(Vector<Integer> st, char op) {  //выполнение оперделенной операции
        int r = st.lastElement();
        st.remove(st.size() - 1);
        int l = st.lastElement();
        st.remove(st.size() - 1);
        switch (op) {
            case '+' -> st.add(l + r);
            case '-' -> st.add(l - r);
            case '*' -> st.add(l * r);
            case '/' -> st.add(l / r);
            case '%' -> st.add(l % r);
        }
    }

    public int calc(String s) {
        Vector<Integer> st = new Vector<>(); //вектор операндов
        Vector<Character> op = new Vector<>(); //вектор операций
        for (int i = 0; i < s.length(); ++i)
            if (!delim(s.charAt(i)))
                if (s.charAt(i) == '(') //открывающая скобка просто добавляется
                    op.add('(');
                else if (s.charAt(i) == ')') { //закрывающая, проталкивает все операции до открывающей
                    while (op.lastElement() != '(') {
                        process_op(st, op.lastElement());
                        op.remove(op.size() - 1);
                    }
                    op.remove(st.size() - 1); //открывающая удаляется
                } else if (is_op(s.charAt(i))) {//если операция
                    char curop = s.charAt(i);
                    while (!op.isEmpty() && priority(op.lastElement()) >= priority(s.charAt(i))) { //опеpация выталкивает из стека все опеpации с большим или pавным пpиоpитетом
                        process_op(st, op.lastElement()); op.remove(st.size() - 1);
                    }
                    op.add(curop);
                } else {
                    StringBuilder operand = new StringBuilder();
                    while (i < s.length() && (Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i)))) //если встретили операнд
                        operand.append(s.charAt(i++));
                    --i;
                    if (Character.isDigit(operand.charAt(0)))
                        st.add(Integer.valueOf(operand.toString()));
                    else {
                        var tmp=getVariable(operand.toString(), s); //вводим операнд
                        i+=tmp.length()-operand.length();
                        s=s.replace(operand.toString(),tmp);
                        st.add(Integer.valueOf(tmp));
                    }
                }
        while (!op.isEmpty()) { //выполняем все операции из вектора операций
            process_op(st, op.lastElement());
            op.remove(st.size() - 1);
        }
        return st.lastElement(); //последнее значение, является решением

    }

    private String getVariable(String operand,String s) {
        System.out.println("Введите значение "+ operand + " :");
        return scanner.nextLine();
    }
}

