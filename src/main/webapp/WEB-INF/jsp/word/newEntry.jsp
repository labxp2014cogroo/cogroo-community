<form action="<c:url value="/chooseCategory"/>" method="post">
	<div class="yellow_box">
		<h1>Inserindo a palavra: "${word}"</h1>
		<div class="dashed_white">
		<h3>Manual de inserção:</h3>
		<br>
		<h5>Procure sempre buscar a forma mais primitiva da palavra:</h5>
			<br>
			<ul>
				<li>Para substantivos e adjetivos, flexione a palavra:
					<ol>
						<li>No gênero masculino, se existir</li>
						<li>No singular, se houver</li>
						<li>Retirando qualquer grau da palavra</li>
					</ol>
				</li>
				<br>
				<li>Para verbos:
					<ol>
						<li>Coloque-o no infinitivo impessoal (ex: cantar)</li>
					</ol>
				</li>
			</ul>
		<br>
		<input type="text" value="${word }" name="word"/>
		<br>
		</div>
	</div>
	<div class="white_box">
		<h2>Classifique a palavra sintaticamente:</h2>
		<div class="dashed_white">
			<input type="radio" name="category" value="nc" />Substantivo simples<br>
			<input type="radio" name="category" value="np" />Substantivo próprio<br>
			<input type="radio" name="category" value="adj" />Adjetivo<br>
			<input type="radio" name="category" value="a_nc" />Substantivo simples e adjetivo<br>
			<input type="radio" name="category" value="v" />Verbo<br>
			<input type="radio" name="category" value="adv" />Advérbio<br>
		</div>
	</div>
	<br>
	<div style="clear:both;">
		<input type="submit" value=" OK &raquo; " id="go" />
	</div>
</form>