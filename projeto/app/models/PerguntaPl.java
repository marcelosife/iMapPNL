package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity 
public class PerguntaPl extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	int id;
	boolean temlista;
	int maxqtdparametros;
	String pergunta;
	@ManyToOne
	TipoResposta tpResposta;
	@ManyToOne
	QtdParametros qtdParam;
	
	@ManyToMany
	List <PalavraChave> palavraschave = new ArrayList<PalavraChave>();
	
	public static Finder<Long,PerguntaPl> find = new Finder<Long,PerguntaPl>(
		    Long.class, PerguntaPl.class
	);
	
	public PerguntaPl (String pergunta){
		this.pergunta= pergunta;
	}
	
	public String getPergunta(){
		return pergunta;
	}
	
	public String getPergunta(Pergunta p){
		String perguntac=this.pergunta;
		int tam = 0;
		perguntac += "( ";
		if(p.relacao != null){
			perguntac += p.relacao;
			tam++;
		}
		for(Conceito c : p.conceitos){
			if(tam > 0)
				perguntac += ", ";
			perguntac += c.descricao;
			tam++;
		}
		if( temlista ){
			perguntac += ", ["+p.conceitos.get(0).descricao+"]";
			tam++;
		}
		for(int i= tam, j =1; i < maxqtdparametros; i++, j++){
			if(tam == 0)
				perguntac += "S"+j;
			else
				perguntac += ", S"+j;
			tam++;			
		}
		perguntac += " )";
		return perguntac;
	}
	
	public static List<PerguntaPl> filtraTipo(String tipo){
		List<PerguntaPl> resp = PerguntaPl.find.where().eq("tpResposta.descricao", tipo).findList();
		return resp;
	}
	
	public static List<PerguntaPl> filtraQtdParam(List<PerguntaPl> resps, int nconceito, int nrelacao){
		if( resps == null )
			resps = PerguntaPl.find.all();
		List<PerguntaPl> auxresp = new ArrayList<PerguntaPl>();
		QtdParametros qtdp;
		for (PerguntaPl pergpl : resps) {
			qtdp = QtdParametros.find.byId(Long.parseLong(""+pergpl.qtdParam.id));
			if( qtdp.numConceito == nconceito && qtdp.numRelacao == nrelacao )
				auxresp.add(pergpl);
		}
		return auxresp;
	}
	
	public static List<PerguntaPl> filtraPalavraChave(List<PerguntaPl> resps, Pergunta perg){
		List<PerguntaPl> auxresp = new ArrayList<PerguntaPl>();
		for (PerguntaPl pergpl : resps) {
			for( PalavraChave pchave : pergpl.palavraschave ){
				if(  perg.pergunta.contains(pchave.descricao)  ){
					auxresp.add(pergpl);
				}
			}
		}
		return auxresp;
	}
}
