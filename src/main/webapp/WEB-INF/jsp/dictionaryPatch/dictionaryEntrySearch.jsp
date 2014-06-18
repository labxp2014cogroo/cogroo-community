<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui/jquery-ui-1.8.5.custom.css"/>" />

<div class="white_box">
<c:choose>
	<c:when test="${isEdition}">
		<h2>Editar palavras</h2>
	</c:when>
	<c:otherwise>
		<h2>Consultar palavras</h2>
	</c:otherwise>
</c:choose>
	<div class="dashed_white">
	
	<c:choose>
	<c:when test="${isEdition}">
		<h4>Qual palavra deseja editar?</h4>
	</c:when>
	<c:otherwise>
		<h4>Qual palavra deseja consultar?</h4>
	</c:otherwise>
</c:choose>
	
	<br>
	<form action="<c:url value="/dictionaryPatch/searchEntry"/>" method="post">
		<input type="hidden" name="isEdition" value="${isEdition}" />
		<input type="text" name="text" id="text"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
	</div>
</div>
<div class="yellow_box">
	<c:choose>
		<c:when test="${status == 404}">
			${mensagem_erro}.
			Não encontramos a palavra ${typed_word}.
			<br/>
			Deseja sugeri-la?<br/>
			<a href="/newEntry/loggedUser?word=${typed_word}" >
				<input type="button" value="Sugerir" class="a_button" />
			</a>
		</c:when>
		<c:when test="${status == 501}">
			${mensagem_erro}
		</c:when>	
		<c:when test="${status == 0}">
			<h3>Classificações da Palavra: ${typed_word}</h3>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="table_morf">
				<thead>
					<tr>
					  <th>Nº</th>
					  <th>Lemas</th>
					  <th>Classe</th>
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
		</c:when>
	</c:choose>
</div>
