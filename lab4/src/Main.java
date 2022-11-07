public class Main {

    public static void main(String [] args)
    {
        FiniteAutomaton fn=new FiniteAutomaton("D:\\lftc\\lab4\\src\\FA.txt");

        String states=fn.get_states();
        String alphabet=fn.get_alphabet();
        String final_states=fn.get_finalStates();
        String initial_state=fn.get_initialState();
        String transitions=fn.get_transitions();

        System.out.println(states+"\n"+alphabet+"\n"+final_states+"\n"+initial_state+"\n"+transitions);

    }

}
