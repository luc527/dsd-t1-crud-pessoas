package app.query.entity;

import java.util.Optional;

public enum EntityTag {
    PESSOA,
    DISCIPLINA,
    ALUNO;

    public static Optional<EntityTag> from(String entity) {
        return switch (entity) {
            case "PESSOA" -> Optional.of(PESSOA);
            case "DISCIPLINA" -> Optional.of(DISCIPLINA);
            case "ALUNO" -> Optional.of(ALUNO);
            default -> Optional.empty();
        };
    }
}
