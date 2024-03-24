package fr.nasser.mvp.coursesmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "partants")
public class PartantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    Long id;

    @JsonProperty("nom")
    String nom;

    @JsonProperty("numero")
    Long numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_course")
    @JsonIgnore
    CourseEntity course;

    public PartantEntity(String nom, Long numero, CourseEntity courseEntity) {
        this.nom=nom;
        this.numero=numero;
        this.course = courseEntity;
    }

    public PartantEntity() {

    }
}
