import java.util.Optional;

public enum Command {
    INSERT,
    UPDATE,
    DELETE,
    GET,
    LIST,
    HAS;

    public static Optional<Command> from(String cmd) {
        switch (cmd) {
            case "INSERT": return Optional.of(INSERT);
            case "UPDATE": return Optional.of(UPDATE);
            case "DELETE": return Optional.of(DELETE);
            case "GET": return Optional.of(GET);
            case "LIST": return Optional.of(LIST);
            case "HAS": return Optional.of(HAS);
            default: return Optional.empty();
        }
    }
}
