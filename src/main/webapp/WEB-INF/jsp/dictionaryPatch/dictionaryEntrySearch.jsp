<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui/jquery-ui-1.8.5.custom.css"/>" />

<div class="white_box">
<h2>Consultar palavras</h2>
	<div class="dashed_white">
	<h4>Qual palavra deseja consultar?</h4>
	<br>
	<form action="<c:url value="/dictionaryPatch/searchEntry"/>" method="post">
		<input type="text" name="text" id="text"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
	</div>
</div>
<div class="yellow_box">
	<c:choose>
		<c:when test="${status == 404}">
			<h2>A palavra "${typed_word}" não consta no dicionário.</h2>
			<div class="dashed_white">
				<h4>Deseja sugeri-la?</h4>
				</br>
				<a href="/newEntry/loggedUser?word=${typed_word}"><input type="button" value="Sugerir" class="a_button"/></a>
			</div>
		</c:when>
		<c:when test="${status == 501}">
			${mensagem_erro}
		</c:when>	
		<c:when test="${status == 0}">
			<h2>Palavra: ${typed_word}</h2>
			<div class="dashed_white">
				<h4>Classificações:</h4>
				</br>
				<table cellpadding="0" cellspacing="0" border="0" class="display" id="table_morf">
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
