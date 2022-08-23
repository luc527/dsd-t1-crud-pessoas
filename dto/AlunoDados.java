package dto;

public class AlunoDados {

    public String cpfAluno;
    public String codigoDisciplina;
    public Float mediaFinal;

    public AlunoDados(String cpfAluno, String codigoDisciplina) {
        this(cpfAluno, codigoDisciplina, null);
    }

    public AlunoDados(String cpfAluno, String codigoDisciplina, Float mediaFinal) {
        this.cpfAluno = cpfAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.mediaFinal = mediaFinal;
    }
}
