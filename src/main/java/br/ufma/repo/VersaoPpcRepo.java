package br.ufma.repo;

import br.ufma.entidades.VersaoPPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersaoPpcRepo extends JpaRepository<VersaoPPC, Long> {
    List<VersaoPPC> findByCurso_IdOrderByDataCriacaoDesc(Long cursoId);
}
