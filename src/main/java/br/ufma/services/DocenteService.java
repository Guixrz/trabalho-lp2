package br.ufma.services;

import br.ufma.repo.DocenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepo docenteRepo;

    // para casos futuros de haver serviços especificos de docente
    // toda a logica de antes demanda de outros serviços
}
