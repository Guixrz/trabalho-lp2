package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="versao_PPC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VersaoPPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_versao_ppc")
    private Long id;

    @Column(name="versao")
    private String versao;

    @Column(name="carga_horaria_versao")
    private int cargaHoraria;

    @Column(name="data_criacao")
    private LocalDate dataCriacao;

    @ManyToOne
    private Coordenador coordenador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;
}