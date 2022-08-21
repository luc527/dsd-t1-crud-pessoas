package model;

import java.util.Objects;

public class Pessoa implements Comparable<Pessoa> {

    private final String cpf;
    private String nome;
    private String endereco;

    public Pessoa(String cpf, String nome, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
    }

    public String cpf() { return this.cpf; }
    public String nome() { return this.nome; }
    public String endereco() { return this.endereco; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return cpf.equals(pessoa.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public int compareTo(Pessoa o) {
        return cpf.compareTo(o.cpf);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", cpf, nome, endereco);
    }
}
