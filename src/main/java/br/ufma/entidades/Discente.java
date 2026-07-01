package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="discente")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name="id_usuario")
public class Discente extends Usuarios {

    @Column(name="matricula", nullable=false, unique=true)
    private String matricula;

    @Column(name="semestre_atual", nullable=false)
    private int semestreAtual;

    @ManyToOne
    @JoinColumn(name="id_curso")
    private Curso curso;

}