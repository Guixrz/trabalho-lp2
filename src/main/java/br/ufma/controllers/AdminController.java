package br.ufma.controllers;

import br.ufma.entidades.Admin;
import br.ufma.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> salvar(@RequestBody Admin admin) {
        Admin novoAdmin = adminService.salvar(admin);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoAdmin.getId()).toUri();
        return ResponseEntity.created(uri).body(novoAdmin);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> listarTodos() {
        List<Admin> admins = adminService.listarTodos();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> buscarPorId(@PathVariable Long id) {
        Admin admin = adminService.buscarPorId(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/{adminId}/gestores")
    public ResponseEntity<Void> adicionarGestor(@PathVariable Long adminId, @RequestBody GestorRequest request) {
        adminService.adicionarGestor(adminId, request.getGestorId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{adminId}/gestores/{gestorId}")
    public ResponseEntity<Void> removerGestor(@PathVariable Long adminId, @PathVariable Long gestorId) {
        adminService.removerGestor(adminId, gestorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{adminId}/gestores/{gestorId}")
    public ResponseEntity<Map<String, Boolean>> isGestor(@PathVariable Long adminId, @PathVariable Long gestorId) {
        boolean isGestor = adminService.isGestor(adminId, gestorId);
        return ResponseEntity.ok(Collections.singletonMap("isGestor", isGestor));
    }

    // classe auxiliar para o corpo da requisição

    @lombok.Data
    private static class GestorRequest {
        private Long gestorId;
    }
}