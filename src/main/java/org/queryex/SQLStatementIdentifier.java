package org.queryex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLStatementIdentifier {
    public static void main(String[] args) {
        String text = "This is a sample SQL statement: SELECT * FROM customers WHERE age > 30;";
        //text = "This is a sample SQL statement: SELECT * FROM customers WHERE age > 30 AND (SELECT COUNT(*) FROM orders WHERE customer_id = 123) > 5 group by text.id This is a sample SQL statement: ";
        text = "This is some text.\n"
                + "SELECT * FROM customers WHERE age > 30;\n"
                + "And here is another paragraph.\n"
                + "INSERT INTO orders (customer_id, product) VALUES (123, 'Widget')\n"
                + "This is the end.";
        text = "This is some text.\n"
                + "SELECT * FROM customers WHERE age > 30 AND (SELECT COUNT(*) FROM orders WHERE customer_id = 123) > 5;\n"
                +"and watch this :"
                + "SELECT * FROM customers WHERE age > 30 AND (" +
                "SELECT COUNT(*) FROM orders " +
                "WHERE customer_id = 123" +
                ") > 5;\n"
                +"and watch this :"
                + "SELECT * FROM customers WHERE age > 30 AND 1 > 5;\n"
                + "And here is another paragraph.\n"
                + "INSERT INTO orders (customer_id, product) VALUES (123, 'Widget');\n"
                + "This is the end.";
        // Define a regular expression pattern to match SQL statements
        //String regex = "\\b(SELECT|INSERT|UPDATE|DELETE)\\b.*?;";
        //String regex = "\\b(SELECT|INSERT|UPDATE|DELETE)\\b.*?;(?:(?:[^();]*\\([^();]*\\)[^();]*)*;)?";
        //String regex = "\\b(SELECT|INSERT|UPDATE|DELETE)\\b(?:\\s*\\([^()]*\\))*[^;]*";
        //String regex = "(?s)(?<=\\n)(SELECT|INSERT|UPDATE|DELETE)\\b(?:\\s*\\([^()]*\\))*[^;]*(?=\\n)";
        //String regex = "(?s)(?<=\\n)(?:(?:SELECT|INSERT|UPDATE|DELETE)\\b(?:\\s*\\([^()]*\\))*[^;]*)+(?=\\n)";
        //String regex = "(?s)(?<=\\n)((?:SELECT|INSERT|UPDATE|DELETE)\\b(?:\\s*\\([^()]*\\))*[^;]*)(?=\\n)";
        String regex = "\\b(SELECT|INSERT|UPDATE|DELETE)\\b(?:\\s*\\([^;]*\\))*[^;]*";




        /////pl sql

        text = "This is some text.\n" +
                "DECLARE\n" +
                "   v_name VARCHAR2(50) := 'John'\n" +
                "BEGIN\n" +
                "   -- PL/SQL code here\n" +
                "   DBMS_OUTPUT.PUT_LINE('Hello, ' || v_name || '!')\n" +
                "END\n" +
                "And here is another paragraph.\n" +
                "DECLARE\n" +
                "   v_count NUMBER := 10\n" +
                "BEGIN\n" +
                "   -- More PL/SQL code here\n" +
                "   DBMS_OUTPUT.PUT_LINE('Count: ' || v_count)\n" +
                "END\n" +
                "This is the end.";

        // Define a regular expression pattern to match PL/SQL code with variables between two paragraphs without semicolons
        regex = "(?s)(?<=\\n)(DECLARE(.*?)BEGIN.*?END)(?=\\n)";




        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String sqlStatement = matcher.group();
            System.out.println("Found SQL statement: " + sqlStatement);
        }
    }
}