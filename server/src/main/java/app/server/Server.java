package app.server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO remove all the threading + synchronization stuff in the db, unnecessary

public class Server {

    public static void main(String[] args) throws Exception {
        parseArguments(args);
        int port = Arguments.I().getServerPort();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.printf("Server up on port %s\n", port);
            server.setReuseAddress(true);

            while (true) {
                try {
                    executorService.execute(new Handler(server.accept()));
                } catch (Exception e) {
                    System.out.println("Error while handling connection. " + e.getMessage());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not start server due to: " + e.getMessage());
        }
    }


    public static void parseArguments(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Invalid arguments");
        }

        int port = Integer.parseInt(args[0]);

        Arguments.I().setServerPort(port);
    }
}
