package org.queryex.checker;

import org.queryex.Dictionnary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Processor {

    public static Processor instance;
    private static String SPACE = " ";
    public static Processor getInstance() {
        if (instance == null){
            instance = new Processor();
        }
        return instance;
    }

    public StringBuilder extract(StringBuffer text,int lvl){
        String fileName = "c://sandboxes//testquery//lvl"+lvl+".txt";
        ArrayList<String> queries = new ArrayList<>();
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            StringBuilder tempQuery = new StringBuilder();
            AtomicReference<Boolean> activeAppending = new AtomicReference<>(false);
            AtomicReference<Boolean> openParenthese = new AtomicReference<>(false);
            stream.forEach(line -> {
                //if (line.trim().contains("SELECT") && line.trim().startsWith("SELECT")){
                //    tempQuery.append(line);
                //}
                for (String word:Dictionnary.ALL_WORDS){
                    if (line.trim().startsWith(word) && getNextWord(word) != null && isFollowedBy(line,word,getNextWord(word))){
                        tempQuery.append(SPACE);
                        tempQuery.append(line);
                        activeAppending.set(true);
                    }

                }

            });
            return tempQuery;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StringBuilder();
    }
    ///instead of this method create a class transition ==> all possibilities
    private String getNextWord(String word) {
        if (word.equals("SELECT")){
            return "FROM";
        } else if (word.equals("INNER")) {
            return "JOIN";
        }else if (word.equals("WHERE")) {
            return "=";
        }
        return null;
    }

    private boolean isFollowedBy(String line,String word,String nextWord) {
        String expression = word+".*"+nextWord;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return  matcher.find();
    }
/*
SELECT * FROM CUSTOMER C
join test t on t.id = c.id
INNER JOIN CART CAR ON CAR.ID in (
				SELECT * FROM CUSTOMER C
				INNER JOIN CART CAR ON CAR.ID = C.CART_ID
				WHERE C.EXTERNAL_ID = 5
				)
WHERE C.EXTERNAL_ID = 5
                GROUP BY c.EXTERNAL_ID
ORDER BY TEST.ID
 */
}
