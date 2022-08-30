package app.query;

import app.query.entity.EntityTag;

public class QueryParser {

    public static Query parse(String query) throws ParseException {
        if (query == null) {
            throw new ParseException("Null string query", null);
        }
        var tokens = query.split(";");
        if (tokens.length < 2) {
            throw new ParseException("Missing command and/or entity tokens", query);
        }
        var cmd = Command
            .from(tokens[0].toUpperCase())
            .orElseThrow(() -> new ParseException("Invalid command", query));
        if (cmd.equals(Command.LOADSCRIPT)) {
            return new Query(cmd, null, new String[]{tokens[1]});
        }
        var entity = EntityTag
            .from(tokens[1].toUpperCase())
            .orElseThrow(() -> new ParseException("Invalid entity", query));
        var fields = new String[tokens.length-2];
        System.arraycopy(tokens, 2, fields, 0, tokens.length-2);
        return new Query(cmd, entity, fields);
    }
}
