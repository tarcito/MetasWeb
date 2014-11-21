package controllers;

import java.util.List;

import models.Meta;
import models.dao.GenericDAO;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;



public class Application extends Controller {

	private static final GenericDAO dao = new GenericDAO();
	
	@Transactional  //nescessario para metodos que usem o BD
    public static Result index() {
    	List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
        return ok(index.render(metas));
    }

	@Transactional
    public static Result criaMeta(){
    	DynamicForm form = Form.form().bindFromRequest();
    	String descricao = form.get("descricao");
    	int prioridade = Integer.parseInt(form.get("prioridade")); 
    	Meta meta = new Meta(descricao,prioridade);
    	 dao.persist(meta);
    	return index();
    }
    
}
