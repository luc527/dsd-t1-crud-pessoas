package app.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Disciplina {

    private final String codigo;
    private       Pessoa professor;
    private final Set<Pessoa> alunos;
    private final Map<Pessoa, Float> mediasFinais;

    public Disciplina(String codigo, Pessoa professor) {
        this.codigo = codigo;
        this.professor = professor;
        this.alunos = new HashSet<>();
        this.mediasFinais = new HashMap<>();
    }

    public static Disciplina copy(Disciplina d) {
        return new Disciplina(d.codigo, d.professor);
    }

    public String codigo() { return codigo; }
    public Pessoa professor() { return professor; }

    public void setProfessor(Pessoa professor) {
        this.professor = professor;
    }

    public Set<Pessoa> alunos() {
        return alunos;
    }

    public Map<Pessoa, Float> mediasFinais() {
        return mediasFinais;
    }
}
