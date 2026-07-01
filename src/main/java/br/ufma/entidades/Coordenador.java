package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="coordenador")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name="id_usuario")
public class Coordenador extends Usuarios{

    @ManyToOne
    @JoinColumn(name="id_curso")
    private Curso curso;

}