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
</body>
</html>