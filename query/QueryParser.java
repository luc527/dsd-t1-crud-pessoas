package query;

import query.entity.EntityTag;

public class QueryParser {

    public static Query parse(String query) throws ParseException {
        if (query == null) {
            throw new ParseException("Null string query", query);
        }
        var toks = query.split(";");
        if (toks.length < 2) {
            throw new ParseException("Missing command and/or entity tokens", query);
        }
        var cmd = Command
            .from(toks[0].toUpperCase())
            .orElseThrow(() -> new ParseException("Invalid command", query));
        if (cmd.equals(Command.LOADSCRIPT)) {
            return new Query(cmd, null, new String[]{toks[1]});
        }
        var entity = EntityTag
            .from(toks[1].toUpperCase())
            .orElseThrow(() -> new ParseException("Invalid entity", query));
        var fields = new String[toks.length-2];
        System.arraycopy(toks, 2, fields, 0, toks.length-2);
        return new Query(cmd, entity, fields);
    }
}
