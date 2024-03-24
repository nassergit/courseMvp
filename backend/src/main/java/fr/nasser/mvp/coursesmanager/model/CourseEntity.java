package fr.nasser.mvp.coursesmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "nom","numero"})
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    Long id;

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    LocalDate date;

    @Column(name = "nom")
    @JsonProperty("nom")
    String nom;

    @Column(name = "numero")
    @JsonProperty("numero")
    Long numero;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotNull(message = "la liste des partants ne doit pas être nulle")
    @Size(min = 3, message = "Pour chaque course il faut au minimum 3 partants")
    @JsonProperty("partants")
    List<PartantEntity> partants;
}
