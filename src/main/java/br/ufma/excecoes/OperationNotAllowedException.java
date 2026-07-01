package br.ufma.excecoes;

/**
 * Exceção lançada quando um usuário tenta executar uma operação
 * para a qual não tem permissão
 */
public class OperationNotAllowedException extends AppException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
