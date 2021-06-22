package kea.eksamen.repository;

import kea.eksamen.model.database.Kommune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KommuneRepository extends JpaRepository<Kommune, Long> {

    Optional<Kommune> findKommuneByNavn(String navn);

}
