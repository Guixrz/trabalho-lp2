package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_oportunidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoOportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_oportunidade")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

}