package br.ufma.controllers;

import br.ufma.entidades.Papel;
import br.ufma.entidades.Usuarios;
import br.ufma.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarTodos() {
        List<Usuarios> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarPorId(@PathVariable Long id) {
        Usuarios usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuarios> cadastrar(@RequestBody Usuarios usuario) {
        Usuarios novoUsuario = usuarioService.cadastrar(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuarios> autenticar(@RequestBody LoginRequest request) {
        Usuarios usuario = usuarioService.autenticar(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        Usuarios usuario = usuarioService.findById(id);
        usuarioService.desativar(usuario.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/papeis")
    public ResponseEntity<Void> atribuirPapel(@PathVariable Long id, @RequestBody Papel papel) {
        usuarioService.atribuirPapel(id, papel);
        return ResponseEntity.ok().build();
    }

    // classe auxiliar para o corpo da requisição de login
    @lombok.Data
    private static class LoginRequest {
        private String email;
        private String senha;
    }
}