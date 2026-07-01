package br.ufma.repo;

import br.ufma.entidades.Grupo;
import br.ufma.entidades.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepo extends JpaRepository<Grupo, Long> {
    List<Grupo> findByStatus(Status status);
}
