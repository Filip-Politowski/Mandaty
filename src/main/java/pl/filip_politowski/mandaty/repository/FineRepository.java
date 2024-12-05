package pl.filip_politowski.mandaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filip_politowski.mandaty.model.Fine;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsBySignature(String signature);
}
