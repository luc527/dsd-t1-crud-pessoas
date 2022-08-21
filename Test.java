import db.Database;
import query.ParseException;
import query.QueryExecutor;
import query.QueryParser;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        var db = new Database();
        var exec = new QueryExecutor(db);

        var in = new Scanner(System.in);
        in.useDelimiter("\n");

        while (true) {
            System.out.print("> ");
            var str = in.next().toUpperCase();
            if (str.equals("QUIT")) {
                break;
            }

            try {
                var qry = QueryParser.parse(str);
                var res = exec.execute(qry);
                System.out.print(res);
            } catch (ParseException e) {
                System.out.println("Invalid query: " + e.getMessage());
            }
        }

    }
}
