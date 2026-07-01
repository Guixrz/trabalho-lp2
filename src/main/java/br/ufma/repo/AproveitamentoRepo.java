package br.ufma.repo;

import br.ufma.entidades.Aproveitamento;
import br.ufma.entidades.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AproveitamentoRepo extends JpaRepository<Aproveitamento, Long> {

    List<Aproveitamento> findByStatus(Status status);

    List<Aproveitamento> findByStatusAndDataLimiteBefore(Status status, LocalDate data);

    @Query("SELECT SUM(a.horas) FROM Aproveitamento a WHERE a.discente.id = :discenteId AND a.status = :status")
    Integer sumHorasByDiscenteIdAndStatus(@Param("discenteId") Long discenteId, @Param("status") Status status);
}
