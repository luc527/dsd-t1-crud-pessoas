package query;

import query.entity.EntityTag;

public class Query {

    private final Command cmd;
    private final EntityTag entityTag;
    private final String[] fields;

    public Query(Command cmd, EntityTag entityTag, String[] fields) {
        this.cmd = cmd;
        this.entityTag = entityTag;
        this.fields = fields;
    }

    public Command command() {
        return this.cmd;
    }

    public EntityTag entityTag() {
        return this.entityTag;
    }

    public String field(int idx) throws FieldOutOfBoundsException {
        return this.fields[idx];
    }

    public String toString() {
        return String.format("%s %s %s", cmd, entityTag, String.join(";", fields));
    }
}
