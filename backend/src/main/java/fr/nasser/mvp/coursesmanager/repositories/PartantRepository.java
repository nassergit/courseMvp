package fr.nasser.mvp.coursesmanager.repositories;

import fr.nasser.mvp.coursesmanager.model.PartantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartantRepository extends JpaRepository<PartantEntity, Long> {
}
