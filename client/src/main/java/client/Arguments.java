package client;

public class Arguments {
    private static Arguments instance;

    private String serverHost;
    private int serverPort;

    public static Arguments I() {
        if (instance == null) {
            instance = new Arguments();
        }

        return instance;
    }

    public String getServerHost() {
        return serverHost;
    }

    public Arguments setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    public int getServerPort() {
        return serverPort;
    }

    public Arguments setServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }
}
