import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MScanner {

    private List<String> reservedWords= Arrays.asList("int",
            "list","char","if","else","else if","start","var","scan","print","while");

    private List<String>operators= Arrays.asList("+","-",
            "*","/","%","=","==","!=","<",">","<=",">=");

    private List<String>separators=Arrays.asList("{","}","(",")",";","[","]"," ","<",">");
    private List<String>special_cases=Arrays.asList("=-","=+","==+","==-","!=-","!=+");

    private SymbolTable sym=new SymbolTable(17);

    private final List<Pair<String,Pair<Integer,Integer>>>pInternalForm=new ArrayList<>();

    private final FiniteAutomaton integer_const=new FiniteAutomaton("D:\\lftc lab\\lab4\\lab4\\integerconstantDFA.txt");
    private final FiniteAutomaton identifier=new FiniteAutomaton("D:\\lftc lab\\lab4\\lab4\\identifierDFA.txt");

    private int curly_brackets=0;
    private int angle_brackets=0;
    private int square_brackets=0;
    private int parenthesses=0;


    public boolean isIdentifier(String token)
    {
        return identifier.check_sequence(token);
    }

//    public boolean isIdentifier(String token) {
//        String pattern = "^[a-zA-Z]([a-z|A-Z|0-9])*$";
//        return token.matches(pattern);
//    }

    public boolean isConstant(String token) {
//        String numericPattern = "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*$";
        String charPattern = "^\'[a-zA-Z0-9_?!#*./%+=<>;)(}{ ]\'";

        return integer_const.check_sequence(token) ||
                token.matches(charPattern);

    }

    public boolean detect_Tokens(String line)
    {

        String []vars=line.split(" ");
        for(String value:vars)
        {
            int i=0;
            while(i<value.length())
            {
                StringBuilder sb=new StringBuilder();
                while(i<value.length() && !operators.contains(String.valueOf(value.charAt(i))) && !separators.contains(String.valueOf(value.charAt(i))))
                {
                    sb.append(value.charAt(i));
                    i++;
                }

                String v=sb.toString();
                if(v.length()>0 && reservedWords.contains(v))
                    pInternalForm.add(new Pair<>(v,new Pair<>(0,0)));
                else if(v.length()>0 && (isIdentifier(v)|| isConstant(v)))
                {
                    if(isIdentifier(v)) {
                        sym.add(v);
                        Pair<Integer,Integer> poz=sym.getPosition(v);
                        pInternalForm.add(new Pair<>("id",poz));

                    }
                    else {
                        sym.add(v);
                        Pair<Integer,Integer> poz=sym.getPosition(v);
                        pInternalForm.add(new Pair<>("const",poz));
                    }

                }
                else if(v.length()>0){
                    return false;
                }



                while(i<value.length() && value.charAt(i)==';')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    i++;
                }

                if(i<value.length() && value.charAt(i)=='(')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    parenthesses++;
                    i++;
                }

                else if(i<value.length() && value.charAt(i)==')')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    parenthesses--;
                    if(parenthesses<0)
                        return false;
                    i++;
                }

                else if(i<value.length() && value.charAt(i)=='{')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    curly_brackets++;
                    i++;
                }

                else if(i<value.length() && value.charAt(i)=='}')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    curly_brackets--;
                    if(curly_brackets<0)
                        return false;

                    i++;
                }

                else if(i<value.length() && value.charAt(i)=='[')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    square_brackets++;
                    i++;
                }

                else if(i<value.length() && value.charAt(i)==']')
                {
                    pInternalForm.add(new Pair<>(String.valueOf(value.charAt(i)),new Pair<>(0,0)));
                    square_brackets--;
                    if(square_brackets<0)
                        return false;
                    i++;
                }


                else if(i<value.length() && operators.contains(String.valueOf(value.charAt(i))))
                {
                    StringBuilder sb2=new StringBuilder();
                    while(i<value.length() && operators.contains(String.valueOf(value.charAt(i))))
                    {
                        sb2.append(value.charAt(i));
                        i++;
                    }

                    if(operators.contains(sb2.toString()) || special_cases.contains(sb2.toString()))
                    {
                        if(special_cases.contains(sb2.toString()))
                        {

                            String operator=sb2.substring(0,sb2.toString().length()-1);
                            pInternalForm.add(new Pair<>(operator,new Pair<>(0,0)));
                            sb2.replace(0,sb2.toString().length()-1,"");

                            while(i<value.length() && !operators.contains(String.valueOf(value.charAt(i))) && !separators.contains(String.valueOf(value.charAt(i))))
                            {
                                sb2.append(value.charAt(i));
                                i++;
                            }


                            if(isIdentifier(sb2.toString()) || isConstant(sb2.toString()))
                            {
                                if(isIdentifier(sb2.toString())) {
                                    sym.add(sb2.toString());
                                    Pair<Integer,Integer> poz=sym.getPosition(sb2.toString());
                                    pInternalForm.add(new Pair<>("id",poz));

                                }
                                else {
                                    sym.add(sb2.toString());
                                    Pair<Integer,Integer> poz=sym.getPosition(sb2.toString());
                                    pInternalForm.add(new Pair<>("const",poz));
                                }
                            }
                            else
                                return false;
                        }
                        else
                        {
                            pInternalForm.add(new Pair<>(sb2.toString(),new Pair<>(0,0)));
                        }
                    }

                    else
                        return false;


                }

                else if (i<value.length()) {

                    return false;
                }

            }


        }

        return true;

    }


    public void scan(String filename)
    {

        boolean ok=true;

        try {

            Scanner scanner= new Scanner(new File(filename));
            int line=0;
            while (scanner.hasNextLine() && ok) {
                String data = scanner.nextLine();
                line++;

                if(!detect_Tokens(data))
                {
                    System.out.println("lexical error line: " + line);
                    ok=false;
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if(ok)
        {
            System.out.println("lexically correct");
            try
            {
                FileWriter st=new FileWriter("D:\\lftc lab\\lab4\\lab4\\ST.out");
                FileWriter pif=new FileWriter("D:\\lftc lab\\lab4\\lab4\\PIF.out");

                StringBuilder result = new StringBuilder();
                for (var pair : pInternalForm) {
                    result.append(pair.getFirst()).append(" -> (").append(pair.getSecond().getFirst()).append(", ").append(pair.getSecond().getSecond()).append(")\n");
                }

                pif.write(result.toString());
                st.write(sym.toString());
                st.close();
                pif.close();

            }

            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }


        }

    }


}
