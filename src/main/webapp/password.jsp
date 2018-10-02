<%
%>
<html>
<head>
    <title>Get a new password</title>
    <link rel="stylesheet" href="css/styleForPassword.css">
</head>
<body>
<form class="modal-content" action="password" method="post">
    <div class="container">
        <h1>Ai uitat parola?</h1>
        <p>Introdu adresa de email pentru care vrei sa resetezi parola</p>
        <hr>
        <label for="email"><b>Email</b></label>
        <input id="email" type="text" placeholder="@email.com" name="email" required>

        <div class="clearfix">
            <button type="button" class="cancelbtn"><a href="index.jsp">Renunta</a></button>
            <button id="x" type="submit" class="signupbtn" onclick="message()">Trimite-mi o parola noua</button>
        </div>
    </div>
</form>


</body>
<script>
    function message() {
        var x = document.getElementById('x').value;
        if (x != null) {
            alert("Vei primi un email cu o noua parola");
        }
    }
</script>
</html>
