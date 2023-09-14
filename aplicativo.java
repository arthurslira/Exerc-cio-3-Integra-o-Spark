import static spark.Spark.*;
import service.chuteiraService;
public class aplicativo {
	private static chuteiraService chuteiraService = new chuteiraService();
	
	 public static void main(String[] args) {

		 port(6532);
	        
	        staticFiles.location("/public");
	        
	        post("/chuteira/insert", (request, response) -> chuteiraService.insert(request, response));

	        get("/chuteira/:codigo", (request, response) -> chuteiraService.get(request, response));
	        
	        get("/chuteira/list/:orderby", (request, response) -> chuteiraService.getAll(request, response));

	        get("/chuteira/update/:codigo", (request, response) -> chuteiraService.getToUpdate(request, response));
	        
	        post("/chuteira/update/:codigo", (request, response) -> chuteiraService.update(request, response));
	           
	        get("/chuteira/delete/:codigo", (request, response) -> chuteiraService.delete(request, response));
	       
	    }

}
