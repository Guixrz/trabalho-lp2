package entidades;

import entidades.enums.PapelCargo;
import entidades.enums.Status;
import excecoes.InvalidDataException;
import excecoes.OperationNotAllowedException;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Grupo {

    private String nome;
    private String tipo;
    private String email;
    private String descricao;
    private Status status;
    private Docente responsavel;
    private String id;

    private Set<Usuarios> membros = new HashSet<>();

    private Map<Usuarios, List<HistoricoCargo>> historicoCargosPorUsuario = new HashMap<>();

    public Grupo(String nome, String tipo, String email, String descricao, Status status, Docente responsavel) {
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
        this.id = "GRP_" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }
   public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Docente getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Docente responsavel) {
        this.responsavel = responsavel;
    }

    public Set<Usuarios> getMembros() {
        return new HashSet<>(membros);
    }

    public boolean adicionarMembro(Usuarios usuario) {
        if (usuario == null) {
            throw new InvalidDataException("usuario nulo");
        }

        if (membros.contains(usuario)) {
            throw new OperationNotAllowedException("Usuário '\" + usuario.getNome() + \"' já é membro do grupo.");
        }

        boolean adicionado = membros.add(usuario);
        if (adicionado) {
            System.out.println("Usuário '" + usuario.getNome() + "' adicionado ao grupo '" + nome + "'");
        }
        return adicionado;
    }

    public boolean removerMembro(Usuarios usuario) {
        if (usuario == null) {
            throw new InvalidDataException("usuario nulo");
        };

        boolean removido = membros.remove(usuario);
        if (removido) {
            System.out.println("Usuário '" + usuario.getNome() + "' removido do grupo '" + nome + "'");
        }
        return removido;
    }

    public boolean isMembro(Usuarios usuario) {
        return usuario != null && membros.contains(usuario);
    }

    public void adicionarHistoricoCargo(Usuarios usuario, HistoricoCargo historico) {
        if (usuario == null || historico == null) {
            throw new InvalidDataException("usuário ou histórico nulo");
        }

        List<HistoricoCargo> lista = historicoCargosPorUsuario
                .computeIfAbsent(usuario, k -> new ArrayList<>());

        lista.add(historico);
        System.out.println("Histórico de cargo registrado para " + usuario.getNome());
    }

    public List<HistoricoCargo> obterHistoricoCargo(Usuarios usuario) {
        if (usuario == null) return new ArrayList<>();

        List<HistoricoCargo> lista = historicoCargosPorUsuario.get(usuario);
        return lista != null ? new ArrayList<>(lista) : new ArrayList<>();
    }

   public Map<Usuarios, List<HistoricoCargo>> obterTodoHistoricoCargo() {
        return new HashMap<>(historicoCargosPorUsuario);
    }

   public HistoricoCargo obterCargoAtual(Usuarios usuario) {
        List<HistoricoCargo> lista = historicoCargosPorUsuario.get(usuario);
        if (lista == null || lista.isEmpty()) return null;

        for (int i = lista.size() - 1; i >= 0; i--) {
            if (lista.get(i).getDataRemocao() == null) {
                return lista.get(i);
            }
        }
        return null;
    }

}