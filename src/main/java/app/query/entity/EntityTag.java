package app.query.entity;

import java.util.Optional;

public enum EntityTag {
    PESSOA,
    DISCIPLINA,
    ALUNO;

    public static Optional<EntityTag> from(String entity) {
        switch (entity) {
            case "PESSOA": return Optional.of(PESSOA);
            case "DISCIPLINA": return Optional.of(DISCIPLINA);
            case "ALUNO": return Optional.of(ALUNO);
            default: return Optional.empty();
        }
    }
}
