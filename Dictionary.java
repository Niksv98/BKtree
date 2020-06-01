import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary {
    private ArrayList<String> words;
    Dictionary(String text) {
        this.words = new ArrayList<>(Arrays.asList(text.split("\n")));
    }
    ArrayList<String> getWords() {
        return words;
    }
}
