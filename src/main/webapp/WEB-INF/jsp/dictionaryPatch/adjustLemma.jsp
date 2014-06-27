<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<form action="<c:url value="/dictionaryPatch/correctedLemma"/>" method="post">
	<div class="yellow_box">
		<h1>Editando a palavra: "${word}"</h1>
		<div class="dashed_white">
			<h3>Digite o lema correto da palavra:</h3>
			</br>
			<input type="text" name="word"/>
			<input style="clear:both;" type="submit" value=" OK &raquo; " id="go"/>
			<input type="hidden" name="entry" value="${entry}"/>
		</div>
	</div>
	<br/>
</form>