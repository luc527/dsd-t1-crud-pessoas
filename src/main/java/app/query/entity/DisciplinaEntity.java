package app.query.entity;

import app.db.Database;
import app.db.Result;
import app.dto.DisciplinaDados;
import app.query.Query;

public class DisciplinaEntity implements Entity {

    protected final Database db;

    public DisciplinaEntity(Database db) {
        this.db = db;
    }

    @Override
    public Result insert(Query qry) throws UnsupportedOperationException {
        var dados = new DisciplinaDados(qry.field(0), qry.field(1));
        return db.insertDisciplina(dados);
    }

    @Override
    public Result update(Query qry) throws UnsupportedOperationException {
        var dados = new DisciplinaDados(qry.field(0), qry.field(1));
        return db.updateDisciplina(dados);
    }

    @Override
    public Result delete(Query qry) throws UnsupportedOperationException {
        return db.deleteDisciplina(qry.field(0));
    }

    @Override
    public Result get(Query qry) throws UnsupportedOperationException {
        return db.getDisciplina(qry.field(0));
    }

    @Override
    public Result list() throws UnsupportedOperationException {
        return db.listDisciplinas();
    }

    @Override
    public Result exists(Query qry) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("EXISTS unsupported for DISCIPLINA");
    }
}
