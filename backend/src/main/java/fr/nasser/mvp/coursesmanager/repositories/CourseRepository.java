package fr.nasser.mvp.coursesmanager.repositories;

import fr.nasser.mvp.coursesmanager.exception.CourseException;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    /**
     * chercher en BDD la course qui a comme nom : nom
     * et numero : numero
     * @param nom
     * @param numero
     * @return CourseEntity
     */
    CourseEntity findByNomAndNumero(String nom, Long numero);
}
