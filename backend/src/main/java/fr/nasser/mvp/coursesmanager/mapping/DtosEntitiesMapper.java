package fr.nasser.mvp.coursesmanager.mapping;

import fr.nasser.mvp.coursesmanager.api.model.Course;
import fr.nasser.mvp.coursesmanager.api.model.Partant;
import fr.nasser.mvp.coursesmanager.model.CourseEntity;
import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface DtosEntitiesMapper {

    @Mapping(source = "date", target = "date")
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "partants", target = "partants")
    CourseEntity getCourseEntityFromDTO(Course course);

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "nom", target = "nom")
    PartantEntity getPartantEntityFromDTO(Partant partant);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "partants", target = "partants")
    Course getCourseDTOFromCourseEntity(CourseEntity courseEntity);

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "nom", target = "nom")
    Partant getPartantDTOFromPartantEntity(PartantEntity partantEntity);
}
