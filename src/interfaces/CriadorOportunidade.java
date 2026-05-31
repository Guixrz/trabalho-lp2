package interfaces;

import entidades.Docente;
import entidades.Oportunidade;
import entidades.Usuarios;
import entidades.enums.Modalidade;
import entidades.enums.Status;
import entidades.enums.Tipo;

import java.time.LocalDateTime;

public interface CriadorOportunidade {
    Oportunidade criarOportunidade(String titulo, String descricao,
                                   Tipo tipo, Modalidade modalidade,
                                   int cargaHoraria, int vagas,
                                   Status status, LocalDateTime inicio,
                                   Usuarios autor, Docente responsavel);

}
