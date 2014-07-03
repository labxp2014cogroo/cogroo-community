<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/dataTables_table.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/jquery-ui/jquery-ui-1.8.5.custom.css"/>" />

<div class="white_box">
	<h2>Consultar palavras</h2>
	<div class="dashed_white">
		<h4>Qual palavra deseja consultar?</h4>
		<br>
		<form action="<c:url value="/dictionaryPatch/searchEntry"/>"
			method="post">
			<input type="text" name="word" id="text" /> <input type="submit"
				value=" OK &raquo; " id="go" />
		</form>
	</div>
</div>
<div class="yellow_box">
	<c:choose>
		<c:when test="${status == 404}">
			${mensagem_erro}.
			Não encontramos a palavra ${typed_word}.
			<br />
			Deseja sugeri-la?<br />
			<a href="/newEntry/loggedUser?word=${typed_word}"> <input
				type="button" value="Sugerir" class="a_button" />
			</a>
		</c:when>
		<c:when test="${status == 501}">
			${mensagem_erro}.
		</c:when>
		<c:when test="${status == 0}">
			<div>
				<div style="float: left">
					<h3>Classificações da Palavra: ${typed_word}</h3>
				</div>
				<div style="float: right">
					<form method="post" action="/dictionaryPatch/searchLemma">
						<input type="hidden" name="word" value="${typed_word}"></input> <input
							id="go" type="submit" value=" Editar » "></input>
					</form>
				</div>
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="display"
				id="table_morf">
				<thead>
					<tr>
						<th>Nº</th>
						<th>Classe gramatical</th>
						<th>Raiz</th>
						<th>Flexão</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${vocables}" var="vocable" varStatus="j">
						<tr>
							<td>${j.count }</td>
							<td>${vocable[0]}</td>
							<td>${vocable[1]}</td>
							<td>${vocable[2]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</div>
</c:when>
</c:choose>
</div>

<script>
	$('#text').focus();
</script>
