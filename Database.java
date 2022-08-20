import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private final Map<String, Pessoa> pessoas;

    public Database() {
        pessoas = new ConcurrentHashMap<>();
    }

    public synchronized Result insertPessoa(Pessoa pessoa) {
        if (pessoas.containsKey(pessoa.cpf())) {
            return Result.err("Já existe uma pessoa com esse CPF");
        }
        pessoas.put(pessoa.cpf(), pessoa);
        return Result.ok("Pessoa inserida com sucesso");
    }

    public synchronized Result getPessoa(String cpf) {
        if (!pessoas.containsKey(cpf)) {
            return Result.err("Pessoa não encontrada");
        }
        return Result.data(pessoas.get(cpf).toString());
    }

    public synchronized Result deletePessoa(String cpf) {
        if (!pessoas.containsKey(cpf)) {
            return Result.err("Pessoa não encontrada");
        }
        pessoas.remove(cpf);
        return Result.ok("Pessoa removida com sucesso");
    }

    public synchronized Result updatePessoa(Pessoa pessoa) {
        if (!pessoas.containsKey(pessoa.cpf())) {
            return Result.err("Pessoa não encontrada");
        }
        pessoas.put(pessoa.cpf(), pessoa);
        return Result.ok("Pessoa atualizada com sucesso");
    }

    public synchronized Result listPessoas() {
        var data = new String[1 + pessoas.size()];
        data[0] = "" + pessoas.size();
        var i = 1;
        for (var pessoa : pessoas.values()) {
            data[i++] = pessoa.toString();
        }
        return Result.data(data);
    }

}
