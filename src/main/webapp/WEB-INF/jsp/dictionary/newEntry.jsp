
<h2>Inserir Palavras</h2>


<p>Digite aqui a palavra a ser inserida no dicionário</p>
<form action="<c:url value="/grammar"/>"  method="post" >
    <textarea rows="1" cols="70" name="text" id="text">${text}</textarea>
    <input type="submit" value=" OK &raquo; " id="go"/>
</form>
