package br.ufma.services;

import br.ufma.entidades.Grupo;
import br.ufma.entidades.HistoricoCargo;
import br.ufma.entidades.Usuarios;
import br.ufma.entidades.enums.PapelCargo;
import br.ufma.repo.HistoricoCargoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistroCargoService {

    private final HistoricoCargoRepo historicoCargoRepo;

    @Transactional
    public HistoricoCargo registrarCargo(Usuarios usuario, Grupo grupo, PapelCargo papel, String motivo) {
        HistoricoCargo novoRegistro = HistoricoCargo.builder()
                .usuario(usuario)
                .grupo(grupo)
                .papelCargo(papel)
                .dataAtribuicao(LocalDate.now())
                .motivo(motivo)
                .build();

        return historicoCargoRepo.save(novoRegistro);
    }

    @Transactional
    public void removerCargo(HistoricoCargo registro, String motivo) {
        registro.setDataRemocao(LocalDate.now());
        registro.setMotivo(motivo);
        historicoCargoRepo.save(registro);
    }
}
