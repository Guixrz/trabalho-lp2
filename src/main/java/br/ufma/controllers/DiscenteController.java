package br.ufma.controllers;

import br.ufma.entidades.Discente;
import br.ufma.services.DiscenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/discentes")
@RequiredArgsConstructor
public class DiscenteController {

    private final DiscenteService discenteService;

    @PostMapping
    public ResponseEntity<Discente> salvar(@RequestBody DiscenteRequest request) {
        // converte para entidade
        Discente discente = new Discente();
        discente.setNome(request.getNome());
        discente.setEmail(request.getEmail());

        Discente novoDiscente = discenteService.salvar(discente, request.getCursoId());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoDiscente.getId()).toUri();
        return ResponseEntity.created(uri).body(novoDiscente);
    }

    @GetMapping
    public ResponseEntity<List<Discente>> listarTodos() {
        List<Discente> discentes = discenteService.listarTodos();
        return ResponseEntity.ok(discentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discente> buscarPorId(@PathVariable Long id) {
        Discente discente = discenteService.buscarPorId(id);
        return ResponseEntity.ok(discente);
    }

    @GetMapping("/{id}/relatorio-horas")
    public ResponseEntity<Map<String, Object>> gerarRelatorioDeHoras(@PathVariable Long id) {
        Map<String, Object> relatorio = discenteService.gerarRelatorioDeHoras(id);
        return ResponseEntity.ok(relatorio);
    }

    // classes auxiliares para os corpos das requisições

    @lombok.Data
    private static class DiscenteRequest {
        private String nome;
        private String email;
        private Long cursoId;
        // espaço para por outros atributos necessarios
    }
}