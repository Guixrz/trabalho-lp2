package br.ufma.repo;

import br.ufma.entidades.HistoricoCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoCargoRepo extends JpaRepository<HistoricoCargo, Long> {
}
