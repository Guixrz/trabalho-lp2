package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="discente_diretor")
@PrimaryKeyJoinColumn(name="id_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscenteDiretor extends Discente {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "diretoria_grupo",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_grupo")
    )
    private Set<Grupo> grupos = new HashSet<>();

    @Column(name="cargo")
    private String cargo;

    @Column(name="data_inicio")
    private LocalDate dataInicio;

    @Column(name="data_final")
    private LocalDate dataFim;

}