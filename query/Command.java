package query;

import java.util.Optional;

public enum Command {
    INSERT,
    UPDATE,
    DELETE,
    GET,
    LIST,
    EXISTS,
    LOADSCRIPT;

    public static Optional<Command> from(String cmd) {
        switch (cmd) {
            case "INSERT": return Optional.of(INSERT);
            case "UPDATE": return Optional.of(UPDATE);
            case "DELETE": return Optional.of(DELETE);
            case "GET": return Optional.of(GET);
            case "LIST": return Optional.of(LIST);
            case "EXISTS": return Optional.of(EXISTS);
            case "LOADSCRIPT": return Optional.of(LOADSCRIPT);
            default: return Optional.empty();
        }
    }
}
