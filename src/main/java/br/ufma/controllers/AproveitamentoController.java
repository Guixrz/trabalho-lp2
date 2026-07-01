package br.ufma.controllers;

import br.ufma.entidades.Aproveitamento;
import br.ufma.services.AproveitamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/aproveitamentos")
@RequiredArgsConstructor
public class AproveitamentoController {

    private final AproveitamentoService aproveitamentoService;

    @PostMapping
    public ResponseEntity<Aproveitamento> submeter(@RequestBody AproveitamentoRequest request) {
        Aproveitamento novoAproveitamento = aproveitamentoService.submeter(
                request.getIdDiscente(),
                request.getDescricao(),
                request.getInstituicao(),
                request.getHoras(),
                request.getDataLimite()
        );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoAproveitamento.getId()).toUri();
        return ResponseEntity.created(uri).body(novoAproveitamento);
    }

    @GetMapping
    public ResponseEntity<List<Aproveitamento>> listarTodos() {
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarTodos();
        return ResponseEntity.ok(aproveitamentos);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Aproveitamento>> listarPendentes() {
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarPendentes();
        return ResponseEntity.ok(aproveitamentos);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<Aproveitamento>> listarVencidos() {
        List<Aproveitamento> aproveitamentos = aproveitamentoService.listarVencidos();
        return ResponseEntity.ok(aproveitamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aproveitamento> buscarPorId(@PathVariable Long id) {
        Aproveitamento aproveitamento = aproveitamentoService.buscarPorId(id);
        return ResponseEntity.ok(aproveitamento);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovar(@PathVariable Long id, @RequestBody AvaliadorRequest request) {
        aproveitamentoService.aprovar(id, request.getIdAvaliador());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Void> rejeitar(@PathVariable Long id, @RequestBody RejeicaoRequest request) {
        aproveitamentoService.rejeitar(id, request.getIdAvaliador(), request.getMotivo());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reenviar")
    public ResponseEntity<Void> reenviar(@PathVariable Long id) {
        aproveitamentoService.reenviar(id);
        return ResponseEntity.ok().build();
    }

    // classes auxiliares para os corpos das requisições

    @lombok.Data
    private static class AproveitamentoRequest {
        private Long idDiscente;
        private String descricao;
        private String instituicao;
        private int horas;
        private LocalDate dataLimite;
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
}