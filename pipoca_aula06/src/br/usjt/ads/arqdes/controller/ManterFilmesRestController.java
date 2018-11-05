package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;

@RestController
public class ManterFilmesRestController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "api/filme")
	public ResponseEntity<List<Filme>> buscarFilmes() {
		try {
			List<Filme> filmes = fService.listarFilmes();
			return new ResponseEntity<List<Filme>>(filmes, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "api/filme/{id}")
	public ResponseEntity<Filme> buscarFilme(@PathVariable("id") Long id) {
		try {
			Filme filme = fService.buscarFilme(id.intValue());
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "api/filme")
	public ResponseEntity<Filme> inserirFilme(@RequestBody Filme filme) {
		try {
			filme = fService.inserirFilme(filme);
			System.out.println(filme);
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.DELETE, value = "api/filme/{id}")
	public ResponseEntity<Long> excluirFilme(@PathVariable("id") Long id) {
		try {
			fService.excluirFilme(id.intValue());
			return new ResponseEntity<Long>(id, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.PUT, value = "api/filme")
	public ResponseEntity<Filme> alterarFilme(@RequestBody Filme filme) {
		try {
			fService.atualizarFilme(filme);
			return new ResponseEntity<Filme>(filme, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "api/genero")
	public ResponseEntity<List<Genero>> buscarGeneros() {
		try {
			List<Genero> generos = gService.listarGeneros();
			return new ResponseEntity<List<Genero>>(generos, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "api/genero/{id}")
	public ResponseEntity<Genero> buscarGenero(@PathVariable("id") Long id) {
		try {
			Genero genero = gService.buscarGenero(id.intValue());
			return new ResponseEntity<Genero>(genero, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}