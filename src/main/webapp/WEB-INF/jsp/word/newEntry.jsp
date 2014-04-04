<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<h2>Inserir Palavra</h2>

${mensagem}
<br>
<br>

<div id="start_div">
	Classifique a palavra sintaticamente:<br>
	<br>
	<form action="<c:url value="/trataPalavra"/>" method="post">
		<input type="radio" name="CAT" value="COMMON_NOUN" />Substantivo simples<br>
		<input type="radio" name="CAT" value="PROPER_NOUN" />Substantivo próprio<br>
		<input type="radio" name="CAT" value="VERB" />Verbo<br>
		<input type="radio" name="CAT" value="ADJECTIVE" />Adjetivo<br>
	</form>
</div>



<div id="noun_adj_div" class="vocable_form">
	Classifique a palavra quanto ao gênero:<br>
	<br>
	<form action="<c:url value="/trataPalavra"/>" method="post">
		<input type="radio" name="G" value="m" />Masculino<br> <input
			type="radio" name="G" value="f" />Feminino<br> <input
			type="radio" name="G" value="n" />Neutro [ex: o(a) estudante]<br>
	</form>

	<br>
	<br> Classifique a palavra quanto ao número:<br>
	<br>
	<form action="<c:url value="/trataPalavra"/>" method="post">
		<input type="radio" name="N" value="s" />Singular<br>
		<input type="radio" name="N" value="p" />Plural<br>
		<input type="radio" name="N" value="_" />Indefinido [ex: o(s) óculos]<br>
	</form>
</div>

<div id="verb_div" class="vocable_form">
	Classifique a palavra quanto ao modo verbal:<br>
	<br>
	<form action="<c:url value="/trataPalavra"/>" method="post">
		<input type="radio" name="G" value="m" />Indicativo<br>
		<input type="radio" name="G" value="f" />Subjuntivo<br>
		<input type="radio" name="G" value="n" />Imperativo<br>
	</form>

	<br>
</div>

<br>
<br>

<form action="<c:url value="/trataPalavra"/>" method="post">
	<input type="submit" value=" OK &raquo; " id="go" />
</form>


<script>
	$(document).ready(function() {
		$(".vocable_form").hide();
		$("input[type=radio][name=CAT]").change(function() {
			if (this.value == "VERB") {
				$("#verb_div").show();
			} else {
				$("#noun_adj_div").show();
			}
		$("#start_div").hide();
		});
	});
</script>
