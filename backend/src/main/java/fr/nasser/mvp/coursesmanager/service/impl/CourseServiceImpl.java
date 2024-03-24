package fr.nasser.mvp.coursesmanager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.exception.CourseException;
import fr.nasser.mvp.coursesmanager.exception.ErrorMessages;
import fr.nasser.mvp.coursesmanager.mapping.DtosEntitiesMapper;
import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import fr.nasser.mvp.coursesmanager.repositories.PartantRepository;
import fr.nasser.mvp.coursesmanager.service.CourseService;
import fr.nasser.mvp.coursesmanager.service.KafkaCourseProducer;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import fr.nasser.mvp.coursesmanager.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;

    PartantRepository partantRepository;

    KafkaCourseProducer kafkaCourseProducer;

    ObjectMapper objectMapper;

    DtosEntitiesMapper mapper;

    @Value("${kafka.courses.topic}")
    String TOPIC_KAFKA;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             KafkaCourseProducer kafkaCourseProducer,
                             ObjectMapper objectMapper,
                             DtosEntitiesMapper mapper,
                             PartantRepository partantRepository){
        this.courseRepository = courseRepository;
        this.kafkaCourseProducer=kafkaCourseProducer;
        this.objectMapper = objectMapper;
        this.mapper=mapper;
        this.partantRepository = partantRepository;
    }

    /**
     * enregistrer une course avec ses partants en BDD
     *
     * @param course
     * @return id de la course créée
     */
    @Override
    @Transactional
    public long saveCourse(Course course) {
        // sauvegarder la course dans la BDD
        CourseEntity courseEntity = courseRepository.save(mapper.getCourseEntityFromDTO(course));
        // sauvegarder tous le partants
        courseEntity.setPartants(partantRepository.saveAll(course.getPartants().stream()
                        .map(partant -> {
                            PartantEntity partantEntity = mapper.getPartantEntityFromDTO(partant);
                            partantEntity.setCourse(courseEntity);
                            return partantEntity;
                        })
                        .toList()));

        try {
            String json = objectMapper.writeValueAsString(courseEntity);
            kafkaCourseProducer.sendMessage(TOPIC_KAFKA, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return courseEntity.getId();
    }

    /**
     * @return la liste de toutes les courses
     */
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseEntity -> mapper.getCourseDTOFromCourseEntity(courseEntity))
                .toList();
    }

    /**
     * @param nomCourse
     * @param numCourse
     * @return la course qui a comme nom : nomCourse et numero : numCourse
     */
    @Override
    public Course getCourse(String nomCourse, long numCourse) {
        Course course = mapper.getCourseDTOFromCourseEntity(courseRepository.findByNomAndNumero(nomCourse, numCourse));
        if (course == null)
            throw new CourseException(ErrorMessages.COURSE_NOT_FOUND);
        return course;
    }

}
