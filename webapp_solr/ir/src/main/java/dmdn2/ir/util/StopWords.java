package dmdn2.ir.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StopWords {

    public List<String> stopwords;


    public StopWords() throws IOException {

        this.stopwords = Files.readAllLines(Paths.get("src/main/resources/stopwords/ita.txt"));
        this.stopwords.addAll(Files.readAllLines(Paths.get("src/main/resources/stopwords/eng.txt")));
    }

    public String removeAll(String data) {
        ArrayList<String> allWords = Stream.of(data.split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        System.out.println(this.stopwords);
        allWords.removeAll(this.stopwords);
        return allWords.stream().collect(Collectors.joining(" "));
    }
}
