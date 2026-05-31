package entidades;

import entidades.enums.Status;
import java.util.Deque;
import java.util.LinkedList;
import java.util.HashSet;

public class Curso {

    private String nome;
    private int codigo;
    private int cargaHoraria;

    private Deque<VersaoPPC> historicoVersoes = new LinkedList<>();

    public Curso(String nome, int codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public Curso(String nome, int codigo, int cargaHoraria, Deque<VersaoPPC> historicoVersoes) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        if (historicoVersoes != null) {
            this.historicoVersoes = new LinkedList<>(historicoVersoes);
        }
    }

    public void adicionarVersao(VersaoPPC versao) {
        if (versao == null) {
            System.out.println("versão nula");
            return;
        }

        this.historicoVersoes.addLast(versao);
        this.cargaHoraria = versao.getCargaHoraria();
        System.out.println("Versão '" + versao.getVersao() + "' adicionada ao curso '" + nome + "'");
    }

    public VersaoPPC obterVersaoAtual() {
        return historicoVersoes.isEmpty() ? null : historicoVersoes.getLast();
    }

    public VersaoPPC obterVersaoAnterior() {
        if (historicoVersoes.size() < 2) return null;

        LinkedList<VersaoPPC> temp = new LinkedList<>(historicoVersoes);
        temp.removeLast();
        return temp.getLast();
    }

    public Deque<VersaoPPC> obterHistoricoVersoes() {
        return new LinkedList<>(historicoVersoes);
    }

    public int getTamanhoHistorico() {
        return historicoVersoes.size();
    }

     public void listarHistorico() {
        if (historicoVersoes.isEmpty()) {
            System.out.println("Nenhuma versão registrada.");
            return;
        }

        System.out.println("--- Histórico de Versões do Curso '" + nome + "' ---");
        int count = 1;
        for (VersaoPPC v : historicoVersoes) {
            System.out.println(count + ". Versão " + v.getVersao()
                    + " (" + v.getCargaHoraria() + "h) - " + v.getDataCriacao());
            count++;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}