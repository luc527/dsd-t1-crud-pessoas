package query;

import db.Database;
import db.Result;
import dto.DisciplinaDados;
import dto.PessoaDados;
import query.entity.DisciplinaEntity;
import query.entity.Entity;
import query.entity.EntityTag;
import query.entity.PessoaEntity;

import java.util.EnumMap;

public class QueryExecutor {

    private final EnumMap<EntityTag, Entity> entities;

    public QueryExecutor(Database db) {
        entities = new EnumMap<>(EntityTag.class);
        entities.put(EntityTag.PESSOA, new PessoaEntity(db));
        entities.put(EntityTag.DISCIPLINA, new DisciplinaEntity(db));
    }

    public Result execute(Query qry) {
        try {
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
        } catch (UnsupportedClassVersionError e) {
            return Result.err(e.getMessage());
        }
        throw new RuntimeException("unreachable");
    }
}
