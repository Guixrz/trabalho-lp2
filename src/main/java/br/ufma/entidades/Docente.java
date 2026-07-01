package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="docente")
@PrimaryKeyJoinColumn(name="id_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docente extends Usuarios {
    @Column(name="siape", nullable=false, unique=true)
    private String siape;
    @Column(name="departamento")
    private String departamento;
    @OneToMany(mappedBy = "usuario")
    private List<HistoricoCargo> historicoCargo = new ArrayList<>();

}
