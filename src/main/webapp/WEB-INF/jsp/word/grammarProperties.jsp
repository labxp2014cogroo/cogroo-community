${entry }

<form action="<c:url value="chooseProperties"/>" method="post">
	<div id="others" class="vocable_form">
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
	
	<div id="v" class="vocable_form">
		Classifique o verbo :<br>
		<br>
		<input type="radio" name="trasitivity" value="t" />Transitivo<br>
		<input type="radio" name="trasitivity" value="i" />Intransitivo<br>
		<input type="radio" name="trasitivity" value="_" />Ambos<br>
		<input type="radio" name="trasitivity" value="l" />De Ligação<br>
		<br>
	</div>
	
	<div id="av" class="vocable_form">
		Classifique o verbo :<br>
		<br>
		<input type="radio" name="type" value="t" />Modo<br>
		<input type="radio" name="type" value="i" />Tempo<br>
		<input type="radio" name="type" value="_" />Lugar<br>
		<br>
	</div>
	<br>
	<br>
	<input type="submit" value=" OK &raquo; " id="go" />
	<input type="hidden" name="entry" value="${entry}" />
</form>


<script>
$(document).ready(
function() {
	$(".vocable_form").each(
		function(i, e) {
			$(this).hide();
		}
	);

	if ('${category}' == "v") {
		$("#v").show();
	
	} else if ('${category}' == "av"){
		$("#av").show();
	} else {
		$("#others").show();
		}
});

</script>
