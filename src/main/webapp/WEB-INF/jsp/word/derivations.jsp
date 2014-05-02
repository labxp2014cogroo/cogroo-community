<h2>Selecione quais derivações abaixo se aplicam</h2>

<br>
<form action="<c:url value="/chooseFlags"/>" method="post">
	<c:forEach items="${derivations}" var="derivation">
		<input type="checkbox" name="flag[]" value="${derivation.key}" />${derivation.value}<br>
	</c:forEach>

	<input type="submit" value=" OK &raquo; " id="go" />
	<input type="hidden" name="entry" value="${entry}" />

</form>

