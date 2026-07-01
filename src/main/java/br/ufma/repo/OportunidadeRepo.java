package br.ufma.repo;

import br.ufma.entidades.Docente;
import br.ufma.entidades.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadeRepo extends JpaRepository<Oportunidade, Long> {

    List<Oportunidade> findByAutorId(Long autorId);

}