import java.io.File;
import java.util.*;

public class FiniteAutomaton {

    public Set<String> alphabet,states,finalStates;
    public String initialState;

    public Map<Pair<String,String>,Set<String>>transitions;

    public FiniteAutomaton(String filename)
    {
        this.states=new HashSet<>();
        this.alphabet=new HashSet<>();
        this.finalStates=new HashSet<>();
        this.transitions=new HashMap<>();
        readFiniteAutomation(filename);
    }

    private void readFiniteAutomation(String filename)
    {
        try
        {
            File file=new File(filename);
            Scanner reader=new Scanner(file);

            String statesLine=reader.nextLine();
            states=new HashSet<>(Arrays.asList(statesLine.split(" ")));

            String alphabetLine=reader.nextLine();
            alphabet=new HashSet<>(Arrays.asList(alphabetLine.split(" ")));

            initialState=reader.nextLine();

            String finalStatesLine=reader.nextLine();
            finalStates=new HashSet<>(Arrays.asList(finalStatesLine.split(" ")));

            while(reader.hasNextLine())
            {
                String transitionLine=reader.nextLine();
                String [] transitionElems=transitionLine.split(" ");

                if(states.contains(transitionElems[0]) && states.contains(transitionElems[2]) && alphabet.contains(transitionElems[1]))
                {
                    Pair<String,String>transitionStates=new Pair<>(transitionElems[0],transitionElems[1]);

                    if(!transitions.containsKey(transitionStates))
                    {
                        Set<String>transitionStatesSet=new HashSet<>();
                        transitionStatesSet.add(transitionElems[2]);
                        transitions.put(transitionStates,transitionStatesSet);

                    }
                    else
                    {
                        transitions.get(transitionStates).add(transitionElems[2]);
                    }

                }
            }



        }

        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public String get_states()
    {
        StringBuilder st= new StringBuilder("States: ");
        for(String state:states)
            st.append(state).append(" ");

        return st.toString();

    }

    public String get_alphabet()
    {
        StringBuilder st= new StringBuilder("Alphabet: ");
        for(String alph:alphabet)
            st.append(alph).append(" ");

        return st.toString();

    }

    public String get_initialState()
    {
        StringBuilder st=new StringBuilder("InitialState: ").append(initialState);
        return st.toString();
    }

    public String get_finalStates()
    {
        StringBuilder st= new StringBuilder("finalStates: ");
        for(String state:finalStates)
            st.append(state).append(" ");

        return st.toString();

    }

    public String get_transitions()
    {
        StringBuilder st= new StringBuilder("Transitions: \n");


        for(var trans:transitions.keySet())
        {


            for(var t2:transitions.get(trans))
            {
                st.append("(").append(trans.getFirst()).append(",").append(trans.getSecond()).append(")").append("=").append(t2).append("\n");
            }

        }

        return st.toString();

    }

}
