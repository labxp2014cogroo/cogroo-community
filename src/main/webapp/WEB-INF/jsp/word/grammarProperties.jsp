<form action="<c:url value="chooseProperties"/>" method="post">
	<div class="white_box">
	<h2>Classifique a palavra "${word }"</h2>
	<br>
	<div id="others" class="vocable_form">
		<h3>Quanto ao gênero:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="gender" value="G=m," />Masculino<br>
			<input type="radio" name="gender" value="G=f," />Feminino<br>
			<input type="radio" name="gender" value="G=n," />Neutro [ex: o(a) estudante]<br>
		</div>
		<br>
		<h3>Quanto ao número:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="number" value="N=s" />Singular<br>
			<input type="radio" name="number" value="N=p" />Plural<br>
			<input type="radio" name="number" value="N=_" />Indefinido [ex: o(s) óculos]<br>
		</div>
	</div>
	
	<div id="v" class="vocable_form">
		<h3>Quanto à transitividade:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="transitivity" value="TR=t" />Transitivo<br>
			<input type="radio" name="transitivity" value="TR=i" />Intransitivo<br>
			<input type="radio" name="transitivity" value="TR=_" />Ambos<br>
			<input type="radio" name="transitivity" value="TR=l" />De Ligação<br>
		</div>
	</div>
	
	<div id="adv" class="vocable_form">
		<h3>Quanto à circunstância:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="type" value="SUBCAT=modo" />Modo<br>
			<input type="radio" name="type" value="SUBCAT=tempo" />Tempo<br>
			<input type="radio" name="type" value="SUBCAT=lugar" />Lugar<br>
			<input type="radio" name="type" value="SUBCAT=quant" />Quantidade<br>
			<input type="radio" name="type" value="SUBCAT=neg" />Negação<br>
		</div>
	</div>
	<br>
	<input type="submit" value=" OK &raquo; " id="go" />
	<input type="hidden" name="entry" value="${entry}" />
	</div>
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
	
	} else if ('${category}' == "adv"){
		$("#adv").show();
	} else {
		$("#others").show();
		}
});
</script>