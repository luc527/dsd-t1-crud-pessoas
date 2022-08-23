package query.entity;

import db.Database;
import db.Result;
import dto.AlunoDados;
import query.Query;

public class AlunoEntity implements Entity {

    private final Database db;

    public AlunoEntity(Database db) {
        this.db = db;
    }

    @Override
    public Result insert(Query qry) throws UnsupportedOperationException {
        var dados = new AlunoDados(qry.field(0), qry.field(1));
        return db.insertAluno(dados);
    }

    @Override
    public Result update(Query qry) throws UnsupportedOperationException {
        var dados = new AlunoDados(qry.field(0), qry.field(1), Float.parseFloat(qry.field(2)));
        return db.updateAluno(dados);
    }

    @Override
    public Result delete(Query qry) throws UnsupportedOperationException {
        var dados = new AlunoDados(qry.field(0), qry.field(1));
        return db.deleteAluno(dados);
    }

    @Override
    public Result get(Query qry) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("GET unsupported for ALUNO");
    }

    @Override
    public Result list() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("LIST unsupported for ALUNO");
    }

    @Override
    public Result exists(Query qry) throws UnsupportedOperationException {
        var dados = new AlunoDados(qry.field(0), qry.field(1));
        return db.existsAluno(dados);
    }
}
