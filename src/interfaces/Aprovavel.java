package interfaces;

import entidades.Usuarios;
import entidades.enums.Status;

public interface Aprovavel<T extends Usuarios> {
    void aprovar(T avaliador);
    void rejeitar(T avaliador, String motivo);
    Status getStatus();
}
