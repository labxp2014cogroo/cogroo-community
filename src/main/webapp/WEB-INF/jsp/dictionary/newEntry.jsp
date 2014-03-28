<h2>Inserir Palavras</h2>


<p>Digite aqui a palavra a ser inserida no dicionário</p>
<form action="<c:url value="/trataPalavra"/>"  method="post" >
    <input type="text" name="word" id="word"><br>
    <input type="submit" value=" OK &raquo; " id="go"/>    
</form>
<br />
${mensagem}
