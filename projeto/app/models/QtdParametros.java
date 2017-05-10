
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class QtdParametros extends Model
{

	private static final long serialVersionUID = 1L;
	@Id
	int id;
	int numRelacao;
	int numConceito;
	
	QtdParametros(int numRelacao, int numConceito){
		this.numConceito = numConceito;
		this.numRelacao = numRelacao;
	}
	
	public static Finder<Long,QtdParametros> find = new Finder<Long, QtdParametros>(
		    Long.class, QtdParametros.class
	); 
}
