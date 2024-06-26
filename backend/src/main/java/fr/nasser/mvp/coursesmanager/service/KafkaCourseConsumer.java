package fr.nasser.mvp.coursesmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaCourseConsumer {

    ObjectMapper objectMapper;

    @Autowired
    public KafkaCourseConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.courses.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            log.debug("message kafka : {}", message);
            CourseEntity course = objectMapper.readValue(message, new TypeReference<CourseEntity>(){});
            log.debug("Course : "+ course);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
