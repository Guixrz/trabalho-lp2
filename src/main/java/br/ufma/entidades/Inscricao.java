package br.ufma.entidades;

import br.ufma.entidades.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inscricao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_inscricao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_oportunidade", nullable = false)
    private Oportunidade oportunidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_discente", nullable = false)
    private Discente discente;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private Status status;
    
    private String motivacao;

    @Column(name="data_de_aprovacao")
    private LocalDate dataAprovacao;

    @Column(name="data_de_limite")
    private LocalDate dataLimiteCoord;
    @Column(name="data_de_inscricao")
    private LocalDate dataInscricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_avaliador")
    private Usuarios avaliador;

}