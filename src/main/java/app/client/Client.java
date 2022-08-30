package app.client;

import app.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Type \"quit\" to exit application.");

        while (true) {
            System.out.print("> ");
            String command = scanner.next();
            if (command.equals("quit")) break;
            request(command);
        }
    }

    public static void request(String command) {
        try (Socket connection = new Socket("127.0.0.1", Server.PORT)) {
            try (
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ) {
                out.println(command);

                for (String line; (line = in.readLine()) != null;) {
                    if (!line.isBlank()) {
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while connection to host: " + e.getMessage());
        }
    }

}
