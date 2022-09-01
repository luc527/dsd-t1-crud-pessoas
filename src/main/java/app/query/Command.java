package app.query;

import java.util.Optional;

public enum Command {
    INSERT,
    UPDATE,
    DELETE,
    GET,
    LIST,
    EXISTS;

    public static Optional<Command> from(String cmd) {
        return switch (cmd) {
            case "INSERT" -> Optional.of(INSERT);
            case "UPDATE" -> Optional.of(UPDATE);
            case "DELETE" -> Optional.of(DELETE);
            case "GET" -> Optional.of(GET);
            case "LIST" -> Optional.of(LIST);
            case "EXISTS" -> Optional.of(EXISTS);
            default -> Optional.empty();
        };
    }
}
