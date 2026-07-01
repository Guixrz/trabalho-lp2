package br.ufma.services;

import br.ufma.entidades.Admin;
import br.ufma.entidades.Usuarios;
import br.ufma.excecoes.NotFoundException;
import br.ufma.repo.AdminRepo;
import br.ufma.repo.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepo adminRepo;
    private final UsuarioRepo usuarioRepo;

    @Transactional
    public Admin salvar(Admin admin) {
        return adminRepo.save(admin);
    }

    public List<Admin> listarTodos() {
        return adminRepo.findAll();
    }

    public Admin buscarPorId(Long id) {
        return adminRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrador com ID " + id + " não encontrado."));
    }

    @Transactional
    public void adicionarGestor(Long adminId, Long gestorId) {
        Admin admin = buscarPorId(adminId);
        Usuarios gestor = findUsuarioById(gestorId);

        gestor.setAtivo(true);
        admin.getGestores().add(gestor);

        adminRepo.save(admin);
    }

    @Transactional
    public void removerGestor(Long adminId, Long gestorId) {
        Admin admin = buscarPorId(adminId);
        Usuarios gestor = findUsuarioById(gestorId);

        admin.getGestores().remove(gestor);

        adminRepo.save(admin);
    }

    public boolean isGestor(Long adminId, Long gestorId) {
        Admin admin = buscarPorId(adminId);
        Usuarios gestor = findUsuarioById(gestorId);
        return admin.getGestores().contains(gestor);
    }

    private Usuarios findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}