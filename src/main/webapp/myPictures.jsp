<% HttpSession s = request.getSession();

    Object o = s.getAttribute("userid");

    if (o == null) {
        response.sendRedirect("index.jsp");
    }

    Object images = s.getAttribute("images");

    String formattedString = images.toString()
            .replace(",", "")  //remove the commas
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim();           //remove trailing spaces from partially initialized arrays

    Object imagesSize = s.getAttribute("size");
%>
<html class="body" style="color: white">
<head>
    <title>EVAN</title>
    <link rel="stylesheet" href="css/styleForMyPictures.css">
    <link rel="stylesheet" href="css/cssMyPic.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
</head>
<body>
<a href="userInfo.jsp">
    <button class="btn"><i class="fa fa-home"></i></button>
</a>
<a href="trash.jsp">
    <button class="btn"><i class="fa fa-trash"></i></button>
</a>
<br><br>

<form action="upload" method="post" enctype="multipart/form-data">
    <div class="file-upload">
        <div class="file-select">
            <div class="file-select-button" id="fileName">Alege o poza</div>
            <div class="file-select-name" id="noFile">Nici o poza...</div>
            <input type="file" name="chooseFile" id="chooseFile">
        </div>
        <div>
            <input type="submit" value="Incarca"/>
        </div>
    </div>

</form>
<div>
    <div id="pics">
        <%=formattedString%>
    </div>

    <div style="text-align: center" id="myModal" class="modal">
        <span class="close">&times;</span>
        <img class="modal-content" id="img01">
        <br>
        <button class="button" onclick="remove()">Sterge</button>
        <button class="button" onclick="profilePic()">Fotografie de profil</button>
        <div id="caption"></div>
    </div>

</div>

</body>
<script>


    var modal = document.getElementById('myModal');

    function profilePic() {

        var path = modalImg.src;

        $.ajax({
            url: '/manipulate?action=set&path=' + path.replace('http://localhost:8080', '..')
        }).done(function (response) {
            location.href = "userInfo.jsp";
        });
    }

    for (var i = 0; i < <%=imagesSize%>; i++) {

        var img = document.getElementById('myImg' + i);

        img.onclick = function () {
            modal.style.display = "block";
            modalImg.src = this.src;
            captionText.innerHTML = this.alt;
        };

    }
    var modalImg = document.getElementById("img01");

    var captionText = document.getElementById("caption");

    var span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";
    };

    function remove() {

        var path = modalImg.src;

        $.ajax({
            url: '/manipulate?action=delete&path=' + path.replace('http://localhost:8080', '..')
        }).done(function (response) {
            location.href = "myPictures.jsp";
        });
    }

    $('#chooseFile').bind('change', function () {
        var filename = $("#chooseFile").val();
        if (/^\s*$/.test(filename)) {
            $(".file-upload").removeClass('active');
            $("#noFile").text("No file chosen...");
        }
        else {
            $(".file-upload").addClass('active');
            $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
        }
    });


</script>
</html>
