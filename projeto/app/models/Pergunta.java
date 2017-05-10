package models;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Pergunta extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	int id;
	String relacao;
	String chave;
	String pergunta;
	@OneToOne
	TipoResposta tpResposta;

	@OneToMany(cascade = CascadeType.ALL)
	List<Conceito> conceitos = new ArrayList<Conceito>();

	@ManyToMany
	public List<PerguntaPl> perguntapls = new ArrayList<PerguntaPl>();

	public Pergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void saveTipoResposta() {
		this.tpResposta.save();
	}
	
	public void setConceitos( List<Conceito> lc){
		this.conceitos = new ArrayList<Conceito>(lc);
	}
	
	public int getId(){
		return id;
	}
	public static Finder<Long, Pergunta> find = new Finder<Long, Pergunta>(
			Long.class, Pergunta.class);

	public void trataPergunta(ArrayList<String> listVisl) {
		int i;
		String termo[];
		boolean isconc = false;
		boolean isrela = false;
		boolean teveconc = false;
		boolean teverelc = false;
		boolean tevenome = false;
		for (String line : listVisl) {
			isconc = false;
			isrela = false;
			termo = line.split(" ");
			for (i = 0; i < termo.length; i++) {
				if (termo[i].equals("interr") && this.chave == null) {
					this.chave = termo[2].substring(1, termo[2].length() - 1);
				}
				if (termo[i].equals("fmc") && this.chave == null) {
					this.chave = termo[2].substring(1, termo[2].length() - 1);
				}
				if (termo.length > 2) {
					if(termo[2].length() > 6){
						if (termo[2].substring(1, termo[2].length() - 1).equals(
								"conceito")) {
							isconc = true;
						}
						if (termo[2].substring(1, termo[2].length() - 1).equals(
								"relação")) {
							isrela = true;
						}
					}
				}
				if ((teveconc || teverelc)) {
					if(termo[i].equals("N"))
						tevenome = true;
					if (teveconc && (termo[i].equals("N")|| termo[i].equals("ADJ"))) {
						this.conceitos.add(new Conceito(termo[0]));
						teveconc = false;
					}
					if (teverelc) {
						if(termo[i].equals("N") || termo[i].equals("V")){
							this.relacao = termo[0];
							teverelc = false;
						}
					}
				}
			}
			if (teveconc || teverelc || !this.conceitos.isEmpty() || this.relacao!=null) {
				if (!tevenome) {
					if (teveconc || !this.conceitos.isEmpty()) {
						this.tpResposta = TipoResposta.find.where("descricao = 'conceito'").findUnique();
						teveconc = false;
					}
					if (teverelc || this.relacao!=null) {
						this.tpResposta = TipoResposta.find.where("descricao = 'relação'").findUnique();
						teverelc = false;
					}
				}
			}
			if (isconc)
				teveconc = true;
			if (isrela)
				teverelc = true;
		}
	}

	// filtro de tipo de pergunta
	public List<PerguntaPl> filtroTipoPergunta() {
		if (this.chave.equals("existir") || this.chave.equals("estar")
				|| this.chave.equals("ser"))
			return PerguntaPl.filtraTipo("logico"); // logico
		if (this.chave.equals("quanto") )
			return PerguntaPl.filtraTipo("numerico");// numerico
		if (this.chave.equals("conceito")
				|| this.tpResposta.descricao.equals("conceito"))
			return PerguntaPl.filtraTipo("conceito");// conceito
		if (this.chave.equals("relação")
				|| this.tpResposta.descricao.equals("relação"))
			return PerguntaPl.filtraTipo("relação");// relação
		
		return null;
	}

	// filtro de quantidade de parametros
	public List<PerguntaPl> filtroQuantParametros(List<PerguntaPl> resps) {
		int numconceito = conceitos.size();
		int numrelacao = 0;
		if (this.relacao != null)
			numrelacao = 1;
		return PerguntaPl.filtraQtdParam(resps, numconceito, numrelacao);
	}

	// filtro de quantidade de parametros
	public List<PerguntaPl> filtroPalavraChave(List<PerguntaPl> resps) {
		return PerguntaPl.filtraPalavraChave(resps, this);
	}
}
