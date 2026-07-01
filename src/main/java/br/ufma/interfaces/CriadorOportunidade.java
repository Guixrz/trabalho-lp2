package br.ufma.interfaces;

import br.ufma.entidades.Docente;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;

import java.time.LocalDateTime;

public interface CriadorOportunidade {
    Oportunidade criarOportunidade(String titulo, String descricao,
                                   Long idTipoOportunidade, Modalidade modalidade,
                                   int cargaHoraria, int vagas,
                                   Status status, LocalDateTime inicio,
                                   Usuarios autor, Docente responsavel);
}
