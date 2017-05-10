package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity 
public class Conceito extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	int id;
	String descricao;
	
	public Conceito (String descricao){
		this.descricao = descricao;
	}
	
	public static Finder<Long, Conceito> find = new Finder<Long, Conceito>(
			Long.class, Conceito.class);
}

