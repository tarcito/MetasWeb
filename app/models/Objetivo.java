package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Objetivo {
	
	@Id
    @GeneratedValue
    private Long id;
	
	private String objetivo;
	
	public Objetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	public Objetivo(){
		
	}

	public String getObjetivo() {
		return objetivo;
	}
	
}
