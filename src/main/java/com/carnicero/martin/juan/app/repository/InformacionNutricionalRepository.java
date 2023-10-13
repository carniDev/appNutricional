package com.carnicero.martin.juan.app.repository;

import com.carnicero.martin.juan.app.model.InformacionNutricionalAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformacionNutricionalRepository extends JpaRepository<InformacionNutricionalAlimento,Long> {

    Optional<InformacionNutricionalAlimento>findByCodigoAlimento(String codigo);
    boolean existsByCodigoAlimento(String codigo);
}
