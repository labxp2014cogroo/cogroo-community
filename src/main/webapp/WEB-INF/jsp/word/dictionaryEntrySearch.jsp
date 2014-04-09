<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui/jquery-ui-1.8.5.custom.css"/>" />

<title>Consultar palavras</title>
</head>
<body>
	Qual palavra deseja consultar? <br><br>
	<form action="<c:url value="/searchEntry"/>" method="post">
		<input type="text" name="text" id="text"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
	<br />
	<c:choose>
		<c:when test="${status == 400}">
			${mensagem_erro} 
		</c:when>
		<c:when test="${status == 404}">
			${mensagem_erro} 
			<a href="/newEntry?word=${typed_word}"> Deseja incluir palavra?</a>
		</c:when>
		<c:when test="${status == 501}">
			${mensagem_erro}
		</c:when>	
		<c:when test="${status == 0}">
			<h3>Classificações da Palavra: ${typed_word}</h3>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="table_morf">
				<thead>
					<tr>
					  <th>Nº.</th>
					  <th>Lemas</th>
					  <th>Classe</th>
					  <th>Flexão</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${vocables}" var="vocable" varStatus="j">
						<tr>
							<td>${ j.count }</td>
							<td>${vocable[0]}</td>
							<td>${vocable[1]}</td>
							<td>${vocable[2]}</td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
	</c:choose>
		
</body>
