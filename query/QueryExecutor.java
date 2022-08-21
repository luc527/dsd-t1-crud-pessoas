package query;

import db.Database;
import db.Result;
import dto.DisciplinaDados;
import dto.PessoaDados;

public class QueryExecutor {

    private final Database db;

    public QueryExecutor(Database db) {
        this.db = db;
    }

    public Result execute(Query qry) {
        try {
            switch (qry.entity()) {
                case PESSOA: return pessoaExecute(qry);
                case DISCIPLINA: return disciplinaExecute(qry);
                case ALUNO: return alunoExecute(qry);
            }
        } catch (FieldOutOfBoundsException e) {
            return Result.err("Too few arguments");
        }
        throw new RuntimeException("unreachable");
    }

    private Result pessoaExecute(Query qry) {
        switch (qry.command()) {
            case INSERT: {
                var dados = new PessoaDados(qry.field(0), qry.field(1), qry.field(2));
                return db.insertPessoa(dados);
            }
            case UPDATE: {
                var dados = new PessoaDados(qry.field(0), qry.field(1), qry.field(2));
                return db.updatePessoa(dados);
            }
            case DELETE: {
                return db.deletePessoa(qry.field(0));
            }
            case GET: {
                return db.getPessoa(qry.field(0));
            }
            case LIST: {
                return db.listPessoas();
            }
            case EXISTS: {
                return Result.err("EXISTS unsupported for PESSOA");
            }
        }
        throw new RuntimeException("unreachable");
    }

    private Result disciplinaExecute(Query qry) {
        switch (qry.command()) {
            case INSERT: {
                var dados = new DisciplinaDados(qry.field(0), qry.field(1));
                return db.insertDisciplina(dados);
            }
            case UPDATE: {
                var dados = new DisciplinaDados(qry.field(0), qry.field(1));
                return db.updateDisciplina(dados);
            }
            case DELETE: {
                return db.deleteDisciplina(qry.field(0));
            }
            case GET: {
                return db.getDisciplina(qry.field(0));
            }
            case LIST: {
                return db.listDisciplinas();
            }
            case EXISTS: {
                return Result.err("EXISTS unsupported for DISCIPLINA");
            }
        }
        throw new RuntimeException("unreachable");
    }

    private Result alunoExecute(Query qry) {
        return Result.err("unimplemented");
    }
}
