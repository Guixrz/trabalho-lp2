package repositorios;

import entidades.Oportunidade;
import excecoes.InvalidDataException;
import interfaces.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OportunidadeRepositorio implements Repositorio<Oportunidade, String> {

    private Map<String, Oportunidade> oportunidades = new HashMap<>();

    @Override
    public void salvar(Oportunidade oportunidade) {
        if (oportunidade == null) throw new InvalidDataException("Oportunidade nulo");
        oportunidades.put(oportunidade.getTitulo(), oportunidade);
    }

    @Override
    public Oportunidade buscar(String chave) {
        if (chave == null) throw new InvalidDataException("Chave nula");
        return oportunidades.get(chave);
    }

    @Override
    public List<Oportunidade> listarTodos() {
        return new ArrayList<>(oportunidades.values());
    }

    @Override
    public void remover(String chave) {
        if (chave == null) throw new InvalidDataException("Chave nula");
        oportunidades.remove(chave);
    }
}