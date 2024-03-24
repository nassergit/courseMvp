package fr.nasser.mvp.coursesmanager.service;

import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface CourseService {

    /**
     * enregistrer une course avec ses partants en BDD
     * @param course
     * @return id de la course créée
     */
    long saveCourse(Course course);

    /**
     *
     * @return la liste de toutes les courses
     */
    List<Course> getAllCourses();

    /**
     *
     * @param nomCourse
     * @param numCourse
     * @return la course qui a comme nom : nomCourse et numero : numCourse
     */
    Course getCourse(String nomCourse, long numCourse);
}
