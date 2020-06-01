import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class SpellChecker {
    ArrayList<String> suggestions = new ArrayList<>();
    Dictionary dictionary;
    BKTree tree;

    public SpellChecker(String text){
        dictionary = new Dictionary(text);
        tree = BKTree.build(new ArrayList<>(dictionary.getWords()));
    }

    public ArrayList<String> checkWord(String word) {
        suggestions.addAll(tree.getSimilarWords(word));
        return suggestions;
    }

    public static void main(String[] args) throws IOException {
        
        String file = "termini.txt";
        String text = "";
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              text += myReader.nextLine() + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        text.toLowerCase();

        SpellChecker speller = new SpellChecker(text);

        ArrayList<String> wordsToCorrect = new ArrayList<>();
        wordsToCorrect.add("lapņa");
        wordsToCorrect.add("dilless");
        wordsToCorrect.add("greibfrūts");
        wordsToCorrect.add("bezdelgīactiņa");
        wordsToCorrect.add("knapwed");
        wordsToCorrect.add("sugaar");
        wordsToCorrect.add("coriandel");
        wordsToCorrect.add("balckberry");

        long start;
        long end;
        ArrayList<String> suggestions = new ArrayList<>();

        for(String word : wordsToCorrect){
            System.out.println("\nvards: " + word);
            float all_time = 0;
            for(int i = 0; i < 5; i++){
                suggestions.clear();
                start = System.currentTimeMillis();
                suggestions = speller.checkWord(word);
                end = System.currentTimeMillis();
                float spell_time =  (end - start) / 1000F;
                all_time += spell_time;
                if(i == 0){
                    System.out.println("labojumi: " + suggestions);
                    System.out.println("laiks: ");
                }
                System.out.println(String.format("%.10f", spell_time));
                if(i == 4){
                    float avg_time = all_time/5;
                    System.out.println("The average time to spell " + word + " is " + String.format("%.10f", avg_time));
                }
            }
        }
    }
}
