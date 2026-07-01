package br.ufma.controllers;

import br.ufma.entidades.VersaoPPC;
import br.ufma.services.PpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/cursos/{cursoId}/versoes")
@RequiredArgsConstructor
public class VersaoPpcController {

    private final PpcService ppcService;

    @PostMapping
    public ResponseEntity<VersaoPPC> cadastrarPPC(@PathVariable Long cursoId, @RequestBody PpcRequest request) {
        VersaoPPC novaVersao = ppcService.cadastrarPPC(
                request.getCoordenadorId(),
                cursoId,
                request.getVersao(),
                request.getCargaHoraria()
        );

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/cursos/{cursoId}/versoes/{versaoId}")
                .buildAndExpand(cursoId, novaVersao.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novaVersao);
    }

    @PutMapping
    public ResponseEntity<VersaoPPC> atualizarPPC(@PathVariable Long cursoId, @RequestBody PpcUpdateRequest request) {
        VersaoPPC versaoAtualizada = ppcService.atualizar(
                cursoId,
                request.getNovaCargaHoraria(),
                request.getNovaVersao(),
                request.getCoordenadorId()
        );
        return ResponseEntity.ok(versaoAtualizada);
    }

    // classes auxiliares para os corpos de requisição

    @lombok.Data
    private static class PpcRequest {
        private Long coordenadorId;
        private String versao;
        private int cargaHoraria;
    }

    @lombok.Data
    private static class PpcUpdateRequest {
        private int novaCargaHoraria;
        private String novaVersao;
        private Long coordenadorId;
    }
}