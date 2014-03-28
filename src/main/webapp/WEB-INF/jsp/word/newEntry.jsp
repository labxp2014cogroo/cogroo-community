<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<h2>Inserir Palavra</h2>

${mensagem}
<br>
<br>

Classifique a palavra quanto ao gênero:<br><br>
<form action="<c:url value="/trataPalavra"/>" method="post">
	<input type="radio" name="G" value="m" />Masculino<br>
	<input type="radio" name="G" value="f" />Feminino<br>
	<input type="radio" name="G" value="n" />Neutro [ex: o(a) estudante]<br>
</form>

<br><br>

Classifique a palavra quanto ao número:<br><br>
<form action="<c:url value="/trataPalavra"/>" method="post">
	<input type="radio" name="N" value="s" />Singular<br>
	<input type="radio" name="N" value="p" />Plural<br>
	<input type="radio" name="N" value="_" />Indefinido [ex: o(s) óculos]<br>
</form>

<br><br>

<form action="<c:url value="/trataPalavra"/>" method="post">
	<input type="submit" value=" OK &raquo; " id="go" />
</form>
