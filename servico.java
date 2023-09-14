import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.chuteiraDAO;
import modelo.chuteira;
import spark.Request;
import spark.Response;

public class sevico {
	private chuteiraDAO chuteiraDAO = new chuteiraDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_MARCA = 2;
	private final int FORM_ORDERBY_TAMANHO = 3;
	private final int FORM_ORDERBY_PRECO = 4;
	
	
	public chuteiraService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new chuteira(), FORM_ORDERBY_marca);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new chuteira(), orderBy);
	}

	
	public void makeForm(int tipo, chuteira chuteira, int orderBy) {
		String nomeArquivo = "./src/main/resources/paginazinha.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umachuteira = "";
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/chuteira/";
			String name, marca, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir chuteira";
				marca = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + chuteira.getCodigo();
				name = "Atualizar chuteira (Código " + chuteira.getCodigo() + ")";
				marca = chuteira.getmarca();
				buttonLabel = "Atualizar";
			}
			umachuteira += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umachuteira += "\t<table width=\"80%\" align=\"center\" class=\"principal__form--inserir\">";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td colspan=\"3\" bgcolor=\"\" text-align=\"center\"><font size=\"+2\"><b>" + name + "</b></font></td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t\t<tr class=\"principal__form--marcaes-atributos\">";
			umachuteira += "\t\t\t<td>Código: <input class=\"input--register\" type=\"text\" name=\"codigo\" placeholder=\"Código da chuteira\" value=\""+ chuteira.getCodigo() +"\"></td>";
			umachuteira += "\t\t\t<td>marca: <input class=\"input--register\" type=\"text\" name=\"marca\" placeholder=\"marca da chuteira\" value=\""+ marca +"\"></td>";
			umachuteira += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" placeholder=\"0000\" value=\""+ chuteira.getPreco() +"\"></td>";
			umachuteira += "\t\t\t<td>Tamanho: <input class=\"input--register\" type=\"text\" name=\"tamanho\" placeholder=\"Tamanho da chuteira\" value=\""+ chuteira.getTamanho() +"\"></td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td colspan=\"3\" text-align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button btn btn-danger\"></td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t</table>";
			umachuteira += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umachuteira += "\t<table class=\"principal__tabela--detalhes\" width=\"80%\" bgcolor=\"##ff0000\" align=\"center\">";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>Detalhar chuteira (Código " + chuteira.getCodigo() + ")</b></font></td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td>marca: "+ chuteira.getmarca() +"</td>";
			umachuteira += "\t\t\t<td>Preco: "+ chuteira.getPreco() +"</td>";
			umachuteira += "\t\t\t<td>Tamanho: "+ chuteira.getTamanho() +"</td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t\t<tr>";
			umachuteira += "\t\t\t<td></td>";
			umachuteira += "\t\t</tr>";
			umachuteira += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<!--UMA-chuteira-->", umachuteira);
		
		String list = new String("<table class=\"principal__tabela--chuteiras\" width=\"80%\" align=\"center\" bgcolor=\"#ff0000\">");
		list += "\n<tr><td colspan=\"7\" align=\"center\"><font size=\"+2\"><b>Relação de chuteiras</b></font></td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td align=\"center\"><a href=\"/chuteira/list/" + FORM_ORDERBY_CODIGO + "\"><b><i class=\"fa-solid fa-sort\"></i> Código</b></a></td>\n" +
        		"\t<td align=\"center\"><a href=\"/chuteira/list/" + FORM_ORDERBY_MARCA + "\"><b><i class=\"fa-solid fa-sort\"></i> marca</b></a></td>\n" +
        		"\t<td align=\"center\"><a href=\"/chuteira/list/" + FORM_ORDERBY_TAMANHO + "\"><b><i class=\"fa-solid fa-sort\"></i> Tamanho</b></td>\n" +
        		"\t<td><a href=\"/chuteira/list/" + FORM_ORDERBY_PRECO + "\"><b><i class=\"fa-solid fa-sort\"></i> Preco</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<chuteira> chuteiras;
		if (orderBy == FORM_ORDERBY_CODIGO) {               chuteiras = chuteiraDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_MARCA) {		chuteiras = chuteiraDAO.getOrderBymarca();
		} else if (orderBy == FORM_ORDERBY_TAMANHO) {			chuteiras = chuteiraDAO.getOrderByTamanho();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			chuteiras = chuteiraDAO.getOrderByPreco();
		} else {											chuteiras = chuteiraDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (chuteira p : chuteiras) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td width=\"150\" align=\"center\">" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getmarca() + "</td>\n" +
            		  "\t<td>" + p.getTamanho() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/chuteira/" + p.getCodigo() + "\"><i class=\"fa-solid fa-circle-info\"></i></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/chuteira/update/" + p.getCodigo() + "\"><i class=\"fa-sharp fa-solid fa-pen-to-square\"></i></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/chuteira/delete/" + p.getCodigo() + "\"><i class=\"fa-solid fa-trash\"></i></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<!--LISTAR-chuteira-->", list);		
	}
	
	
	public Object insert(Request request, Response response) {
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		String marca = request.queryParams("marca");
		double preco = Double.parseDouble(request.queryParams("preco"));
		String tamanho = request.queryParams("tamanho");
		
		String resp = "";
		
		chuteira chuteira = new chuteira(codigo, marca, tamanho, preco);
		
		if(chuteiraDAO.insert(chuteira) == true) {
            resp = "chuteira (" + marca + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "chuteira (" + marca + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}

	
	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		chuteira chuteira = (chuteira) chuteiraDAO.get(codigo);
		
		if (chuteira != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, chuteira, FORM_ORDERBY_marca);
        } else {
            response.status(404); // 404 Not found
            String resp = "chuteira " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		chuteira chuteira = (chuteira) chuteiraDAO.get(codigo);
		
		if (chuteira != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, chuteira, FORM_ORDERBY_MARCA);
        } else {
            response.status(404); // 404 Not found
            String resp = "chuteira " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		chuteira chuteira = (chuteira) chuteiraDAO.get(codigo);
        String resp = "";       

        if (chuteira != null) {
        	chuteira.setCodigo(Integer.parseInt(request.queryParams("codigo")));
        	chuteira.setmarca(request.queryParams("marca"));
        	chuteira.setPreco(Double.parseDouble(request.queryParams("preco")));
        	chuteira.setTamanho(request.queryParams("tamanho"));
        	chuteiraDAO.update(chuteira);
        	response.status(200); // success
            resp = "chuteira (Codigo " + chuteira.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "chuteira (Codigo " + chuteira.getCodigo() + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}

	
	public Object delete(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
        chuteira chuteira = (chuteira) chuteiraDAO.get(codigo);
        String resp = "";       

        if (chuteira != null) {
            chuteiraDAO.delete(codigo);
            response.status(200); // success
            resp = "chuteira (" + codigo + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "chuteira (" + codigo + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<!--MENSAGEM-->", "<p>" + resp + "</p>");
	}
}
	
	
