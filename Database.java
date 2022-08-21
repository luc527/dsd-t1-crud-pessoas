import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private final Map<String, Pessoa> pessoas;

    public Database() {
        pessoas = new ConcurrentHashMap<>();
    }

    public synchronized Result insertPessoa(Pessoa dados) {
        if (pessoas.containsKey(dados.cpf())) {
            return Result.err("Já existe uma pessoa com esse CPF");
        }
        var pessoa = Pessoa.copy(dados);
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

    public synchronized Result updatePessoa(Pessoa dados) {
        if (!pessoas.containsKey(dados.cpf())) {
            return Result.err("Pessoa não encontrada");
        }
        var pessoa = pessoas.get(dados.cpf());
        pessoa.setNome(dados.nome());
        pessoa.setEndereco(dados.endereco());
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
