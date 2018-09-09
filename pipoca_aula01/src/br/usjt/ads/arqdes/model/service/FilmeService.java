package br.usjt.ads.arqdes.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.ads.arqdes.model.dao.FilmeDAO;
import br.usjt.ads.arqdes.model.entity.Filme;

public class FilmeService {
	private FilmeDAO dao;
	private Filme filme;
	
	public FilmeService() {
		dao = new FilmeDAO();
	}
	
	public ArrayList<Filme> listarFilme(int id) throws IOException{
		return dao.listarFilme(id);
	}
	
	public ArrayList<Filme> listarFilme() throws IOException{
		return dao.listarFilme();
	}
	
	public Filme inserirFilme(Filme filme) throws IOException {
		int id = dao.inserirFilme(filme);
		filme.setId(id);
		return filme;
	}
	
	public void atualizar(Filme filme) throws IOException, SQLException{
		dao.atualizar(filme);
	}
	
	public void excluir(int id) throws IOException, SQLException {
		dao.excluir(id);
	}

}
