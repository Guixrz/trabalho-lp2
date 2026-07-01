package br.ufma.repo;

import br.ufma.entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByEmail(String email);
}
