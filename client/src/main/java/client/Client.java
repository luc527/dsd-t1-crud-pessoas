package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        parseArguments(args);

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("Type \"quit\" to exit application.");

        while (true) {
            System.out.print("> ");
            String command = scanner.next();
            if (command.equals("quit")) break;

            String[] commandTokens = command.split(";");
            if (commandTokens.length > 1 && commandTokens[0].equals("loadscript")) {
                if (commandTokens.length == 2) {
                    requestCommandsFromFile(commandTokens[1]);
                } else {
                    System.out.println("loadscript command requires one and only one extra token: the path of the script file to load and run");
                }
            }
            else {
                request(command);
            }
        }
    }

    public static void request(String command) {
        try (Socket connection = new Socket(Arguments.I().getServerHost(), Arguments.I().getServerPort())) {
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

    public static void requestCommandsFromFile(String filename) {
        try (var in = new BufferedReader(new FileReader(filename))) {
            for (String line; (line = in.readLine()) != null;) {
                if (line.isBlank()) continue;
                if (line.charAt(0) == '#') continue;
                System.out.println("~> Executando " + line);
                request(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Script file does not exist");
        } catch (IOException e) {
            System.out.println("Error while opening script file: " + filename);
        }
    }

    public static void parseArguments(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Invalid arguments");
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Arguments.I().setServerHost(host).setServerPort(port);
    }

}
