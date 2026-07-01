package br.ufma.entidades;

import br.ufma.entidades.enums.PapelCargo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="historico_cargo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoCargo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_historico_cargo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name="papel_cargo")
    private PapelCargo papelCargo;

    @Column(name="data_atribuicao_cargo")
    private LocalDate dataAtribuicao;

    @Column(name="data_remocao_cargo")
    private LocalDate dataRemocao;

    private String motivo;

}