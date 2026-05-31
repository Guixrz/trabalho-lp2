package services;

import entidades.Coordenador;
import entidades.Curso;
import entidades.VersaoPPC;
import excecoes.InvalidDataException;
import excecoes.NotFoundException;

import java.time.LocalDate;
import java.util.HashSet;

public class PpcService {

    public void cadastrarPPC(Coordenador coordenador, Curso curso, String versao, int cargaHoraria) {
        if (coordenador == null || curso == null) {
            throw new InvalidDataException("Coordenador ou curso nulo");
        }

        if (!coordenador.isAtivo()) {
            throw new NotFoundException("Coordenador inativo");
        }

        VersaoPPC novoPPC = new VersaoPPC(versao, cargaHoraria, LocalDate.now(), coordenador);
        curso.adicionarVersao(novoPPC);

        System.out.println("PPC cadastrado com sucesso!");
        System.out.println("  Curso: " + curso.getNome());
        System.out.println("  Versão: " + versao);
        System.out.println("  Carga horária: " + cargaHoraria + "h");
    }

    public void atualizar(Curso curso, int novaCargaHoraria, String novaVersao, Coordenador coordenador) {
        if (coordenador == null|| curso == null) {
            throw new InvalidDataException("Coordenador ou curso nulo");
        }
        if (!coordenador.isAtivo()) {
            throw new NotFoundException("Coordenador inativo");
        }
        VersaoPPC novaVersaoPPC = new VersaoPPC(novaVersao, novaCargaHoraria, LocalDate.now(), coordenador);
        curso.adicionarVersao(novaVersaoPPC);
        System.out.println("PPC do curso '" + curso.getNome()
                + "' atualizado para versão " + novaVersao);
    }

    public void exibir(Curso curso) {
        System.out.println("curso: "  + curso.getNome());
        System.out.println("carga:  " + curso.getCargaHoraria() + "h");
        System.out.println("histórico de versões:");
        for (VersaoPPC v : curso.obterHistoricoVersoes()) {
            System.out.println("  - " + v.getVersao() + " (" + v.getCargaHoraria() + "h) - " + v.getDataCriacao());
        }
    }
}