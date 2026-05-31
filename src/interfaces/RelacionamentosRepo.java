package interfaces;

import java.util.List;

public interface RelacionamentosRepo<T,O> {

    void salvar(T entidade);
    List<T> buscarPor(O owner);
    List<T> listarTodos();
    void remover(T entidade);

}
