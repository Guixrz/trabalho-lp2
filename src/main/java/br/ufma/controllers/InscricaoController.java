package br.ufma.controllers;

import br.ufma.entidades.Inscricao;
import br.ufma.services.InscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @PostMapping
    public ResponseEntity<Inscricao> criar(@RequestBody InscricaoRequest request) {
        Inscricao novaInscricao = inscricaoService.criar(
                request.getIdOportunidade(),
                request.getIdDiscente(),
                request.getMotivacao()
        );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaInscricao.getId()).toUri();
        return ResponseEntity.created(uri).body(novaInscricao);
    }

    @GetMapping
    public ResponseEntity<List<Inscricao>> listarTodos() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> buscarPorId(@PathVariable Long id) {
        Inscricao inscricao = inscricaoService.buscarPorId(id);
        return ResponseEntity.ok(inscricao);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Inscricao> aprovar(@PathVariable Long id, @RequestBody AvaliadorRequest request) {
        Inscricao inscricao = inscricaoService.aprovar(id, request.getIdAvaliador());
        return ResponseEntity.ok(inscricao);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Inscricao> rejeitar(@PathVariable Long id, @RequestBody RejeicaoRequest request) {
        Inscricao inscricao = inscricaoService.rejeitar(id, request.getIdAvaliador(), request.getMotivo());
        return ResponseEntity.ok(inscricao);
    }

    @PutMapping("/{id}/abandonar")
    public ResponseEntity<Inscricao> abandonar(@PathVariable Long id) {
        Inscricao inscricao = inscricaoService.abandonar(id);
        return ResponseEntity.ok(inscricao);
    }

    @PutMapping("/{id}/reenviar")
    public ResponseEntity<Inscricao> reenviar(@PathVariable Long id, @RequestBody ReenvioRequest request) {
        Inscricao inscricao = inscricaoService.reenviar(id, request.getNovaMotivacao());
        return ResponseEntity.ok(inscricao);
    }

    // classes auxiliares para os corpos das requisições

    @lombok.Data
    private static class InscricaoRequest {
        private Long idOportunidade;
        private Long idDiscente;
        private String motivacao;
    }

    @lombok.Data
    private static class AvaliadorRequest {
        private Long idAvaliador;
    }

    @lombok.Data
    private static class RejeicaoRequest {
        private Long idAvaliador;
        private String motivo;
    }

    @lombok.Data
    private static class ReenvioRequest {
        private String novaMotivacao;
    }
}