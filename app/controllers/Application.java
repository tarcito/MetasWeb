package controllers;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
	private static Calendar atual;
	
	@Transactional  //nescessario para metodos que usem o BD
    public static Result index() {
		iniciaDate();
    	List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
    	Collections.sort(metas);
    	return ok(index.render(metas,getDate(), getDateMax()));
    }

	@Transactional
    public static Result criaMeta(){
    	DynamicForm form = Form.form().bindFromRequest();
    	String descricao = form.get("descricao");
    	int prioridade = Integer.parseInt(form.get("prioridade")); 
    	String[] prazo = form.get("prazo").split("-");
    	int dia = Integer.parseInt(prazo[2]);
    	int mes = Integer.parseInt(prazo[1]);
    	int ano = Integer.parseInt(prazo[0]);
    	Meta meta = new Meta(descricao,prioridade);
    	meta.setPrazo(dia, mes, ano);
    	 dao.persist(meta);
    	 dao.flush();
    	return index();
    }
	
	private static void iniciaDate(){
		atual=(GregorianCalendar) GregorianCalendar.getInstance();
		atual.setFirstDayOfWeek(Calendar.SUNDAY);
	}
	
    private static String getDate(){
        String date= dateToString();
    	return date;  
            
      }  
    private static String getDateMax(){
    	atual.add(Calendar.WEEK_OF_YEAR, +6);
    	String date= dateToString();
    	atual.add(Calendar.WEEK_OF_YEAR, -6);
    	return date;
    }
    
    private static String dateToString(){
    	String dezenaDia = "", dezenaMes ="";
    	if (atual.get(GregorianCalendar.MONTH) <9){
    		dezenaMes = "0";
    	}
    	if(atual.get(GregorianCalendar.DATE) <10){
    		dezenaDia = "0";
    	}
    	String date = (atual.get(GregorianCalendar.YEAR)+"-"+ dezenaMes + (atual.get(GregorianCalendar.MONTH)+1)+"-"+ dezenaDia +(atual.get(GregorianCalendar.DATE)));
    	return date;
    }
}
