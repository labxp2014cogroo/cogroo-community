package br.usp.ime.cogroo.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class Vocable {
	
	private String word;
	private String radical;
	private HashMap<String, String> properties;
	
	public Vocable(String category, String word, String radical) {
		this.word = word;
		this.radical = radical;
		this.properties = new HashMap<String, String>();
		this.addProperty("CAT", category);
	}
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getCategory() {
		return properties.get("Categoria");
	}
	
	public String getPropertiesAsString() {
		
		StringBuilder description = new StringBuilder();
		
		
		for (Entry<String, String> entry : properties.entrySet()) {
			description.append(entry.getValue()).append(", ");
		}
		
		
		return description.toString();
	}
	
	public void addProperty (String key, String value) {
		String myKey = null;
		String myValue = null;
		
		if (key.equals("LA")){
			myKey = "Necessita artigo";
			if (value.equals("1")){
				myValue = "sim";
			}
		}else if (key.equals("Prep")){
			myKey = "Preposição (contracção)";
			if (value.equals("em")){
				myValue = "em";
			}else if (value.equals("por")){
				myValue = "por";
			}else if (value.equals("de")){
				myValue = "de";
			}else if (value.equals("a")){
				myValue = "a";
			}else if (value.equals("com")){
				myValue = "com";
			}
		}else if (key.equals("T")){
			myKey = "Tempo";
			if (value.equals("ip")){
				myValue = "infinitivo pessoal";
			}else if (value.equals("inf")){
				myValue = "infinitivo";
			}else if (value.equals("pp")){
				myValue = "pretérito perfeito";
			}else if (value.equals("ppa")){
				myValue = "particípio passado";
			}else if (value.equals("pc")){
				myValue = "presente do conjuntivo";
			}else if (value.equals("pic")){
				myValue = "pretérito imperfeito do conjuntivo";
			}else if (value.equals("c")){
				myValue = "condicional";
			}else if (value.equals("p")){
				myValue = "presente";
			}else if (value.equals("fc")){
				myValue = "futuro do conjuntivo";
			}else if (value.equals("g")){
				myValue = "gerúndio";
			}else if (value.equals("pmp")){
				myValue = "pretérito mais que perfeito";
			}else if (value.equals("pi")){
				myValue = "pretérito imperfeito";
			}else if (value.equals("f")){
				myValue = "futuro";
			}else if (value.equals("i")){
				myValue = "imperativo";
			}
		}else if (key.equals("N")){
			myKey = "Número";
			if (value.equals("n")){
				myValue = "neutro";
			}else if (value.equals("p")){
				myValue = "plural";
			}else if (value.equals("_")){
				myValue = "indefinido";
			}else if (value.equals("s")){
				myValue = "singular";
			}
			
		}else if (key.equals("SEM")){
			myKey = "Semântica";
			if (value.equals("rio")){
				myValue = "rio";
			}else if (value.equals("mes")){
				myValue = "mês";
			}else if (value.equals("livro")){
				myValue = "obra literária";
			}else if (value.equals("po")){
				myValue = "povo";
			}else if (value.equals("ter")){
				myValue = "localidade";
			}else if (value.equals("country")){
				myValue = "país";
			}else if (value.equals("n")){
				myValue = "número romano";
			}else if (value.equals("mar")){
				myValue = "referente a marca";
			}else if (value.equals("p")){
				myValue = "nome português";
			}else if (value.equals("pl")){
				myValue = "planeta";
			}else if (value.equals("sigla")){
				myValue = "sigla";
			}else if (value.equals("mitol")){
				myValue = "seres mitológicos";
			}else if (value.equals("cont")){
				myValue = "continente";
			}else if (value.equals("cid")){
				myValue = "cidade";
			}else if (value.equals("lingua")){
				myValue = "língua";
			}else if (value.equals("instituicao")){
				myValue = "instituição";
			}else if (value.equals("servico")){
				myValue = "serviço";
			}else if (value.equals("proj")){
				myValue = "Projeto";
			}else if (value.equals("clube")){
				myValue = "clube ou time";
			}
		}else if (key.equals("CAT")){
			myKey = "Categoria";
			if (value.equals("punct")){
				myValue = "pontuação";
			}else if (value.equals("punctg")){
				myValue = "ponto de exclamação";
			}else if (value.equals("puncth")){
				myValue = "ponto de interrogação";
			}else if (value.equals("punctj")){
				myValue = "reticências";
			}else if (value.equals("puncti")){
				myValue = "ponto final";
			}else if (value.equals("ppos")){
				myValue = "pronome possessivo";
			}else if (value.equals("pind")){
				myValue = "pronome indefinido";
			}else if (value.equals("pdem")){
				myValue = "pronome demonstrativo";
			}else if (value.equals("pint")){
				myValue = "pronome interrogativo";
			}else if (value.equals("ppes")){
				myValue = "pronome pessoal";
			}else if (value.equals("prel")){
				myValue = "pronome relativo";
			}else if (value.equals("prep")){
				myValue = "preposição";
			}else if (value.equals("in")){
				myValue = "interjeição";
			}else if (value.equals("conj-s")){
				myValue = "conjunção subordinativa";
			}else if (value.equals("conj-c")){
				myValue = "conjunção coordenativa";
			}else if (value.equals("nc")){
				myValue = "substantivo comum";
			}else if (value.equals("np")){
				myValue = "nome próprio";
			}else if (value.equals("adj")){
				myValue = "adjetivo";
			}else if (value.equals("art")){
				myValue = "artigo";
			}else if (value.equals("pass")){
				myValue = "partícula apassivadora";
			}else if (value.equals("v")){
				myValue = "verbo";
			}else if (value.equals("cp")){
				myValue = "contração";
			}else if (value.equals("card")){
				myValue = "cardinal";
			}else if (value.equals("nord")){
				myValue = "ordinal";
			}else if (value.equals("adv")){
				myValue = "advérbio";
			}
		}else if (key.equals("F")){
			myKey = "Ocorrências (Frequência)";
			if (value.equals("0")){
				myValue = "pouco frequente";
			}else if (value.equals("1")){
				myValue = "razoavelmente frequente";
			}else if (value.equals("2")){
				myValue = "muito frequente";
			}
		}else if (key.equals("FSEM")){
			myKey = "Função semântica do sufixo";
			if (value.equals("vel")){
				myValue = "-vel";
			}else if (value.equals("dd")){
				myValue = "-idade";
			}else if (value.equals("mente")){
				myValue = "-mente (forma adjectival como lema)";
			}else if (value.equals("vmente")){
				myValue = "-mente (forma verbal como lema)";
			}else if (value.equals("ismo")){
				myValue = "-ismo";
			}else if (value.equals("ista")){
				myValue = "-ista";
			}
		}else if (key.equals("Adv")){
			myKey = "Advérbio (contracção)";
			if (value.equals("a")){
				myValue = "a";
			}else if (value.equals("onde")){
				myValue = "onde";
			}else if (value.equals("ali")){
				myValue = "ali";
			}else if (value.equals("algures")){
				myValue = "algures";
			}else if (value.equals("aqui")){
				myValue = "aqui";
			}else if (value.equals("outrora")){
				myValue = "outrora";
			}
		}else if (key.equals("C")){
			myKey = "Caso latino";
			if (value.equals("n")){
				myValue = "nominativo";
			}else if (value.equals("a")){
				myValue = "acusativo";
			}else if (value.equals("g")){
				myValue = "genitivo";
			}else if (value.equals("d")){
				myValue = "dativo";
			}
		}else if (key.equals("Pdem2")){
				myKey = "Transitividade";
				if (value.equals("outro")){
					myValue = "outro";
				}
		}else if (key.equals("TR")){
			myKey = "Transitividade";
			if (value.equals("_")){
				myValue = "transitivo/intransitivo";
			}else if (value.equals("i")){
				myValue = "intransitivo";
			}else if (value.equals("t")){
				myValue = "transitivo";
			}else if (value.equals("l")){
				myValue = "de ligação";
			}
		}else if (key.equals("Pind")){
			myKey = "Pronome indefinido (contracção)";
			if (value.equals("algo")){
				myValue = "algo";
			}else if (value.equals("outro")){
				myValue = "outro";
			}else if (value.equals("outrem")){
				myValue = "outrem";
			}else if (value.equals("algum")){
				myValue = "algum";
			}else if (value.equals("o")){
				myValue = "o";
			}
		}else if (key.equals("GR")){
			myKey = "Grau";
			if (value.equals("sup")){
				myValue = "superlativo";
			}else if (value.equals("dim")){
				myValue = "diminutivo";
			}
		}else if (key.equals("P")){
			myKey = "Pessoa";
			if (value.equals("1")){
				myValue = "primeira";
			}else if (value.equals("3")){
				myValue = "terceira";
			}else if (value.equals("2")){
				myValue = "segunda";
			}else if (value.equals("1_3")){
				myValue = "primeira/terceira";
			}
		}else if (key.equals("ABR")){
			myKey = "Abreviatura";
			if (value.equals("1")){
				myValue = "sim";
			}
		}else if (key.equals("Verb")){
			myKey = "Do verbo";
			if (value.equals("haver")){
					myValue = "haver";
			}
		}else if (key.equals("Pdem")){
			myKey = "Pronome demonstrativo (contracção)";
			if (value.equals("isto")){
				myValue = "isto";
			}else if (value.equals("isso")){
				myValue = "isso";
			}else if (value.equals("esse")){
				myValue = "esse";
			}else if (value.equals("aquele")){
				myValue = "aquele";
			}else if (value.equals("aquilo")){
				myValue = "aquilo";
			}else if (value.equals("este")){
				myValue = "este";
			}
		}else if (key.equals("Pdem")){
			myKey = "Pronome demonstrativo (contracção)";
			if (value.equals("entre")){
				myValue = "entre";
			}
		}else if (key.equals("Art")){
			myKey = "Artigo (contracção)";
			if (value.equals("as")){
				myValue = "as";
			}else if (value.equals("a")){
				myValue = "a";
			}else if (value.equals("um")){
				myValue = "um";
			}else if (value.equals("o")){
				myValue = "o";
			}
		}else if (key.equals("Ppes")){
			myKey = "Pronome pessoal (contracção)";
			if (value.equals("sigo")){
				myValue = "sigo";
			}else if (value.equals("ele")){
				myValue = "ele";
			}else if (value.equals("te")){
				myValue = "te";
			}else if (value.equals("tigo")){
				myValue = "tigo";
			}else if (value.equals("nosco")){
				myValue = "nosco";
			}else if (value.equals("me")){
				myValue = "me";
			}else if (value.equals("vosco")){
				myValue = "vosco";
			}else if (value.equals("lhe")){
				myValue = "lhe";
			}else if (value.equals("migo")){
				myValue = "migo";
			}
		}else if (key.equals("BRAS")){
			myKey = "Origem brasileira";
			if (value.equals("1")){
				myValue = "sim";
			}
		}else if (key.equals("I")){
			myKey = "Nível de irregularidade";
			if (value.equals("4")){
				myValue = "4";
			}else if (value.equals("3")){
				myValue = "3";
			}
		}else if (key.equals("SUBCAT")){
			myKey = "Sub-categoria";
			if (value.equals("lug")){
				myValue = "advérbio de lugar";
			}else if (value.equals("lugar")){
				myValue = "advérbio de lugar";
			}else if (value.equals("tempo")){
				myValue = "advérbio de tempo";
			}else if (value.equals("neg")){
				myValue = "advérbio de negação";
			}else if (value.equals("quant")){
				myValue = "advérbio de quantidade";
			}else if (value.equals("modo")){
				myValue = "advérbio de modo";
			}
		}else if (key.equals("CLA")){
			myKey = "Classificação";
			if (value.equals("def")){
				myValue = "definido";
			}else if (value.equals("indef")){
				myValue = "indefinido";
			}
		}else if (key.equals("G")){
			myKey = "Género";
			if (value.equals("n")){
				myValue = "neutro";
			}else if (value.equals("m")){
				myValue = "masculino";
			}else if (value.equals("2")){
				myValue = "ambos";
			}else if (value.equals("_")){
				myValue = "indefinido";
			}else if (value.equals("f")){
				myValue = "feminino";
			}
		}else if (key.equals("PFSEM")){
			myKey = "Função semântica do prefixo";
			if (value.equals("outra")){
				myValue = "re-";
			}
		}else if (key.equals("ORIG")){
			myKey = "Origem";
			if (value.equals("ing")){
				myValue = "inglesa";
			}else if (value.equals("lat")){
				myValue = "latina";
			}else if (value.equals("fra")){
				myValue = "francesa";
			}else if (value.equals("esp")){
				myValue = "espanhola";
			}else if (value.equals("pol")){
				myValue = "polaca";
			}else if (value.equals("rus")){
				myValue = "russa";
			}else if (value.equals("ita")){
				myValue = "italiana";
			}else if (value.equals("ale")){
				myValue = "alemã";
			}else if (value.equals("jap")){
				myValue = "japonesa";
			}else if (value.equals("isl")){
				myValue = "islandêsa";
			}else if (value.equals("nor")){
				myValue = "norueguesa";
			}else if (value.equals("cor")){
				myValue = "coreana";
			}else if (value.equals("gre")){
				myValue = "grega";
			}
		}else if (key.equals("unknown")){
			myKey = "Palavra sugerida";
			if (value.equals("1")){
				myValue = "sim";
			}
		}

		if (myKey == null || myValue == null){
			// TODO: throw Exception ?
		}else {
			properties.put(myKey, myValue);
		}
		
	}

	public String getRadical() {
		return radical;
	}
	public void setRadical(String radical) {
		this.radical = radical;
	}
	
	
}
