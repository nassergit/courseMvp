package fr.nasser.mvp.coursesmanager.repositories;

import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void whenSave_thenRetrieveCourse() {
        CourseEntity cousre = new CourseEntity();
        cousre.setNom("Course");
        cousre.setPartants(getPartant(cousre));
        CourseEntity savedCousre = courseRepository.save(cousre);

        CourseEntity foundCourseEntity = courseRepository.findById(savedCousre.getId()).orElse(null);

        Assertions.assertThat(foundCourseEntity).isNotNull();
        Assertions.assertThat(foundCourseEntity.getNom()).isEqualTo("Course");
        Assertions.assertThat(foundCourseEntity.getPartants().size()).isEqualTo(3);
    }

    @Test
    public void shouldThrowsDataIntegrityViolationException(){
        LocalDate now = LocalDate.now();

        CourseEntity cousre1 = new CourseEntity();
        cousre1.setNom("Course");
        cousre1.setNumero(1L);
        cousre1.setDate(now);
        cousre1.setPartants(getPartant(cousre1));

        CourseEntity cousre2 = new CourseEntity();
        cousre2.setNom("Course");
        cousre2.setNumero(1L);
        cousre2.setDate(now);
        cousre2.setPartants(getPartant(cousre1));

        CourseEntity savedCousre1 = courseRepository.save(cousre1);

        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            courseRepository.saveAndFlush(cousre2);
        });
    }

    private List<PartantEntity> getPartant(CourseEntity courseEntity){
        List<PartantEntity> partantEntities = new ArrayList<>();
        partantEntities.add(new PartantEntity("Partant 1", 1L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 2", 2L, courseEntity));
        partantEntities.add(new PartantEntity("Partant 3", 3L, courseEntity));

        return partantEntities;
    }
}
