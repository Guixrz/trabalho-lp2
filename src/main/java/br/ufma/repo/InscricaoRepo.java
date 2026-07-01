package br.ufma.repo;

import br.ufma.entidades.Discente;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.Inscricao;
import br.ufma.entidades.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InscricaoRepo extends JpaRepository<Inscricao, Long> {

    Optional<Inscricao> findByDiscenteAndOportunidade(Discente discente, Oportunidade oportunidade);

    @Query("SELECT SUM(o.carga_horaria) FROM Inscricao i JOIN i.oportunidade o " +
           "WHERE i.discente.id = :discenteId AND i.status = :statusInscricao AND o.status = :statusOportunidade")
    Integer sumHorasByDiscenteIdAndStatus(
            @Param("discenteId") Long discenteId,
            @Param("statusInscricao") Status statusInscricao,
            @Param("statusOportunidade") Status statusOportunidade);
}
