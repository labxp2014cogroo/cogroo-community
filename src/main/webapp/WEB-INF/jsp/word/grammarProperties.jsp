<form action="<c:url value="chooseProperties"/>" method="post">
	<div id="others" class="vocable_form">
		Classifique a palavra quanto ao gênero:<br>
		<br>
		<input type="radio" name="gender" value="G=m," />Masculino<br>
		<input type="radio" name="gender" value="G=f," />Feminino<br>
		<input type="radio" name="gender" value="G=n," />Neutro [ex: o(a) estudante]<br>
		<br>
		<br> Classifique a palavra quanto ao número:<br>
		<br>
		<input type="radio" name="number" value="N=s" />Singular<br>
		<input type="radio" name="number" value="N=p" />Plural<br>
		<input type="radio" name="number" value="N=_" />Indefinido [ex: o(s) óculos]<br>
	</div>
	
	<div id="v" class="vocable_form">
		Classifique o verbo :<br>
		<br>
		<input type="radio" name="transitivity" value="TR=t" />Transitivo<br>
		<input type="radio" name="transitivity" value="TR=i" />Intransitivo<br>
		<input type="radio" name="transitivity" value="TR=_" />Ambos<br>
		<input type="radio" name="transitivity" value="TR=l" />De Ligação<br>
		<br>
	</div>
	
	<div id="adv" class="vocable_form">
		Classifique o verbo :<br>
		<br>
		<input type="radio" name="type" value="SUBCAT=modo" />Modo<br>
		<input type="radio" name="type" value="SUBCAT=tempo" />Tempo<br>
		<input type="radio" name="type" value="SUBCAT=lugar" />Lugar<br>
		<input type="radio" name="type" value="SUBCAT=quant" />Quantidade<br>
		<input type="radio" name="type" value="SUBCAT=neg" />Negação<br>
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
