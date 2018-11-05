package br.usjt.ads.arqdes.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.ads.arqdes.model.entity.Filme;
import br.usjt.ads.arqdes.model.entity.Genero;
import br.usjt.ads.arqdes.model.entity.Usuario;
import br.usjt.ads.arqdes.model.service.FilmeService;
import br.usjt.ads.arqdes.model.service.GeneroService;
import br.usjt.ads.arqdes.model.service.LoginService;

@Controller
public class ManterFilmesController {
	@Autowired
	private FilmeService fService;
	@Autowired
	private GeneroService gService;
	@Autowired
	private LoginService lService;

	// Formatação de Datas
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	// Request Padrao
	@RequestMapping("/")
	public String inicio() {
		return "index";
	}

	@RequestMapping("/inicio")
	public String inicio1() {
		return "index";
	}

	// Request Login - TODO
	// Faz Login
	@RequestMapping("/fazer_login")
	public String fazerLogin(HttpSession session, Usuario usuario) {
		try {
			usuario = lService.login(usuario);
			session.setAttribute("usuario", usuario);
			if (usuario != null) {
				return "redirect:administracao";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:inicio";
	}

	// Faz Logout
	@RequestMapping("/fazer_logout")
	public String deslogar(HttpSession session) {
		session.removeAttribute("usuario");
		return "redirect:inicio";
	}

	@RequestMapping("/listar_filmes")
	public String listarFilmes(HttpSession session) {
		session.setAttribute("lista", null);
		return "ListarFilmes";
	}

	@RequestMapping("/novo_filme")
	public String novoFilme(HttpSession session) {
		try {
			List<Genero> generos = gService.listarGeneros();
			session.setAttribute("generos", generos);
			return "CriarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/inserir_filme")
	public String inserirFilme(@Valid Filme filme, BindingResult result, Model model) {
		try {
			if (!result.hasFieldErrors("titulo")) {
				Genero genero = gService.buscarGenero(filme.getGenero().getId());
				filme.setGenero(genero);
				model.addAttribute("filme", filme);
				fService.inserirFilme(filme);
				return "VisualizarFilme";
			} else {
				return "CriarFilme";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping("/visualizar_filme")
	public String visualizarFilme(HttpSession session, @RequestParam String id) {
		try {
			Filme verFilme = fService.buscarFilme(Integer.parseInt(id));
			session.setAttribute("filme", verFilme);
			return "VisualizarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "ListarFilmes";
	}

	@RequestMapping("/excluir_filme")
	public String excluirFilme(Model model, @RequestParam String id) {
		try {
			fService.excluirFilme(Integer.parseInt(id));
			model.addAttribute("chave", "");
			return "redirect:buscar_filmes";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro";
	}

	@RequestMapping("/editar_filme")
	public String editarFilme(Model model, @RequestParam String id) {
		try {
			List<Genero> generos = gService.listarGeneros();
			model.addAttribute("generos", generos);
			model.addAttribute("filme", fService.buscarFilme(Integer.parseInt(id)));
			return "AlterarFilme";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro";
	}

	@RequestMapping("/buscar_filmes")
	public String buscarFilmes(HttpSession session, @RequestParam String chave) {
		try {
			List<Filme> lista;

			if (chave != null && chave.length() > 0)
				lista = fService.listarFilmes(chave);
			else
				lista = fService.listarFilmes();

			session.setAttribute("lista", lista);
			return "ListarFilmes";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}

	@RequestMapping("/ordenar_genero")
	public String listarGenero(Model model) {
		try {
			fService = new FilmeService();
			List<Filme> ordemGenero;
			ordemGenero = fService.ordenarGeneros();
			model.addAttribute("lista", ordemGenero);
			return "ListarPorGenero";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}
	
	@RequestMapping("/ordenar_popularidade")
	public String listarPopularidade(Model model) {
		try {
			fService = new FilmeService();
			List<Filme> ordemPopularidade;
			ordemPopularidade = fService.ordenarGeneros();
			model.addAttribute("lista", ordemPopularidade);
			return "ListarPorPopularidade";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}
	
	@RequestMapping("/ordenar_data")
	public String listarLancamento(Model model) {
		try {
			fService = new FilmeService();
			List<Filme> lista5;
			lista5 = fService.ordenarData();
			

			Filme filmeMes = null;
			Filme filmeAno = null;
			
			ArrayList<Filme> listaAno = new ArrayList<Filme>();
			ArrayList<Filme> listaMes = new ArrayList<Filme>();
			
			Date data = new Date();
			Date dataBanco = new Date();
			Calendar c = Calendar.getInstance();
			Calendar c3 = Calendar.getInstance();
			Calendar c4 = Calendar.getInstance();
			DateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");

			int i = 0;
		    for(Filme a: lista5) 
		    {
		    	
		    	try {
					String dataString = formatter2.format((a.getDataLancamento()));
					dataBanco = (Date)formatter2.parse(dataString);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	c.setTime(dataBanco);
		    
		    	Format format1 = new SimpleDateFormat("MM");
		    	Format format2 = new SimpleDateFormat("yyyy");
		    	Format format3 = new SimpleDateFormat("dd");
		    	int data01 = Integer.parseInt(format1.format(c.getTime()));
		    	int data02 = Integer.parseInt(format2.format(c.getTime()));
		    	int data03 = Integer.parseInt(format3.format(c.getTime()));
		    	
			    // formata e exibe a data e hora
		    	c4.setTime(data);
		    	
			    Format format4 = new SimpleDateFormat("MM");
			    Format format5 = new SimpleDateFormat("yyyy");
			    Format format6 = new SimpleDateFormat("dd");
			    int data04 = Integer.parseInt(format4.format(c4.getTime()));
			    int data05 = Integer.parseInt(format5.format(c4.getTime()));
			    int data06 = Integer.parseInt(format6.format(c4.getTime()));
			    
				 
			    
			    Calendar c1 = Calendar.getInstance();
			    c1.set(Calendar.MONTH, data01);
			    c1.set(Calendar.YEAR, data02);
			    c1.set(Calendar.DAY_OF_MONTH, data03);

			    Calendar c2 = Calendar.getInstance();
			    c2.set(Calendar.MONTH, data04);
			    c2.set(Calendar.YEAR, data05);
			    c2.set(Calendar.DAY_OF_MONTH, data06);
			    
			    long diferenca = c2.getTime().getTime() - c1.getTime().getTime();
			    if(diferenca <= 31){
					filmeMes = lista5.get(i);
					listaMes.add(filmeMes);
				}
				
				if(diferenca >= 365){
					filmeAno = lista5.get(i);
					listaAno.add(filmeAno);
				}
			i++;
		    }
		    model.addAttribute("listaMes", listaMes);
		    model.addAttribute("listaAno", listaAno);
			model.addAttribute("lista", lista5);
			return "ListarPorData";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}

	@RequestMapping("/alterar_filme")
	public String alterarFilme(Filme filme, Model model) {
		System.out.println(filme);
		try {
			Genero genero = gService.buscarGenero(filme.getGenero().getId());
			filme.setGenero(genero);
			System.out.println(filme);
			model.addAttribute("filme", filme);
			fService.atualizarFilme(filme);
			return "VisualizarFilme";

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erro";
	}

	@RequestMapping("/salvar_filme")
	public String salvarFilme(Filme filme, Model model) {
		System.out.println(filme);
		try {
			Genero genero = gService.buscarGenero(filme.getGenero().getId());
			filme.setGenero(genero);
			System.out.println(filme);
			model.addAttribute("filme", filme);
			fService.atualizarFilme(filme);
			return "VisualizarFilme";

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

}
