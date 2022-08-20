public class Query {

    private final Command cmd;
    private final Entity entity;
    private final String[] fields;

    public Query(Command cmd, Entity entity, String[] fields) {
        this.cmd = cmd;
        this.entity = entity;
        this.fields = fields;
    }

    public Command command() {
        return this.cmd;
    }

    public Entity entity() {
        return this.entity;
    }

    public String field(int idx) throws IndexOutOfBoundsException {
        return this.fields[idx];
    }

    public String toString() {
        return String.format("%s %s %s", cmd, entity, String.join(";", fields));
    }
}
