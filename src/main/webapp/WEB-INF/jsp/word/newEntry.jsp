<h2>Inserir Palavra</h2>

${word}
<br>
<br>

<form id="word" action="<c:url value="/insertEntry?word=${word}"/>" method="post">
	<div style="float:left;">
		
		<h4>Classifique a palavra sintaticamente:</h4>
		<br>
		<br>
		<input type="radio" name="dictionaryEntry.category" value="COMMON_NOUN" />Substantivo simples<br>
		<input type="radio" name="dictionaryEntry.category" value="PROPER_NOUN" />Substantivo próprio<br>
		<input type="radio" name="dictionaryEntry.category" value="ADJECTIVE" />Adjetivo<br>
		<input type="radio" name="dictionaryEntry.category" value="VERB" />Substantivo simples e adjetivo<br>
		<input type="radio" name="dictionaryEntry.category" value="VERB" />Verbo<br>
		<input type="radio" name="dictionaryEntry.category" value="ADJECTIVE" />Outra classe gramatical<br>
		<br>
	</div>
	
	<div id="word" style="float:right;">
		<h4>Inserindo a palavra corretamente:</h4>
		<br>
		<br>
		<h5>Procure sempre buscar a forma mais primitiva da palavra:</h5>
			<ul>
				<li>Para substantivos e adjetivos:
					<ol>
						<li>Coloque-o no gênero masculino, se existir</li>
						<li>Coloque-o no singular, se existir</li>
						<li>Retire qualquer grau da palavra</li>
					</ol>
				</li>
				<li>Para verbos:
					<ol>
						<li>Coloque-o no infinitivo impessoal (ex: cantar)</li>
					</ol>
				</li>
			</ul>
		<input type="text" value="Palavra conforme manual..." name="text" id="text"/>
		<br>
	</div>
	<br>
	<br>
	<div style="clear:both;">
		<input type="submit" value=" OK &raquo; " id="go" />
	</div>
</form>