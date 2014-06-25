<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<script src="<c:url value='/js/jquery.dataTables.min.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/jquery.dataTables.sort.js' />" type="text/javascript" ></script>

<h2> Renomear Lema </h2>

<div class="white_box">
	<h2>Editar palavras</h2>
<div class="dashed_white">
	
	<h4>Qual palavra deseja editar?</h4>
	
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
					  <th>N�</th>
					  <th>Lemas</th>
					  <th>Classe</th>
					  <th>Flex�o</th>
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
