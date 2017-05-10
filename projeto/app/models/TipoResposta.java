package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity 
public class TipoResposta extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	int id;
	String descricao;
	
	public static Finder<Long,TipoResposta> find = new Finder<Long,TipoResposta>(
		    Long.class, TipoResposta.class
	);
	
	TipoResposta(String descricao){
		this.descricao = descricao;
	}
}
