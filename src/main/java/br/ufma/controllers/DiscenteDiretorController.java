package br.ufma.controllers;

import br.ufma.entidades.DiscenteDiretor;
import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.entidades.enums.Status;
import br.ufma.services.DiscenteDiretorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/discentes-diretores")
@RequiredArgsConstructor
public class DiscenteDiretorController {

    private final DiscenteDiretorService discenteDiretorService;

    @PostMapping
    public ResponseEntity<DiscenteDiretor> salvar(@RequestBody DiscenteDiretor discenteDiretor) {
        DiscenteDiretor novo = discenteDiretorService.salvar(discenteDiretor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novo.getId()).toUri();
        return ResponseEntity.created(uri).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<DiscenteDiretor>> listarTodos() {
        List<DiscenteDiretor> lista = discenteDiretorService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscenteDiretor> buscarPorId(@PathVariable Long id) {
        DiscenteDiretor obj = discenteDiretorService.buscarPorId(id);
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/{autorId}/oportunidades")
    public ResponseEntity<Oportunidade> criarOportunidade(
            @PathVariable Long autorId,
            @RequestBody OportunidadeRequest request) {

        Oportunidade novaOportunidade = discenteDiretorService.criarOportunidadePorIds(
                request.getTitulo(),
                request.getDescricao(),
                request.getIdTipoOportunidade(),
                request.getModalidade(),
                request.getCargaHoraria(),
                request.getVagas(),
                request.getStatus(),
                request.getInicio(),
                autorId,
                request.getResponsavelId()
        );

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/oportunidades/{id}")
                .buildAndExpand(novaOportunidade.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novaOportunidade);
    }

    @GetMapping("/{autorId}/oportunidades")
    public ResponseEntity<List<Oportunidade>> listarOportunidadesPorAutor(@PathVariable Long autorId) {
        List<Oportunidade> oportunidades = discenteDiretorService.listarOportunidadesPorAutor(autorId);
        return ResponseEntity.ok(oportunidades);
    }


    // classe auxiliar para o corpo da requisição

    @lombok.Data
    private static class OportunidadeRequest {
        private String titulo;
        private String descricao;
        private Long idTipoOportunidade;
        private Modalidade modalidade;
        private int cargaHoraria;
        private int vagas;
        private Status status;
        private LocalDateTime inicio;
        private Long responsavelId;
    }
}