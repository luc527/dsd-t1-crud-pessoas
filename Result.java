import java.util.Arrays;
import java.util.List;

public class Result {

    public static final boolean OK = true;
    public static final boolean ERR = false;

    private final boolean ok;
    private final String message;
    private final String[] data;

    public static Result ok(String message) {
        return new Result(Result.OK, message, new String[0]);
    }

    public static Result ok(String message, String[] data) {
        return new Result(Result.OK, message, data);
    }

    public static Result err(String message) {
        return new Result(Result.ERR, message, new String[0]);
    }

    public static Result data(String[] data) {
        return new Result(Result.OK, "", data);
    }

    public static Result data(String data) {
        return new Result(Result.OK, "", new String[]{data});
    }

    private Result(boolean ok, String message, String[] data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public boolean ok() { return ok; }
    public String message() { return message; }

    public boolean hasData() {
        return data.length > 0;
    }

    public boolean hasMessage() {
        return message.length() > 0;
    }

    public Iterable<String> data() {
        return List.of(data);
    }

    @Override
    public String toString() {
        var s = (ok ? "OK" : "ERR")
              + (hasMessage() ? ": " + message : "")
              + "\n";
        if (!hasData()) {
            return s;
        }
        var sb = new StringBuilder();
        sb.append(s);
        for (var d : data) {
            sb.append(d).append("\n");
        }
        return sb.toString();
    }
}
