package app;

import app.db.Database;
import app.query.ParseException;
import app.query.QueryExecutor;
import app.query.QueryParser;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws ParseException {

        var db = new Database();
        var exec = new QueryExecutor(db);

        if (args[0] != null) {
            if (args[0].equals("load")) {
                exec.execute(QueryParser.parse("LOADSCRIPT;script.txt"));
                System.out.println("Loaded initial script.");
            }
        }

        var in = new Scanner(System.in);
        in.useDelimiter("\n");

        while (true) {
            System.out.print("> ");
            var str = in.next();
            if (str.equals("quit")) {
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
