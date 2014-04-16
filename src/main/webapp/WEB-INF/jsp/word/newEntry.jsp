<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>" />

<h2>Inserir Palavra</h2>


${word}
<br>
<br>

<form id="word" action="<c:url value="/insertEntry?word=${word}"/>" method="post">
	<div style="float:left;">
		
		<h4>Classifique a palavra sintaticamente:</h4>
		<br>
		<br>
		<input type="radio" name="dictionaryEntry.category" value="COMMON_NOUN" />Substantivo simples<br>
		<input type="radio" name="dictionaryEntry.category" value="PROPER_NOUN" />Substantivo próprio<br>
		<input type="radio" name="dictionaryEntry.category" value="ADJECTIVE" />Adjetivo<br>
		<input type="radio" name="dictionaryEntry.category" value="VERB" />Substantivo simples e adjetivo<br>
		<input type="radio" name="dictionaryEntry.category" value="VERB" />Verbo<br>
		<input type="radio" name="dictionaryEntry.category" value="ADJECTIVE" />Outra classe gramatical<br>
		<br>
		<input type="submit" value=" OK &raquo; " id="go" />
	</div>
	
	<div id="word" style="float:right;">
		<h4>Inserindo a palavra corretamente:</h4>
		<br>
		<br>
		<h5>Procure sempre buscar a forma mais primitiva da palavra:</h5>
			<ul>
				<li>Para substantivos e adjetivos:
					<ol>
						<li>Coloque-o no gênero masculino, se existir</li>
						<li>Coloque-o no singular, se existir</li>
						<li>Retire qualquer grau da palavra</li>
					</ol>
				</li>
				<li>Para verbos:
					<ol>
						<li>Coloque-o no infinitivo impessoal (ex: cantar)</li>
					</ol>
				</li>
			</ul>
		<br>
	</div>
</form>


<script>
	$(document).ready(function() {
		$(".vocable_form").hide();
		$("input[type=radio][name=dictionaryEntry.category]").change(function() {
			if (this.value == "VERB") {
				$("#verb_div").show();
			} else {
				$("#noun_adj_div").show();
			}
		$("#start_div").hide();
		});
	});

</script>
