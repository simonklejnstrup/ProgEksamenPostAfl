package kea.eksamen.repository;

import kea.eksamen.model.database.Sogn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SognRepository extends JpaRepository<Sogn, Long> {

    Optional<Sogn> findSognByNavn(String navn);
}
