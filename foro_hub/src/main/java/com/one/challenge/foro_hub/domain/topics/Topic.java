package com.one.challenge.foro_hub.domain.topics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Year;

@Table(name = "topic", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "message"})
})
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topic_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(name = "creation_year")
    private Integer creationYear;

    private String status;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "course_id", nullable = false)
    private String courseId;

    private String response;

    public Topic(RecordDataTopic recordDataTopic) {
        this.title = recordDataTopic.title();
        this.message = recordDataTopic.message();
        this.authorId = recordDataTopic.authorId();
        this.courseId = recordDataTopic.courseId();
        this.creationYear = Year.now().getValue();
        this.status = "activo";
    }
}
