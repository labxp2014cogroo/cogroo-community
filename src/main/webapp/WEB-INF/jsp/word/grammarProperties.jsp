<form action="<c:url value="chooseProperties"/>" method="post">
	<div id="others" class="white_box">
		<h3>Classifique a palavra quanto ao g�nero:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="gender" value="G=m," />Masculino<br>
			<input type="radio" name="gender" value="G=f," />Feminino<br>
			<input type="radio" name="gender" value="G=n," />Neutro [ex: o(a) estudante]<br>
		</div>
		<br>
		<h3>Classifique a palavra quanto ao n�mero:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="number" value="N=s" />Singular<br>
			<input type="radio" name="number" value="N=p" />Plural<br>
			<input type="radio" name="number" value="N=_" />Indefinido [ex: o(s) �culos]<br>
		</div>
	</div>
	
	<div id="v" class="white_box">
		<h3>Classifique o verbo:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="transitivity" value="TR=t" />Transitivo<br>
			<input type="radio" name="transitivity" value="TR=i" />Intransitivo<br>
			<input type="radio" name="transitivity" value="TR=_" />Ambos<br>
			<input type="radio" name="transitivity" value="TR=l" />De Liga��o<br>
		</div>
	</div>
	
	<div id="adv" class="white_box">
		<h3>Classifique o adv�rbio:</h3>
		<br>
		<div class="dashed_white">
			<input type="radio" name="type" value="SUBCAT=modo" />Modo<br>
			<input type="radio" name="type" value="SUBCAT=tempo" />Tempo<br>
			<input type="radio" name="type" value="SUBCAT=lugar" />Lugar<br>
			<input type="radio" name="type" value="SUBCAT=quant" />Quantidade<br>
			<input type="radio" name="type" value="SUBCAT=neg" />Nega��o<br>
		</div>
	</div>
	<br>
	<input type="submit" value=" OK &raquo; " id="go" />
	<input type="hidden" name="entry" value="${entry}" />
</form>


<script>
$(document).ready(
function() {
	$(".white_box").each(
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