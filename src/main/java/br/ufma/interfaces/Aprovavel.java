// C:/Dev-Calvet/Java/ProjetoSoloSpring/src/main/java/br/ufma/interfaces/AprovavelService.java
package br.ufma.interfaces;

/**
 * Define um contrato para serviços que gerenciam entidades aprováveis.
 * @param <T> O tipo da entidade que será aprovada/rejeitada (ex: Oportunidade, Inscricao).
 */
public interface Aprovavel<T> {

    /**
     * Aprova uma entidade.
     * @param idEntidade O ID da entidade a ser aprovada.
     * @param idAvaliador O ID do usuário que está realizando a aprovação.
     * @return A entidade atualizada e salva.
     */
    T aprovar(Long idEntidade, Long idAvaliador);

    /**
     * Rejeita uma entidade.
     * @param idEntidade O ID da entidade a ser rejeitada.
     * @param idAvaliador O ID do usuário que está realizando a rejeição.
     * @param motivo O motivo da rejeição.
     * @return A entidade atualizada e salva.
     */
    T rejeitar(Long idEntidade, Long idAvaliador, String motivo);
}