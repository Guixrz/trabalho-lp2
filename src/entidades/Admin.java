package entidades;

import entidades.enums.PapelSistema;

import java.util.HashSet;

public class Admin extends Usuarios {

    private HashSet<Usuarios> gestores;

    public Admin(String nome, String email, String senha, PapelSistema papelSistema, HashSet<Usuarios> gestoresIniciais) {
        super(nome, email, senha, papelSistema);

        if (gestoresIniciais != null && !gestoresIniciais.isEmpty()) {
            this.gestores = new HashSet<>(gestoresIniciais);
        } else {
            this.gestores = new HashSet<>();
        }
    }

    public Admin(String nome, String email, String senha, PapelSistema papelSistema) {
        super(nome, email, senha, papelSistema);
        this.gestores = new HashSet<>();
    }

    public void cadastroGestores(HashSet<Usuarios> novosGestores) {
        if (novosGestores == null || novosGestores.isEmpty()) {
            System.out.println("Nenhum gestor informado.");
            return;
        }

        for (Usuarios u : novosGestores) {
            if (u == null) continue;

            u.setAtivo(true);

            boolean adicionado = this.gestores.add(u);

            if (adicionado) {
                System.out.println("Gestor cadastrado: " + u.getNome());
            } else {
                System.out.println("Gestor já existia: " + u.getNome());
            }
        }
    }

    public void adicionarGestor(Usuarios gestor) {
        if (gestor == null) {
            System.out.println("Gestor nulo.");
            return;
        }

        gestor.setAtivo(true);
        if (this.gestores.add(gestor)) {
            System.out.println("Gestor '" + gestor.getNome() + "' adicionado.");
        }
    }

    public void removerGestor(Usuarios gestor) {
        if (gestor == null) return;

        if (this.gestores.remove(gestor)) {
            System.out.println("Gestor '" + gestor.getNome() + "' removido.");
        }
    }

    public HashSet<Usuarios> getGestores() {
        return new HashSet<>(this.gestores);
    }

    public boolean isGestor(Usuarios usuario) {
        return usuario != null && this.gestores.contains(usuario);
    }

    public void listarGestores() {
        if (this.gestores.isEmpty()) {
            System.out.println("Nenhum gestor cadastrado.");
            return;
        }

        System.out.println("--- Gestores Cadastrados ---");
        for (Usuarios gestor : this.gestores) {
            System.out.println("- " + gestor.getNome() + " (" + gestor.getEmail() + ")");
        }
    }

}