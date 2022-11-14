import java.util.Scanner;

public class Main {

    private static void printMenu(){
        System.out.println("1. Print states.");
        System.out.println("2. Print alphabet.");
        System.out.println("3. Print final states.");
        System.out.println("4. Print transitions.");
        System.out.println("5. Check if sequence is accepted by DFA.");
        System.out.println("0. Exit");
    }

    public static void main(String [] args)
    {
//        FiniteAutomaton fn=new FiniteAutomaton("D:\\lftc lab\\lab4\\lab4\\FA.txt");
//
//        boolean ok=true;
//        Scanner s=new Scanner(System.in);
//
//        while(ok) {
//            printMenu();
//
//            int opt;
//            opt=s.nextInt();
//
//            switch (opt)
//            {
//                case 1 -> System.out.println(fn.get_states());
//                case 2 -> System.out.println(fn.get_alphabet());
//                case 3 -> System.out.println(fn.get_finalStates());
//                case 4 -> System.out.println(fn.get_transitions());
//                case 5 -> {
//                    Scanner s2=new Scanner(System.in);
//                    String seq=s2.nextLine();
//                    if(fn.check_sequence(seq))
//                        System.out.println("Sequence is accepted by DFA");
//                    else
//                        System.out.println("Sequence is not accepted by DFA");
//
//                }
//
//                default -> ok=false;
//            }
//
//        }

        MScanner sn=new MScanner();
        sn.scan("D:\\lftc lab\\lab4\\lab4\\p1.txt");
    }

}
