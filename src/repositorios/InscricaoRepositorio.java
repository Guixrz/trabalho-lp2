package repositorios;

import entidades.Discente;
import entidades.Inscricao;
import entidades.Oportunidade;
import excecoes.InvalidDataException;
import interfaces.RelacionamentosRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscricaoRepositorio implements RelacionamentosRepo<Inscricao, Discente> {

    private Map<Discente, List<Inscricao>> inscricoesPorDiscente = new HashMap<>();
    private List<Inscricao> inscricoesTodas = new ArrayList<>();

    @Override
    public void salvar(Inscricao inscricao) {
        if (inscricao == null) throw new InvalidDataException("Inscrição nula");
        if (inscricao.getDiscente() == null) throw new InvalidDataException("Discente da inscrição nulo");
        Discente discente = inscricao.getDiscente();
        List<Inscricao> listasDoDiscente = inscricoesPorDiscente.computeIfAbsent(discente, k -> new ArrayList<>());
        listasDoDiscente.add(inscricao);
        inscricoesTodas.add(inscricao);
    }

    @Override
    public List<Inscricao> buscarPor(Discente discente) {
        if (discente == null) throw new InvalidDataException("Discente nulo");
        List<Inscricao> resultado = inscricoesPorDiscente.get(discente);
        if (resultado != null) {
            return new ArrayList<>(resultado);
        } else {
            return new ArrayList<>();
        }
    }

    public Inscricao buscarDiscenteOportunidade(Discente d, Oportunidade o) {
        if (d == null || o == null) return null;
        List<Inscricao> lista = inscricoesPorDiscente.get(d);
        if (lista == null) return null;
        for (Inscricao i : lista) {
            if (i.getOportunidade().equals(o)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Inscricao> listarTodos() {
        return new ArrayList<>(inscricoesTodas);
    }

    @Override
    public void remover(Inscricao inscricao) {
        if (inscricao == null || inscricao.getDiscente() == null) return;
        Discente discente = inscricao.getDiscente();
        List<Inscricao> lista = inscricoesPorDiscente.get(discente);
        if (lista != null) {
            lista.remove(inscricao);
            if (lista.isEmpty()) {
                inscricoesPorDiscente.remove(discente);
            }
        }
        inscricoesTodas.remove(inscricao);
    }
}