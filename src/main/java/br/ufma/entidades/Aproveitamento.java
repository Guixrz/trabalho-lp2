package br.ufma.entidades;

import br.ufma.entidades.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="aproveitamento")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Aproveitamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_aproveitamento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_discente")
    private Discente discente;

    private String descricao;

    @Column(name="nome_instituicao")
    private String instituicao;

    @Column(name = "horas_aproveitamento")
    private int horas;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_avaliador")
    private Usuarios avaliador;

    private String motivo_rejeicao;

    @Column(name="data_limite")
    private LocalDate dataLimite;
}