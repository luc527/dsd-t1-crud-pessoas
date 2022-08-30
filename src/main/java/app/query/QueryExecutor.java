package app.query;

import app.db.Database;
import app.db.Result;
import app.query.entity.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

public class QueryExecutor {

    private final EnumMap<EntityTag, Entity> entities;

    public QueryExecutor(Database db) {
        entities = new EnumMap<>(EntityTag.class);
        entities.put(EntityTag.PESSOA, new PessoaEntity(db));
        entities.put(EntityTag.DISCIPLINA, new DisciplinaEntity(db));
        entities.put(EntityTag.ALUNO, new AlunoEntity(db));
    }

    public Result execute(Query qry) {
        try {

            var cmd = qry.command();

            if (cmd.equals(Command.LOADSCRIPT)) {
                var script = qry.field(0);
                try (var in = new BufferedReader(new FileReader(script))) {
                    var messages = new ArrayList<String>();
                    String qryStr;
                    while ((qryStr = in.readLine()) != null) {
                        qryStr = qryStr.trim();

                        if (qryStr.length() == 0) continue;
                        if (qryStr.charAt(0) == '#') continue;

                        var parsedQry = QueryParser.parse(qryStr);
                        if (parsedQry.command().equals(Command.LOADSCRIPT)) {
                            return Result.err("Recursive LOADSCRIPT forbidden!");
                        }
                        var result = execute(parsedQry);
                        messages.add(parsedQry + " : " + result.message());
                    }
                    return Result.data(messages.toArray(new String[0]));
                } catch (FileNotFoundException e) {
                    return Result.err("Arquivo de script não existe");
                } catch (IOException e) {
                    return Result.err("Erro ao abrir o arquivo de script");
                } catch (ParseException e) {
                    return Result.err("Erro ao ler query de script ("+e.getQueryString()+")");
                }
            }

            var entity = entities.get(qry.entityTag());
            switch (qry.command()) {
                case INSERT: return entity.insert(qry);
                case UPDATE: return entity.update(qry);
                case DELETE: return entity.delete(qry);
                case GET: return entity.get(qry);
                case LIST: return entity.list();
                case EXISTS: return entity.exists(qry);
            }
        } catch (FieldOutOfBoundsException e) {
            return Result.err("Too few arguments");
        } catch (UnsupportedOperationException e) {
            return Result.err(e.getMessage());
        }
        throw new RuntimeException("unreachable");
    }
}
