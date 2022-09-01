package app.db;

import app.dto.AlunoDados;
import app.dto.DisciplinaDados;
import app.dto.PessoaDados;
import app.model.Disciplina;
import app.model.Pessoa;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private final Map<String, Pessoa> pessoas;
    private final Map<String, Disciplina> disciplinas;
    private final Map<String, Set<Disciplina>> disciplinasPorProfessor;

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {
        pessoas = new ConcurrentHashMap<>();
        disciplinas = new ConcurrentHashMap<>();
        disciplinasPorProfessor = new ConcurrentHashMap<>();
    }

    public synchronized Result insertPessoa(PessoaDados dados) {
        if (pessoas.containsKey(dados.cpf)) {
            return Result.err("Já existe uma pessoa com esse CPF");
        }
        var pessoa = new Pessoa(dados.cpf, dados.nome, dados.endereco);
        pessoas.put(dados.cpf, pessoa);
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
        var disciplinas = disciplinasPorProfessor.getOrDefault(cpf, Set.of());
        if (!disciplinas.isEmpty()) {
            return Result.err("Pessoa não pode ser excluída pois é professor de alguma disciplina");
        }
        pessoas.remove(cpf);
        return Result.ok("Pessoa removida com sucesso");
    }

    public synchronized Result updatePessoa(PessoaDados dados) {
        if (!pessoas.containsKey(dados.cpf)) {
            return Result.err("Pessoa não encontrada");
        }
        var pessoa = pessoas.get(dados.cpf);
        pessoa.setNome(dados.nome);
        pessoa.setEndereco(dados.endereco);
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

    public synchronized Result insertDisciplina(DisciplinaDados dados) {
        if (disciplinas.containsKey(dados.codigo)) {
            return Result.err("Já existe uma disciplina com esse código");
        }
        if (!pessoas.containsKey(dados.cpfProfessor)) {
            return Result.err("Não existe pessoa com esse CPF pra ser professor");
        }
        var prof = pessoas.get(dados.cpfProfessor);
        var disc = new Disciplina(dados.codigo, prof);
        disciplinas.put(disc.codigo(), disc);

        disciplinasPorProfessor
            .computeIfAbsent(prof.cpf(), cpf -> new HashSet<>())
            .add(disc);

        return Result.ok("Disciplina inserida com sucesso");
    }

    public synchronized Result updateDisciplina(DisciplinaDados dados) {
        if (!disciplinas.containsKey(dados.codigo)) {
            return Result.err("Não existe disciplina com esse código");
        }
        if (!pessoas.containsKey(dados.cpfProfessor)) {
            return Result.err("Não existe pessoa com esse CPF pra ser professor");
        }
        var disc = disciplinas.get(dados.codigo);

        var cpfAntigo = disc.professor().cpf();
        var cpfNovo   = dados.cpfProfessor;

        var prof = pessoas.get(cpfNovo);
        disc.setProfessor(prof);

        disciplinasPorProfessor
            .get(cpfAntigo)
            .remove(disc);

        disciplinasPorProfessor
            .computeIfAbsent(cpfNovo, cpf -> new HashSet<>())
            .add(disc);

        return Result.ok("Disciplina atualizada com sucesso");
    }

    public synchronized Result deleteDisciplina(String codigo) {
        if (!disciplinas.containsKey(codigo)) {
            return Result.ok("Já não existe disciplina com esse código");
        }
        var disc = disciplinas.remove(codigo);
        disciplinasPorProfessor
            .get(disc.professor().cpf())
            .remove(disc);
        return Result.ok("Disciplina removida com sucesso");
    }

    public synchronized Result listDisciplinas() {
        var data = new String[1 + disciplinas.size()];
        var i = 0;
        data[i++] = "" + disciplinas.size();
        for (var disc : disciplinas.values()) {
            var prof = disc.professor();
            data[i++] = String.format("%s;%s;%s;%s", disc.codigo(), prof.cpf(), prof.nome(), prof.endereco());
        }
        return Result.data(data);
    }

    public synchronized Result getDisciplina(String codigo) {
        if (!disciplinas.containsKey(codigo)) {
            return Result.err("Não existe disciplina com esse código");
        }
        var disc = disciplinas.get(codigo);
        var prof = disc.professor();

        var alunos = disc.alunos();
        var medias = disc.mediasFinais();

        var data = new String[1 + 1 + alunos.size()];

        var i = 0;
        data[i++] = String.format("%s;%s;%s;%s",
            codigo, prof.cpf(), prof.nome(), prof.endereco()
        );
        data[i++] = "" + alunos.size();

        for (var aluno : disc.alunos()) {
            var mediaStr = "--";
            if (medias.containsKey(aluno)) {
                mediaStr = String.format("%02.2f", medias.get(aluno));
            }
            data[i++] = String.format("%s;%s;%s;%s",
                aluno.cpf(), aluno.nome(), aluno.endereco(), mediaStr
            );
        }

        return Result.data(data);
    }

    public synchronized Result insertAluno(AlunoDados dados) {
        var disciplina = disciplinas.get(dados.codigoDisciplina);
        if (disciplina == null) {
            return Result.err("Não existe disciplina com esse código");
        }
        var aluno = pessoas.get(dados.cpfAluno);
        if (aluno == null) {
            return Result.err("Não existe pessoa como esse CPF pra ser aluno");
        }
        if (disciplina.professor().cpf().equals(aluno.cpf())) {
            return Result.err("A pessoa já é professor da disciplina, não pode ser aluno também");
        }
        if (disciplina.alunos().contains(aluno)) {
            return Result.ok("O aluno já pertence à disciplina");
        }
        disciplina.alunos().add(aluno);
        return Result.ok("Aluno inserido com sucesso");
    }

    public synchronized Result updateAluno(AlunoDados dados) {
        var disciplina = disciplinas.get(dados.codigoDisciplina);
        if (disciplina == null) {
            return Result.err("Não existe disciplina com esse código");
        }
        var aluno = pessoas.get(dados.cpfAluno);
        if (!disciplina.alunos().contains(aluno)) {
            return Result.err("Esse aluno não pertence à disciplina");
        }
        disciplina.mediasFinais().put(aluno, dados.mediaFinal);
        return Result.ok("Aluno atualizado com sucesso");
    }

    public synchronized Result deleteAluno(AlunoDados dados) {
        var disciplina = disciplinas.get(dados.codigoDisciplina);
        if (disciplina == null) {
            return Result.err("Não existe disciplina com esse código");
        }
        var aluno = pessoas.get(dados.cpfAluno);
        if (disciplina.alunos().remove(aluno)) {
            return Result.ok("Aluno removido com sucesso");
        } else {
            return Result.ok("O aluno já não pertence à disciplina");
        }
    }

    public synchronized Result existsAluno(AlunoDados dados) {
        var disciplina = disciplinas.get(dados.codigoDisciplina);
        if (disciplina == null) {
            return Result.err("Aluno não existe (disciplina não encontrada)");
        }
        var aluno = pessoas.get(dados.cpfAluno);
        if (aluno == null) {
            return Result.err("Aluno não existe (pessoa não encontrada)");
        }
        if (disciplina.alunos().contains(aluno)) {
            return Result.ok("Aluno existe");
        } else {
            return Result.ok("Aluno não existe");
        }
    }
}
