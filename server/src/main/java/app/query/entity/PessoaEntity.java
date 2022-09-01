package app.query.entity;

import app.db.Database;
import app.db.Result;
import app.dto.PessoaDados;
import app.query.Query;

public class PessoaEntity implements Entity {

    protected final Database db;

    public PessoaEntity(Database db) {
        this.db = db;
    }

    @Override
    public Result insert(Query qry) throws UnsupportedOperationException {
        var dados = new PessoaDados(qry.field(0), qry.field(1), qry.field(2));
        return db.insertPessoa(dados);
    }

    @Override
    public Result update(Query qry) throws UnsupportedOperationException {
        var dados = new PessoaDados(qry.field(0), qry.field(1), qry.field(2));
        return db.updatePessoa(dados);
    }

    @Override
    public Result delete(Query qry) throws UnsupportedOperationException {
        return db.deletePessoa(qry.field(0));
    }

    @Override
    public Result get(Query qry) throws UnsupportedOperationException {
        return db.getPessoa(qry.field(0));
    }

    @Override
    public Result list() throws UnsupportedOperationException {
        return db.listPessoas();
    }

    @Override
    public Result exists(Query qry) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("EXISTS unsupported for PESSOA");
    }
}
