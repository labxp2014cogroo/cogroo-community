<form action="<c:url value="/chooseFlags"/>" method="get">
	<div class="white_box">
		<h2>Selecione quais derivações da palavra "${word }" se aplicam:</h2>
		<br>
		<div class="dashed_white">
			
			<c:forEach items="${derivations}" var="derivation">
				<br>
				${flagsTexts[derivation.key]}
				<br>  
				<input type="checkbox" name="flag[]" value="${derivation.key}" />${derivation.value}
				<br> 
			</c:forEach>
		</div>	
		<input type="submit" value=" OK &raquo; " id="go" />
		<input type="hidden" name="entry" value="${entry}" />
	</div>
</form>



