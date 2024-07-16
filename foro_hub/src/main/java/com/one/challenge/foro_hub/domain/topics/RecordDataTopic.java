package com.one.challenge.foro_hub.domain.topics;

import jakarta.validation.constraints.NotBlank;

public record RecordDataTopic(
        @NotBlank(message = "El Titulo es obligatorio")
        String title,
        @NotBlank(message = "El Mensaje es obligatorio")
        String message,
        @NotBlank(message = "El ID del autor es obligatorio")
        String authorId,
        @NotBlank(message = "El ID del curso es obligatorio")
        String courseId
) {
}