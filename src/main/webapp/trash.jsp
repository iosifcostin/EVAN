
<%HttpSession s = request.getSession();

    Object o = s.getAttribute("userid");

    if (o == null) {
        response.sendRedirect("index.jsp");
    }

    Object deletedPics = s.getAttribute("deletedPics");
    String formattedString = deletedPics.toString()
            .replace(",", "")  //remove the commas
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim();           //remove trailing spaces from partially initialized arrays

%>
<html>
<head>
    <title>EVAN</title>
    <link rel="stylesheet" href="css/styleForTrash.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<a href="myPictures.jsp">
    <button class="btn"><i class="fa fa-arrow-circle-o-left"> Inapoi Poze</i></button>
</a>
<a href="userInfo.jsp">
    <button class="btn"><i class="fa fa-home"> Inapoi la profil</i></button>
</a>
<button onclick="remove()" class="btn"><i class="fa fa-trash-o"></i> Sterge tot</button>
<button onclick="restore()" class="btn"><i class="fa fa-undo"></i> Restaureaza</button>

<br><br>
<div>
    <%=formattedString%>
</div>
</body>
<script>
    function remove() {
        $.ajax({
            url: '/manipulate?action=empty&confirmation=ok'
        }).done(function (response) {
            location.href = "trash.jsp";
        });
    }
    function restore(){
        $.ajax({
            url: '/manipulate?action=restore&ok=ok'
        }).done(function (response) {
            location.href = "trash.jsp";
        });
    }
</script>
</html>
