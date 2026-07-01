package br.ufma.controllers;

import br.ufma.entidades.Docente;
import br.ufma.entidades.Grupo;
import br.ufma.services.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<Grupo>> listarTodos() {
        List<Grupo> grupos = grupoService.listarTodos();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Grupo>> listarAtivos() {
        List<Grupo> grupos = grupoService.listarAtivos();
        return ResponseEntity.ok(grupos);
    }

    @PostMapping
    public ResponseEntity<Grupo> cadastrar(@RequestBody GrupoRequest request) {
        // cria um objeto Docente apenas com o ID para a associação
        Docente responsavel = new Docente();
        responsavel.setId(request.getResponsavelId());

        Grupo novoGrupo = grupoService.cadastrar(
                request.getNome(),
                request.getTipo(),
                request.getEmail(),
                request.getDescricao(),
                responsavel);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoGrupo.getId()).toUri();
        return ResponseEntity.created(uri).body(novoGrupo);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Grupo> aprovarSolicitacao(@PathVariable Long id) {
        Grupo grupo = grupoService.aprovarSolicitacao(id);
        return ResponseEntity.ok(grupo);
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<Grupo> encerrar(@PathVariable Long id) {
        Grupo grupo = grupoService.encerrar(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping("/{id}/membros")
    public ResponseEntity<Grupo> adicionarMembro(@PathVariable Long id, @RequestBody MembroRequest request) {
        Grupo grupo = grupoService.adicionarMembro(id, request.getUsuarioId());
        return ResponseEntity.ok(grupo);
    }

    @DeleteMapping("/{id}/membros/{idUsuario}")
    public ResponseEntity<Grupo> removerMembro(@PathVariable Long id, @PathVariable Long idUsuario) {
        Grupo grupo = grupoService.removerMembro(id, idUsuario);
        return ResponseEntity.ok(grupo);
    }

    // classes auxiliares para o corpo da requisição

    @lombok.Data
    private static class GrupoRequest {
        private String nome;
        private String tipo;
        private String email;
        private String descricao;
        private Long responsavelId; // a requisição enviará o ID do docente responsável
    }

    @lombok.Data
    private static class MembroRequest {
        private Long usuarioId;
    }
}