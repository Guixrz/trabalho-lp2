package br.ufma.entidades;

import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="oportunidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oportunidade {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_oportunidade")
    private Long id;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_tipo_oportunidade", nullable = false)
    private TipoOportunidade tipo;

    @Enumerated(EnumType.STRING)
    private Modalidade modalidade;

    @Column(name="Carga_horaria")
    private int carga_horaria;

    @Column(name="vagas")
    private int vagas;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="data_inicio")
    private LocalDateTime inicio;

    @Column(name="data_fim")
    private LocalDateTime fim;

    @ManyToOne
    @JoinColumn(name = "id_usuario_autor")
    private Usuarios autor;

    @ManyToOne
    @JoinColumn(name = "id_docente_responsavel")
    private Docente responsavel;

    @ManyToOne
    @JoinColumn(name = "id_usuario_avaliador")
    private Usuarios avaliador;

    private String motivoRejeicao;

}
