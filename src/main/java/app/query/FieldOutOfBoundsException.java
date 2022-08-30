package app.query;

public class FieldOutOfBoundsException extends IndexOutOfBoundsException {
    public FieldOutOfBoundsException(String msg) {
        super(msg);
    }
}
