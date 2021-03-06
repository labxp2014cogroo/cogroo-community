<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table_jui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables_table.css"/>" />
<script src="<c:url value='/js/jquery.dataTables.min.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/jquery.dataTables.sort.js' />" type="text/javascript" ></script>


<script>

var colors = Array();
colors[1] = '#CFCFCF';
colors[0] = '#B9B9B9';

bufferDetails = Array();


function submitForm () {
	$("#formAp").attr("action",'<c:url value="/patchDisapproval"/>');
}

function objLength(obj){
	var i=0;
	for (var x in obj){
		if(obj.hasOwnProperty(x)){
			i++;
		}
	} 
	return i;
}

function displayPatchDetails (nTr, idPatch, canApprove, patchState) {
	
	if (!(bufferDetails[idPatch] === undefined || bufferDetails[idPatch] === null)){
		oTable.fnOpen( nTr, bufferDetails[idPatch], 'details' );
		return;
	}
	
	datum = {'idPatch':idPatch};
	$.ajax({
		timeout: 10000, // ten seconds
		url : '<c:url value="/dictionaryPatch/getPatchDetails" />',
		type : "get",
		data : datum,
		beforeSend: function(response){
				oTable.fnOpen( nTr, "Carregando...", 'details' );
		},
		success: function(response){
					var k = 0;
					json = JSON.parse(response);
					if (json.status == json.ok){
						derivations = json.derivations;
						html = '<form action="<c:url value="/patchApproval"/>" method="post" id="formAp">';
						html += '<table class="display"><tr align="center"><td width="8%"><h3>Flags</h3></td><td><h3>Derivações</h3></td><td width="8%">';
						if (patchState == "OPEN" && canApprove && objLength(derivations) > 0) {
 							html += '<input id="checkAllFlags'+ idPatch +'" type="checkbox" checked="checked" onchange="isChecked = $(this).attr(\'checked\');';
 	 						html += '$(\'.flagscheckbox'+ idPatch +'\').attr(\'checked\', isChecked);">';
						}
						html += '</td></tr>';

						var obs = false;

						if (objLength(derivations) > 0) {
							for (var flag in derivations){
								html += '<tr style="background-color:' + colors[k++ % colors.length] + '"><td align="center">';
								html += flag + '</td><td>';
								if (derivations[flag].length > 0){
									html += derivations[flag][0];
			 						for(var l = 1; l < derivations[flag].length; l++){
			 							html += ', ' + derivations[flag][l];	
			 						}
		 						}
		 						html += '</td><td align="center">'
								if (patchState == "OPEN" && canApprove) {
									if (flag.length == 1) {
		 								html += '<input name="flags[]" value="' + flag + '" class="flagscheckbox'+idPatch+'" checked="checked" type="checkbox"';
			 							html += 'onchange="$(\'#checkAllFlags'+idPatch+'\').attr(\'checked\', false);">';
									}
									else {
										html += '*fc';
										obs = true;
									}
								}
		 						html += '</td></tr>';
							}
						} else {
							html += '<tr><td align="center" colspan="3" style="background-color:#B9B9B9">Não há derivações dessa palavra</td></tr>';
						}
						
						if (canApprove) {
							html += '<tr><td colspan="3">';
							if (obs == true) {
								html += '*fc = flags combinadas (são aprovadas se ambas isoladamente também forem)';
							}
							html += '<input name="idPatch" type="hidden" value="' + idPatch + '"></td></tr>';
						}
						html += '</table>';
						var disapproval_url = '<c:url value="/patchDisapproval"/>';

						if (patchState == "OPEN" && canApprove) {
							html += '<div style="margin-bottom: 30px;"><input style="float: right; margin-left: 20px;" value="Desaprovar" type="submit" onclick="submitForm()">';
							html += '<input style="float: right;" value="Aprovar" type="submit"></div>';
						}
						html += '</form>';
						bufferDetails[idPatch] = html;
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
			{ "bSortable": false },		//7
		],
		
		
		"fnDrawCallback": function ( oSettings ) {
			$('#entriesList tbody tr').each( function () {
				var title = $(this).attr('title');
				$(this).click( function () {
					window.location = title;
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
				displayPatchDetails (nTr, $(this).attr('idPatch'), ${loggedUser.approvedUser}, $(this).attr('patchState'));
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

<table class="display" id="entriesList">
		<thead>
			<tr>
			  <th></th> 			<!-- 0 -->
			  <th title="Exibe o número do problema reportado.">Nº.</th>			<!-- 1 -->
			  <th title="Indica a situação (aberta, em andamento, resolvida, aguardando resposta, fechada ou rejeitada) do problema.">Situação</th>				<!-- 2 -->
			  <th title="Exibe a entrada anterior.">Entrada anterior</th>     <!-- 3 -->
			  <th title="Exibe a nova entrada.">Entrada modificada</th>		<!-- 4 -->
			  <th title="Exibe o usuário que enviou a sugestão.">Enviado por</th>  <!-- 5 -->
			  <th title="Exibe a data da última alteração realizada no problema.">Data</th>	<!-- 6 -->
			  <th title="Exibe o número de comentários feitos sobre o problema.">Comentários</th>	<!-- 7 -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dictionaryPatchList}" var="patch" varStatus="i">
				<c:if test="${patch.isNew}">
					<tr id="tr_dictionaryPatch_${ i.count }" class="highlighted" title="<c:url value="/dictionaryPatchDetails/${patch.id}"/>">
				</c:if>
				<c:if test="${not patch.isNew}">
					<tr id="tr_dictionaryPatch_${ i.count }" title="<c:url value="/dictionaryEntries/${patch.id}"/>">
				</c:if>
					<td valign="middle"><img src="./images/details_open.png" idPatch=${patch.id} patchState=${patch.state}></td>		<!-- 0 -->
					<td><a href="<c:url value="/dictionaryEntries/${patch.id}"/>">${patch.id}</a></td>		<!-- 1 -->
					<td><fmt:message key="${patch.state}" /></td>					<!-- 2 -->
					<td>${patch.previousEntry}</td>			<!-- 3 -->
					<td>${patch.newEntry}</td>			<!-- 4 -->
	  			  	<td><a href="<c:url value="/users/${patch.user.service}/${patch.user.login}"/>">${patch.user.name}</a></td><!-- 5 -->
					<td><span title="${patch.modified}"></span><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${patch.modified}" /></td>		<!-- 6 -->
					<td>${patch.commentCount}</td>									<!-- 7 -->
			</c:forEach>
		</tbody>
	</table>