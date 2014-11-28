package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class Meta implements Comparable<Meta> {

	// enum das prioridades
	public enum PrioridadeEnum {
		Alto(1), Normal(0),Baixo(-1) ;

		private final int prioridade;

		PrioridadeEnum(int prioridade) {
			this.prioridade = prioridade;
		}
	}

	@Id
	@GeneratedValue
	private Long id;

	private String descricao;
	private PrioridadeEnum prioridade;
	private boolean cumprida;
	private Calendar prazo;
	private int semana = 0;


	public Meta(String descricao, int prioridade) {
		this.descricao = descricao;
		this.prioridade = getPrioridade(prioridade);
		cumprida = false;
	}

	public Meta() {

	}

	public void setPrazo(Calendar prazo) {
		this.prazo = prazo;
	}

	public String getStringPrazo() {
		return getPrazo().get(Calendar.DAY_OF_MONTH) + "/"
				+ (getPrazo().get(Calendar.MONTH) + 1) + "/"
				+ getPrazo().get(Calendar.YEAR);
	}

	public String getDescricao() {
		return descricao;
	}

	public PrioridadeEnum getPrioridade() {
		return prioridade;
	}

	public boolean isCumprida() {
		return cumprida;
	}

	public void cumpriu() {
		this.cumprida = true;
	}

	public Long getId() {
		return id;
	}

	public Calendar getPrazo() {
		return prazo;
	}
	
	public void setSemana(int semana) {
		this.semana = semana;
		
	}

	public int getSemana() {
		return semana;
	}
	

	public int compareTo(Meta outraMeta) {
		int result = comparaSemanaPrazo(outraMeta);
		if (result == 0) {
			result = prioridade.compareTo(outraMeta.getPrioridade());
			if (result == 0) {
				result = getPrazo().compareTo(outraMeta.getPrazo());
			}
		}
		return result;
	}

	private int comparaSemanaPrazo(Meta outraMeta) {
		int result = 0;
		if (getSemana() == outraMeta.getSemana()) {
			result = 0;
		} else if (getSemana() > outraMeta.getSemana()) {
			result = 1;
		} else {
			result = -1;
		}

		return result;
	}

	private PrioridadeEnum getPrioridade(int prioridade) {
		switch (prioridade) {
		case 1:
			return PrioridadeEnum.Alto;
		case -1:
			return PrioridadeEnum.Baixo;
		}
		return PrioridadeEnum.Normal;
	}
	
}
