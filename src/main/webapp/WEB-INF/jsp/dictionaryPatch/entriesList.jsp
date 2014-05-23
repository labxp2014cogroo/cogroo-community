<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<script src="<c:url value='/js/jquery.dataTables.min.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/jquery.dataTables.sort.js' />" type="text/javascript" ></script>


<script>

function displayPatchDetails (nTr, idPatch) {
	datum = {'idPatch':idPatch};
	$.ajax({
		timeout: 5000,
		url : '<c:url value="/getPatch" />',
		type : "get",
		data : datum,
		beforeSend: function(response){
				oTable.fnOpen( nTr, "Carregando...", 'details' );
		},
		success: function(response){
					console.log(response);
					json = JSON.parse(response);
					if (json.status == json.ok){
						/* TODO: criar o HTML que será mostrado na div que é aberta na página */
						html = 'Derivações: ' + json.derivations;
					}else {
						html = json.msg;
					}
					oTable.fnOpen( nTr, html, 'details' );
			},
		error: function(response){
					oTable.fnOpen( nTr, 'Erro ao se comunicar com o servidor', 'details' );
			}
		});
	
}

$(document).ready(function() {
	oTable = $('#entriesList').dataTable( {
		"oLanguage": {
			"sLengthMenu": "Exibir _MENU_ entradas por página",
			"sSearch": "Filtrar entradas:",
			"sFirst": "Primeira página",
			"sLast": "Última página",
			"sNext": "Próxima página",
			"sPrevious": "Página anterior",
			"sZeroRecords": "Desculpe, nada encontrado.",
			"sInfo": "Exibindo de _START_ até _END_ de um total de _TOTAL_ entradas",
			"sInfoEmpty": "Exibindo de 0 até 0 de um total de 0 entradas",
			"sInfoFiltered": "(filtrados de um total de _MAX_ entradas)"
		},
		"aLengthMenu": [20,50,100,200],
		"aaSorting": [[ 6, 'desc' ]],
		"iDisplayLength": 20,
		"aoColumns": [
			{ "bSortable": false }, 	//0
			{ "sType": "num-html" }, 	//1
			null,  						//2
			null,						//3
			null,  						//4
			{ "sType": "title-string" },//5
			null,						//6
			{ "bVisible": false }		//7
		],
		
		
		"fnDrawCallback": function ( oSettings ) {
			$('#entriesList tbody tr').each( function () {
				var title = $(this).attr('title');
				$(this).click( function () {
					window.location = title;
					alert (title);
				} );
				$(this).hover(function() {
		            $(this).css('cursor', 'pointer');
		        }, function() {
		            $(this).css('cursor', 'auto');
		        });
			} );
		}
	} );
	
	/* Add click event handler for user interaction */
	$('td img', oTable.fnGetNodes() ).each( function () {
		$(this).click( function (e) {
			e.stopPropagation();
			var nTr = this.parentNode.parentNode;
			if ( this.src.match('details_close') )
			{
				/* This row is already open - close it */
				this.src = "./images/details_open.png";
				oTable.fnClose( nTr );
			}
			else if(this.src.match('details_open'))
			{
				/* Open this row */
				this.src = "./images/details_close.png";
				//oTable.fnOpen( nTr, fnFormatDetails(nTr), 'details' );
				displayPatchDetails (nTr, $(this).attr('idPatch'));
			}
		} );
	} );
});
</script>

<h2>
Lista de palavras
<span class="help"><a onclick="onOff('helpErrorList'); return false" href="#"><img src="<c:url value='/images/help.png' />" /></a></span>
</h2>

<div id="helpErrorList" style="display: none;" class="help">
	<p>Exibe todas as palavras sugeridas pelos usuários.</p>
</div>

<span style="FLOAT: right; POSITION: static">
	<c:if test="${loggedUser.user.role.canEditErrorReport}">
		<a href="<c:url value="/reports/edit"/>">Edição múltipla</a>
	</c:if>
</span>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="entriesList">
		<thead>
			<tr>
			  <th></th> 			<!-- 0 -->
			  <th title="Exibe o número do problema reportado.">Nº.</th>			<!-- 1 -->
			  <th title="Indica a situação (aberta, em andamento, resolvida, aguardando resposta, fechada ou rejeitada) do problema.">Situação</th>				<!-- 2 -->
			  <th title="Exibe a entrada anterior.">Entrada anterior</th>     <!-- 3 -->
			  <th title="Exibe a nova entrada.">Entrada modificada</th>		<!-- 4 -->
			  <th title="Exibe a data da última alteração realizada no problema.">Data</th>	<!-- 5 -->
			  <th title="Exibe o número de comentários feitos sobre o problema.">Comentários</th>	<!-- 6 -->
			  <th>Detalhes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dictionaryPatchList}" var="patch" varStatus="i">

				<c:if test="${patch.isNew}">
					<tr id="tr_dictionaryPatch_${ i.count }" class="highlighted" title="<c:url value="/entries/${patch.id}"/>">
				</c:if>
				<c:if test="${not errorEntry.isNew}">
					<tr id="tr_dictionaryPatch_${ i.count }" title="<c:url value="/entries/${patch.id}"/>">
				</c:if>
			
					<td valign="middle"><img src="./images/details_open.png" idPatch=${patch.id} ></td>		<!-- 0 -->
					<td><a href="<c:url value="/entries/${patch.id}"/>">${patch.id}</a></td>		<!-- 1 -->
					
					<td><fmt:message key="${patch.state}" /></td>					<!-- 2 -->
					<td>${patch.previousEntry}</td>			<!-- 3 -->
					<td>${patch.newEntry}</td>			<!-- 4 -->
					<td><span title="${patch.modified}"></span><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${patch.modified}" /></td>		<!-- 5 -->
					<td>${patch.commentCount}</td>									<!-- 6 -->
	  			  	<td>																<!-- 7 -->
<!-- 	  			  	TODO -->
<%--   					<c:if test="${(patch.submitter.login == loggedUser.user.login) && (patch.submitter.service == loggedUser.user.service) || loggedUser.user.role.canDeleteOtherUserErrorReport }">  --%>
<%-- 						<a onclick="remove_error('${ i.count }'); return false;" id="_${ i.count }" href="about:blank" class="remove_error">excluir</a> --%>
<%-- 						<form action="<c:url value="/entries/${patch.id}"/>" method="post" id="form_remove_error_${ i.count }"> --%>
<!-- 						    <input type="hidden" name="_method" value="DELETE"/> -->
<%-- 						    <input name="patch.id" value="${errorEntry.id}" type="hidden" /> --%>
<!-- 						</form> -->
<%-- 					</c:if> --%>
	  			  	<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">
	  			  		<tr><td><input name="patch.id" value="${errorEntry.id}" type="hidden" /></td></tr>
		  			  	<tr><td>Palavras envolvidas</td></tr>
						<tr><td>Enviado por:</td><td><a href="<c:url value="/users/${patch.user.service}/${patch.user.login}"/>">${patch.user.name}</a></td></tr>
	  			  	</table>
					</td>
			</c:forEach>
		</tbody>
	</table>