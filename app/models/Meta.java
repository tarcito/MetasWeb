package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class Meta {
	
	//enum das prioridades
	public enum PrioridadeEnum {
		BAIXO(-1),Normal(0),ALTO(1);
			
		private final int prioridade;
		PrioridadeEnum(int prioridade){
			this.prioridade = prioridade;
		}
	}
	
	@Id
    @GeneratedValue
    private Long id;
	
	private String descricao;
	private PrioridadeEnum prioridade;
	private Calendar prazo;
	
	public Meta(String descricao, int prioridade) {
		this.descricao = descricao;
		this.prioridade = getPrioridade(prioridade);
	}
	
	public Meta(){
		
	}
	
	public void setPrazo(int dia, int mes, int ano){
		prazo = new GregorianCalendar(ano, mes-1, dia);
		prazo.setFirstDayOfWeek(Calendar.SUNDAY);
	}

	public Calendar getPrazo() {
		return prazo;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public PrioridadeEnum getPrioridade(){
		return prioridade;
	}
	
	private PrioridadeEnum getPrioridade(int prioridade){
		switch (prioridade) {
		case 1:
			return PrioridadeEnum.ALTO;
		case -1: 
			return PrioridadeEnum.BAIXO;	
		} 
		return PrioridadeEnum.Normal;
		
	}

}
