package br.ufma.excecoes;

/**
 * Exceção lançada quando uma operação viola uma regra de negócio fundamental
 */
public class RegraNegocioException extends AppException {
    public RegraNegocioException(String message) {
        super(message);
    }
}
