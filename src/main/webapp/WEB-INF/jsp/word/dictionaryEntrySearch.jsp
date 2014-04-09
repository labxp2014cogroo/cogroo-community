<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consultar palavras</title>
</head>
<body>
	Qual palavra deseja consultar? <br><br>
	<form action="<c:url value="/searchEntry"/>" method="post">
		<input type="text" name="text" id="text"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
	${json_result}
	<br />
	<c:if test="${cod_erro == 404}">
		${mensagem_erro} 
		<a href="/newEntry?word=${typed_word}"> Deseja incluir palavra?</a>
	</c:if>
	<c:if test="${cod_erro == 501}">
		${mensagem_erro}
	</c:if>	
	
	<h3>Resultado da Consulta</h3>
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="table_morf">
		<thead>
			<tr>
			  <th>Nº.</th>
			  <th>Palavra</th>
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
					<td>${vocable[3]}</td>
			    </tr>
			</c:forEach>
		</tbody>
	</table>
	
		
</body>
</html>