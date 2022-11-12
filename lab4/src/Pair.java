import java.util.Objects;

public class Pair<T,K> {

    private T first;
    private K second;

    Pair(T first,K second)
    {
        this.first=first;
        this.second=second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public K getSecond() {
        return second;
    }

    public void setSecond(K second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Pair<?,?>))
            return false;

        Pair<?,?> k=(Pair<?,?>) o;

        return first.equals(k.first) && second.equals(k.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
