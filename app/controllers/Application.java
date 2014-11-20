package controllers;

import java.util.List;

import models.Objetivo;
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
    	List<Objetivo> objetivos = dao.findAllByClassName(Objetivo.class.getName());
        return ok(index.render(objetivos));
    }

	@Transactional
    public static Result criaObjetivo(){
    	DynamicForm form = Form.form().bindFromRequest();
    	String obj = form.get("objetivo");
    	Objetivo objetivo = new Objetivo(obj);
    	 dao.persist(objetivo);
    	return index();
    }
    
}
