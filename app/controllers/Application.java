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

	@Transactional
	// nescessario para metodos que usem o BD
	public static Result index() {
		iniciaDate();
		List<Meta> metas =  pegaMetasNoBD();
		return ok(index.render(metas, getDate(), getDateMax()));
	}

	@Transactional
	public static Result criaMeta() {
		DynamicForm form = Form.form().bindFromRequest();
		criaMetaNoBD(form);
		return index();
	}
	
	@Transactional
	public static Result concluiMeta(){
		Long id = null;
		DynamicForm form = Form.form().bindFromRequest();
		try{
			id = Long.parseLong(form.get("concluida"));
		}catch(Exception e ){
			return index();
		}
		Meta meta = dao.findByEntityId(Meta.class, id);
		meta.cumpriu();
		dao.flush();
		return index();
	}
	
	@Transactional
	public static Result excluiMeta() {
		DynamicForm form = Form.form().bindFromRequest();
		long id = Long.parseLong(form.get("excluida"));
		dao.removeById(Meta.class, id);
		dao.flush();
		return index();
	}

	@Transactional
	private static List<Meta> pegaMetasNoBD() {
		List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
		Collections.sort(metas);
		return metas;

	}

	@Transactional
	private static void criaMetaNoBD(DynamicForm formulario) {
		String descricao = formulario.get("descricao");
		int prioridade = Integer.parseInt(formulario.get("prioridade"));
		String[] prazo = formulario.get("prazo").split("-");
		int dia = Integer.parseInt(prazo[2]);
		int mes = Integer.parseInt(prazo[1]);
		int ano = Integer.parseInt(prazo[0]);
		Calendar prazoCal = criaDate(dia, mes, ano);
		Meta meta = new Meta(descricao, prioridade);
		meta.setPrazo(prazoCal);
		meta.setSemana(getSemana(prazoCal));
		dao.persist(meta);
		dao.flush();
	}

	private static void iniciaDate() {
		atual = (GregorianCalendar) GregorianCalendar.getInstance();
		atual.setFirstDayOfWeek(Calendar.SUNDAY);
	}

	private static String getDate() {
		String date = dateToString();
		return date;

	}

	private static String getDateMax() {
		atual.add(Calendar.WEEK_OF_YEAR, +6);
		String date = dateToString();
		atual.add(Calendar.WEEK_OF_YEAR, -6);
		return date;
	}

	private static Calendar criaDate(int dia, int mes, int ano) {
		Calendar date = new GregorianCalendar(ano, (mes - 1), dia);
		date.setFirstDayOfWeek(Calendar.SUNDAY);
		return date;
	}

	private static int getSemana(Calendar date) {
		if (date.get(Calendar.WEEK_OF_YEAR) < atual.get(Calendar.WEEK_OF_YEAR)) {
			return date.get(Calendar.WEEK_OF_YEAR)
					+ ( atual.getActualMaximum(Calendar.WEEK_OF_YEAR)  - atual.get(Calendar.WEEK_OF_YEAR));
		}
		return date.get(Calendar.WEEK_OF_YEAR)
				- atual.get(Calendar.WEEK_OF_YEAR);
	}

	private static String dateToString() {
		String dezenaDia = "", dezenaMes = "";
		if (atual.get(GregorianCalendar.MONTH) < 9) {
			dezenaMes = "0";
		}
		if (atual.get(GregorianCalendar.DATE) < 10) {
			dezenaDia = "0";
		}
		String date = (atual.get(GregorianCalendar.YEAR) + "-" + dezenaMes
				+ (atual.get(GregorianCalendar.MONTH) + 1) + "-" + dezenaDia + (atual
				.get(GregorianCalendar.DATE)));
		return date;
	}
}
