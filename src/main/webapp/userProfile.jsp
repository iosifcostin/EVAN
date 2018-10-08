<%
    HttpSession s = request.getSession();

    Object o = s.getAttribute("userid");

    if (o == null) {
        response.sendRedirect("index.jsp");
    }

    Object n = s.getAttribute("uname");
    Object listIMC = s.getAttribute("listIMC");
    Object profilePic = s.getAttribute("profilePic");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>EVAN</title>
    <link rel="stylesheet" href="css/styleForIndex.css">
    <link rel="stylesheet" href="css/styleForStart.css">
    <link rel="stylesheet" href="css/styleForRegAndLog.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/series-label.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>


</head>
<body class="center">

<div class="row">

    <div class="column side">
        <a class="inner" href="infoPage.jsp"><img src="img/runningman.jpg" alt="evan" style="width: 100px;"></a>
        <a class="inner" href="infoPage.jsp"><h1 title="Evaluare nutritionala">EVAN</h1></a>

    </div>

    <div class="column middle">
        <div class="header">
            <h3 style="display: inline-block "><%=n%>
            </h3>
            <br>
            <div style="display: inline-block">
                <a class="inner" href="myPictures.jsp"><img src="<%=profilePic%>" alt="my picture" style="width: 180px" height="180px"></a>
            </div>
        </div>
    </div>
    <br><br><br>
    <div class="dropdown">
        <button onclick="myFunction()" class="dropbtn"><i class="fa fa-bars"></i></button>
        <div id="myDropdown" class="dropdown-content">
            <a href="infoPage.jsp">EVAN Start</a>
            <a href="user.jsp">Calculator</a>
            <a href="myPictures.jsp">Pozele mele</a>
            <a href="chat.jsp">Chat</a>
            <a onclick="document.getElementById('id02').style.display='block'" style="width:150px;">
                Schimba parola
            </a>
            <a href="logout">Deconectare</a>

        </div>
    </div>

    <div id="id02" class="login">

        <form class="login-content animate" action="newpassword" method="post">
            <div class="imgcontainer">
                    <span onclick="document.getElementById('id02').style.display='none'" class="close"
                          title="Inchide fereastra">&times;</span>
            </div>

            <div class="container">
                <label for="oldpwd"><b>Vechea parola</b></label>
                <input id="oldpwd" type="password" placeholder="vechea parola" name="oldpwd" required>

                <label for="newpwd"><b>Noua parola</b></label>
                <input id="newpwd" type="password" placeholder="noua parola" name="newpwd" required>
                <div class="clearfix">
                    <button class="cancelbtn" type="submit">Schimba parola</button>
                </div>
            </div>

        </form>
    </div>
</div>
<br>

<div id="varImc"></div>

<div>
    <p>
        Daca IMC - ul dumneavoastra este cuprins intre 18,5 si 24,9 aveti o greutate ideala si cantitatea de grasime
        corporala suficienta
        pentru asigurarea sanatatii optime. Toate acestea sunt asociate cu un trai prelungit si o rata scazuta a
        riscurilor de boli severe.
        Recomand mentinerea greutatii si evitarea situarii in una dintre cele doua extreme ale intervalului IMC.
    </p>
</div>
<style>

</style>

<script>

    function myFunction() {
        document.getElementById("myDropdown").classList.toggle("show");
    }


    window.onclick = function (event) {
        if (!event.target.matches('.dropbtn')) {

            var dropdowns = document.getElementsByClassName("dropdown-content");
            var i;
            for (i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];
                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
        }
    }


    Highcharts.chart('varImc', {

        title: {
            text: 'Variatia IMC-ului dumneavoastra'
        },
        yAxis: {
            title: {
                text: 'Valoarea'
            }
        },
        xAxis: {
            title: {
                text: 'Timp'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        plotOptions: {
            series: {
                label: {
                    connectorAllowed: false
                },
                pointStart: 1
            }
        },

        series: [{
            name: 'IMC',
            data: <%=listIMC%>
        }],

        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }

    });

    var login = document.getElementById('id02');

    window.onclick = function (event) {
        if (event.target == login) {
            login.style.display = "none";
        }
    }

</script>
</body>
</html>
