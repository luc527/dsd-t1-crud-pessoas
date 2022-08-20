public class ParseException extends Exception {

    private final String query;

    public ParseException(String message, String query) {
        super(message);
        this.query = query;
    }

    public String getQueryString() {
        return query;
    }
}
