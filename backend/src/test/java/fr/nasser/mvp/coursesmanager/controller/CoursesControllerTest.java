package fr.nasser.mvp.coursesmanager.controller;

import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import fr.nasser.mvp.coursesmanager.service.CourseService;
import fr.nasser.mvp.coursesmanager.service.KafkaCourseConsumer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CoursesController.class)
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    KafkaCourseConsumer kafkaCourseConsumer;

    @Test
    @Disabled
    public void whenCreateUser_thenReturnUser() throws Exception {

        CourseEntity cousre1 = new CourseEntity();
        cousre1.setNom("Course");

        String content = "{\"date\":\"2024-02-25\",\"numero\":1,\"nom\":\"course\",\"partants\":[{\"nom\":\"partant1\",\"numero\":1},{\"nom\":\"partant2\",\"numero\":2},{\"nom\":\"partant3\",\"numero\":3}]}";


        given(courseService.saveCourse(any(Course.class))).willReturn(1L);

        mockMvc.perform(post("/OldMvp/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Course saved with success ID : 1"));
    }

    private List<PartantEntity> getPartant(CourseEntity courseEntity){
        List<PartantEntity> partantEntities = new ArrayList<>();
        partantEntities.add(new PartantEntity("Partant 1", 1L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 2", 2L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 3", 3L, courseEntity));

        return partantEntities;
    }
}
