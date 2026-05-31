package interfaces;

import java.util.List;

public interface Repositorio<T,K> {

    void salvar(T entidade);
    T buscar(K chave);
    List<T> listarTodos();
    void remover(K chave);

}
