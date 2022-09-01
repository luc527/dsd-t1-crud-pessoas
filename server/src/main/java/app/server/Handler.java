package app.server;

import app.db.Database;
import app.db.Result;
import app.query.Query;
import app.query.QueryExecutor;
import app.query.QueryParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {
    private final Socket connection;

    Handler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.printf("Handling %s request.\n", connection.getInetAddress().getHostAddress());
        try (
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        ) {
            try {
                String command = in.readLine();
                QueryExecutor exec = new QueryExecutor(Database.getInstance());
                Query query = QueryParser.parse(command);
                Result result = exec.execute(query);
                out.println(result);
            } catch (Exception e) {
                out.println(e.getMessage());
            } finally {
                connection.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
