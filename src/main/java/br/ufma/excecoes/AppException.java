package br.ufma.excecoes;

/*
 Classe base abstrata para todas as exceções customizadas da aplicação
 */
public abstract class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
