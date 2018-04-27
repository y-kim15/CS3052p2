package turingmachine;

// https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value
public class Tuple {

    private final String state;
    private final String alphabet;

    public Tuple(String x, String y) {
        this.state = x;
        this.alphabet = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple key = (Tuple) o;
        return state.equals(key.state) && alphabet.equals(key.alphabet);
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + alphabet.hashCode();
        return result;
    }

    public String getState(){
        return state;
    }

    public String getAlphabet() {
        return alphabet;
    }
}
