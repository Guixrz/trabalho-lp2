package br.ufma.repo;

import br.ufma.entidades.TipoOportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoOportunidadeRepo extends JpaRepository<TipoOportunidade, Long> {
}
