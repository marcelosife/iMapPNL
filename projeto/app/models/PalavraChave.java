
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity 
public class PalavraChave extends Model
{
	private static final long serialVersionUID = 1L;
	@Id
	int id;
	String descricao;
	
	PalavraChave(String descricao){
		this.descricao = descricao;
	}
	
	public static Finder<Long,PalavraChave> find = new Finder<Long,PalavraChave>(
		    Long.class, PalavraChave.class
	); 
	
}
