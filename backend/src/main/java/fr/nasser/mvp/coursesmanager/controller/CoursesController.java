package fr.nasser.mvp.coursesmanager.controller;

import fr.nasser.mvp.coursesmanager.api.CourseEndPointApi;
import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.api.model.CreateCourse200Response;
import fr.nasser.mvp.coursesmanager.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class CoursesController implements CourseEndPointApi {

    CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService){
        this.courseService = courseService;
    }

    @Override
    public ResponseEntity<CreateCourse200Response> createCourse(Course course){
        long savedCourseId = courseService.saveCourse(course);
        CreateCourse200Response rep = new CreateCourse200Response("La course est créée avec succes, l'ID : "+savedCourseId);
        return new ResponseEntity<>(rep, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Course> getCourse(String nomCourse,Integer numCourse){
        return new ResponseEntity<>(courseService.getCourse(nomCourse, numCourse),HttpStatus.OK);
    }

}
