<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consultar palavras</title>
</head>
<body>
	<form action="<c:url value="/searchEntry"/>" method="post">
		<input type="text" name="text" id="text"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
	<br />${json_result}
</body>
</html>


<title>Inserir palavras</title>
	<form action="<c:url value="/trataPalavra"/>" method="post">
		<input type="text" name="word" id="word"/>
		<input type="submit" value=" OK &raquo; " id="go" />
	</form>
<br />${mensagem}
