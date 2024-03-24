package fr.nasser.mvp.coursesmanager.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.api.model.Partant;
import fr.nasser.mvp.coursesmanager.mapping.DtosEntitiesMapper;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import fr.nasser.mvp.coursesmanager.repositories.PartantRepository;
import fr.nasser.mvp.coursesmanager.service.KafkaCourseProducer;
import fr.nasser.mvp.coursesmanager.service.impl.CourseServiceImpl;
import fr.nasser.mvp.coursesmanager.repositories.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    PartantRepository partantRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    KafkaCourseProducer kafkaCourseProducer;
    @Mock
    DtosEntitiesMapper mapper;

    DtosEntitiesMapper mapperDtos = Mappers.getMapper(DtosEntitiesMapper.class);

    @InjectMocks
    private CourseServiceImpl courseService;

    private String CoursesJson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CoursesJson = "{\"date\":\"2024-02-25\",\"numero\":1,\"nom\":\"course\",\"partants\":[{\"nom\":\"partant1\",\"numero\":1},{\"nom\":\"partant2\",\"numero\":2},{\"nom\":\"partant3\",\"numero\":3}]}";
    }

    @Test
    void shouldSaveCourse() throws JsonProcessingException {
        CourseEntity cousreEntity = new CourseEntity();
        cousreEntity.setNom("Course");
        cousreEntity.setPartants(getPartant(cousreEntity));

        CourseEntity expectedCousre = new CourseEntity();
        expectedCousre.setNom("Course");
        expectedCousre.setId(1L);
        expectedCousre.setPartants(getPartant(cousreEntity));

        Course course = mapperDtos.getCourseDTOFromCourseEntity(cousreEntity);

        Mockito.when(courseRepository.save(any(CourseEntity.class))).thenReturn(expectedCousre);
        Mockito.when(partantRepository.saveAll(anyList())).thenReturn(expectedCousre.getPartants());
        Mockito.when(mapper.getCourseEntityFromDTO(any(Course.class))).thenReturn(cousreEntity);
        Mockito.when(mapper.getPartantEntityFromDTO(any(Partant.class))).thenReturn(cousreEntity.getPartants().get(0));
        Mockito.when(objectMapper.writeValueAsString(any(CourseEntity.class))).thenReturn(CoursesJson);
        doNothing().when(kafkaCourseProducer).sendMessage(any(), eq(CoursesJson));

        Long idCourse = courseService.saveCourse(course);

        verify(courseRepository,times(1)).save(any(CourseEntity.class));
        verify(partantRepository,times(1)).saveAll(anyList());
        verify(mapper,times(1)).getCourseEntityFromDTO(any(Course.class));
        verify(mapper, times(3)).getPartantEntityFromDTO(any(Partant.class));
        verify(objectMapper,times(1)).writeValueAsString(any(CourseEntity.class));
        verify(kafkaCourseProducer, times(1)).sendMessage(any(), eq(CoursesJson));

        Assertions.assertEquals(expectedCousre.getId(), idCourse);
    }

    private List<PartantEntity> getPartant(CourseEntity courseEntity){
        List<PartantEntity> partantEntities = new ArrayList<>();
        partantEntities.add(new PartantEntity("Partant 1", 1L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 2", 2L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 3", 3L, courseEntity));

        return partantEntities;
    }
}
