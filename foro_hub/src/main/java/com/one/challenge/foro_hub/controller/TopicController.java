package com.one.challenge.foro_hub.controller;

import com.one.challenge.foro_hub.domain.topics.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topic")
@Validated
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<String> createTopic(@Valid @RequestBody RecordDataTopic recordDataTopic) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        if (topicRepository.existsByTitleAndMessage(recordDataTopic.title(), recordDataTopic.message())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tópico ya existe.");
        }

        // Guardar el nuevo tópico en la base de datos
        try {
            Topic topic = new Topic(recordDataTopic);
            topicRepository.save(topic);
            return ResponseEntity.status(HttpStatus.CREATED).body("El tópico ha sido creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el tópico.");
        }
    }

    @GetMapping
    public ResponseEntity<Page<Topic>> getAllTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTopic(@PathVariable Long id, @Valid @RequestBody RecordDataTopic recordDataTopic) {
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isPresent()) {
            Topic topic = topicOpt.get();
            topic.t(recordDataTopic.getTitle());
            topic.setMessage(recordDataTopic.getMessage());
            topic.setAuthorId(recordDataTopic.getAuthorId());
            topic.setCourseId(recordDataTopic.getCourseId());
            topicRepository.save(topic);
            return ResponseEntity.ok("Tópico actualizado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}