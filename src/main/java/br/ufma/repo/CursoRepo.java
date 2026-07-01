package br.ufma.repo;

import br.ufma.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {
    Optional<Curso> findByCodigo(int codigo);

    boolean existsByCodigo(int codigo);
}
