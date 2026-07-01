package br.ufma.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "papel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_papel")
    private Long id;

    @Column(name="nome")
    private String nome;

    @ManyToMany(mappedBy = "papelSistema")
    private List<Usuarios> usuarios;

    public Papel(String nome) {
        this.nome = nome;
    }
}