package br.ufma.controllers;

import br.ufma.entidades.Curso;
import br.ufma.entidades.VersaoPPC;
import br.ufma.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> salvar(@RequestBody Curso curso) {
        Curso novoCurso = cursoService.salvar(curso);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCurso.getId()).toUri();
        return ResponseEntity.created(uri).body(novoCurso);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        List<Curso> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }

    @GetMapping("/{id}/versoes")
    public ResponseEntity<List<VersaoPPC>> listarHistoricoVersoes(@PathVariable Long id) {
        List<VersaoPPC> versoes = cursoService.listarHistorico(id);
        return ResponseEntity.ok(versoes);
    }

    @GetMapping("/{id}/versoes/atual")
    public ResponseEntity<VersaoPPC> obterVersaoAtual(@PathVariable Long id) {
        VersaoPPC versao = cursoService.obterVersaoAtual(id);
        if (versao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(versao);
    }

    @GetMapping("/{id}/versoes/anterior")
    public ResponseEntity<VersaoPPC> obterVersaoAnterior(@PathVariable Long id) {
        VersaoPPC versao = cursoService.obterVersaoAnterior(id);
        if (versao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(versao);
    }
}