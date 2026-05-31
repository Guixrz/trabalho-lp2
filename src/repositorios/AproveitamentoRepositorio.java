package repositorios;

import entidades.Aproveitamento;
import excecoes.InvalidDataException;
import interfaces.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AproveitamentoRepositorio implements Repositorio<Aproveitamento, String> {

    private Map<String, Aproveitamento> aproveitamentos = new HashMap<>();

    @Override
    public void salvar(Aproveitamento aproveitamento) {
        if (aproveitamento == null) throw new InvalidDataException("Aproveitamento nula");
        aproveitamentos.put(aproveitamento.getId(), aproveitamento);
    }

    @Override
    public Aproveitamento buscar(String chave) {
        if (chave == null) throw new InvalidDataException("Chave nula");
        // chave = id
        return aproveitamentos.get(chave);
    }

    @Override
    public List<Aproveitamento> listarTodos() {
        return new ArrayList<>(aproveitamentos.values());
    }

    @Override
    public void remover(String chave) {
        if (chave == null) throw new InvalidDataException("Chave nula");
        aproveitamentos.remove(chave);
    }
}