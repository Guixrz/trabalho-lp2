package br.ufma.repo;

import br.ufma.entidades.DiscenteDiretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscenteDiretorRepo extends JpaRepository<DiscenteDiretor, Long> {
}
