package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;

@Service
public class FilmeService {
	@Autowired
	private FilmeDAO dao;
	
	public ArrayList<Filme> listarFilmes(String chave) throws IOException{
		return dao.listarFilmes(chave);
	}

	public ArrayList<Filme> listarFilmes() throws IOException {
		return dao.listarFilmes();
	}

	public Filme buscarFilme(int id) throws IOException {
		return dao.buscarFilme(id);
	}

	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}

	public Filme atualizarFilme(Filme filme) throws IOException {
		dao.atualizarFilme(filme);
		return filme;
	}

	public int excluirFilme(int id) throws IOException {
		dao.excluir(id);
		return id;
	}
	
	public List<Filme> ordenarGeneros() throws IOException {
		return dao.ordenarGeneros();
	}
	
	public List<Filme> ordenarPopularidade() throws IOException {
		return dao.ordenarPopularidade();
	}
	
	public List<Filme> ordenarData() throws IOException {
		return dao.ordenarData();
	}

}
