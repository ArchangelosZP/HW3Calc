import javax.script.ScriptException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static String originalSentence;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ScriptException {
        Main m = new Main();
        m.originalSentence = "ZюукяДZіботZ,ежобZощZяZенZ!ьлаксом";
        System.out.println(m.reverseWords(m.reverseCh(m.originalSentence)));
        m.calc();
    }

    public String reverseCh(String args) {
        int lengthString;
        lengthString = args.length();
        String nextWord = "";
        String newSentence = "";
        for (int i = lengthString; i > 0; i--) {
            if (args.charAt(i - 1) == 'Z') {
                newSentence = newSentence + nextWord + " ";
                nextWord = "";
            } else {
                nextWord = nextWord + args.charAt(i - 1);
            }
        }
        return newSentence;
    }

    public String reverseWords(String args) {
        String newSentenceFinish = "";
        String nextWord = "";
        int lengthString = args.length();
        for (int i = 0; i < lengthString; i++) {
            if (args.charAt(i) == ' ') {
                newSentenceFinish = nextWord + " " + newSentenceFinish;
                nextWord = "";
            } else {
                nextWord = nextWord + args.charAt(i);
            }
        }
        return newSentenceFinish;
    }

    // Again, I wrote the code incorrectly - I wrote it as I would do if it were a real task. Now all the same, he began to plan a relocation abroad. Not enough time to do quality homework.
    public void calc() throws ScriptException {
        System.out.println("Введите выражение которое надо вычислить или же введите exit для выхода");
        String text = scanner.nextLine();
        switch (text) {
            case "exit":
                return;
        }
        Expressions object = new Expressions();
        try {
            System.out.println(object.make(text));
        } catch (IndexOutOfBoundsException e) {
            calc();
        } catch (NoSuchElementException e) {
            calc();
        } catch (NumberFormatException e) {
            calc();
        }
        calc();
    }

    public class Expressions {


        public boolean isOperation(char c) {
            return c == '+' || c == '-' || c == '/' || c == '%' || c == '*';
        }

        public boolean interval(char c) {
            return c == ' ';
        }

        public int opearatorsPriority(char operand) {
            switch (operand) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                case '%':
                    return 2;
                default:
                    return -1;
            }
        }

        public void operator(LinkedList<Integer> cislo, char znak) {
            int r = cislo.removeLast();
            int l = cislo.removeLast();
            switch (znak) {
                case '+':
                    cislo.add(l + r);
                    break;
                case '-':
                    cislo.add(l - r);
                    break;
                case '*':
                    cislo.add(l * r);
                    break;
                case '/':
                    cislo.add(l / r);
                    break;
                case '%':
                    cislo.add(l % r);
                    break;
            }
        }


        public int make(String s) {
            Expressions obj = new Expressions();
            LinkedList<Integer> h = new LinkedList<Integer>();
            LinkedList<Character> op = new LinkedList<Character>();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (obj.interval(c))
                    continue;

                if (c == '(') {
                    op.add('(');
                } else if (c == ')') {
                    while (op.getLast() != '(')
                        operator(h, op.removeLast());
                    op.removeLast();
                } else if (obj.isOperation(c))
                {
                    while (!op.isEmpty() && obj.opearatorsPriority(op.getLast()) >= obj.opearatorsPriority(c))
                        operator(h, op.removeLast());
                    op.add(c);
                } else {
                    String operand = "";
                    while (i < s.length() && Character.isDigit(s.charAt(i)))
                        operand += s.charAt(i++);
                    --i;
                    h.add(Integer.parseInt(operand));
                }
            }

            while (!op.isEmpty())
                operator(h, op.removeLast());
            return h.get(0);

        }

    }

}
