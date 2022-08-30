package app.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 80;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.printf("Server up on port %s\n", PORT);
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
}
