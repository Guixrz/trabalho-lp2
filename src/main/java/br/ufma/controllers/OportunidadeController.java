package br.ufma.controllers;

import br.ufma.entidades.Oportunidade;
import br.ufma.entidades.TipoOportunidade;
import br.ufma.entidades.enums.Modalidade;
import br.ufma.services.OportunidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/oportunidades")
@RequiredArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    @PostMapping
    public ResponseEntity<Oportunidade> criarOportunidade(@RequestBody OportunidadeRequest request) {
        Oportunidade novaOportunidade = oportunidadeService.criarOportunidade(
                request.getIdDocenteResponsavel(),
                request.getTitulo(),
                request.getDescricao(),
                request.getTipo(),
                request.getModalidade(),
                request.getCargaHoraria(),
                request.getVagas(),
                request.getInicio(),
                request.getIdAutor()
        );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaOportunidade.getId()).toUri();
        return ResponseEntity.created(uri).body(novaOportunidade);
    }

    @GetMapping
    public ResponseEntity<List<Oportunidade>> listarOportunidades() {
        List<Oportunidade> oportunidades = oportunidadeService.listarTodas();
        return ResponseEntity.ok(oportunidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscarOportunidadePorId(@PathVariable Long id) {
        Oportunidade oportunidade = oportunidadeService.findById(id);
        return ResponseEntity.ok(oportunidade);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Oportunidade> aprovarOportunidade(@PathVariable Long id, @RequestBody AvaliadorRequest request) {
        Oportunidade oportunidade = oportunidadeService.aprovar(id, request.getIdAvaliador());
        return ResponseEntity.ok(oportunidade);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Oportunidade> rejeitarOportunidade(@PathVariable Long id, @RequestBody RejeicaoRequest request) {
        Oportunidade oportunidade = oportunidadeService.rejeitar(id, request.getIdAvaliador(), request.getMotivo());
        return ResponseEntity.ok(oportunidade);
    }

    @PutMapping("/{id}/plano-atividade")
    public ResponseEntity<Void> registrarPlanoAtividade(@PathVariable Long id, @RequestBody PlanoAtividadeRequest request) {
        oportunidadeService.registrarPlanoAtividade(id, request.getDataInicio());
        return ResponseEntity.ok().build();
    }

    // classes auxiliares para os corpos das requisições

    @lombok.Data
    private static class OportunidadeRequest {
        private Long idDocenteResponsavel;
        private String titulo;
        private String descricao;
        private TipoOportunidade tipo;
        private Modalidade modalidade;
        private int cargaHoraria;
        private int vagas;
        private LocalDateTime inicio;
        private Long idAutor;
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
    private static class PlanoAtividadeRequest {
        private LocalDate dataInicio;
    }
}