package query;

import java.util.Optional;

public enum Entity {
    PESSOA,
    DISCIPLINA,
    ALUNO;

    public static Optional<Entity> from(String entity) {
        switch (entity) {
            case "PESSOA": return Optional.of(PESSOA);
            case "DISCIPLINA": return Optional.of(DISCIPLINA);
            case "ALUNO": return Optional.of(ALUNO);
            default: return Optional.empty();
        }
    }
}
