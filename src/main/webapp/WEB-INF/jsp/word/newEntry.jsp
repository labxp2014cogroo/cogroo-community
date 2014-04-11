<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<h2>Inserir Palavra</h2>

${word}
<br>
<br>

<form action="<c:url value="/insertEntry?word=${word}"/>" method="post">
	<div id="start_div">
		Classifique a palavra sintaticamente:<br>
		<br>
		<input type="radio" name="dictionaryEntry.category" value="COMMON_NOUN" />Substantivo simples<br>
		<input type="radio" name="dictionaryEntry.category" value="PROPER_NOUN" />Substantivo próprio<br>
		<input type="radio" name="dictionaryEntry.category" value="VERB" />Verbo<br>
		<input type="radio" name="dictionaryEntry.category" value="ADJECTIVE" />Adjetivo<br>
	</div>
	
	
	<div id="noun_adj_div" class="vocable_form">
		Classifique a palavra quanto ao gênero:<br>
		<br>
		<input type="radio" name="gender" value="m" />Masculino<br>
		<input type="radio" name="gender" value="f" />Feminino<br>
		<input type="radio" name="gender" value="n" />Neutro [ex: o(a) estudante]<br>
		<br>
		<br> Classifique a palavra quanto ao número:<br>
		<br>
		<input type="radio" name="number" value="s" />Singular<br>
		<input type="radio" name="number" value="p" />Plural<br>
		<input type="radio" name="number" value="_" />Indefinido [ex: o(s) óculos]<br>
	</div>
	
	<div id="verb_div" class="vocable_form">
		Classifique a palavra quanto ao modo verbal:<br>
		<br>
		<input type="radio" name="verbtense" value="m" />Indicativo<br>
		<input type="radio" name="verbtense" value="f" />Subjuntivo<br>
		<input type="radio" name="verbtense" value="n" />Imperativo<br>
		<br>
	</div>
	<br>
	<br>
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
