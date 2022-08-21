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
        } catch (IndexOutOfBoundsException e) {
            return Result.err("Too few arguments");
        }
        throw new RuntimeException("unreachable");
    }

    private Result pessoaExecute(Query qry) {
        switch (qry.command()) {
            case INSERT: {
                var pessoa = new Pessoa(qry.field(0), qry.field(1), qry.field(2));
                return db.insertPessoa(pessoa);
            }
            case UPDATE: {
                var pessoa = new Pessoa(qry.field(0), qry.field(1), qry.field(2));
                return db.updatePessoa(pessoa);
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
        return Result.err("unimplemented");
    }

    private Result alunoExecute(Query qry) {
        return Result.err("unimplemented");
    }
}
