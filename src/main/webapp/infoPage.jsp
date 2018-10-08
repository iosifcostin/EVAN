<%--
  Created by IntelliJ IDEA.
  User: ysf
  Date: 8/23/2018
  Time: 12:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");

    if (o == null) {
        response.sendRedirect("index.jsp");
    }

    Object n = s.getAttribute("uname");

%>
<html>
<head>
    <meta charset="UTF-8">
    <title>EVAN</title>
    <link rel="stylesheet" href="css/styleForIndex.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


</head>
<body class="center">

<div class="row">

    <div class="column side">
        <br><br><br><br>
        <p style="font-size: 20px">Bine ai venit <a title="Mergi la profil" style="text-decoration: none" href="userProfile.jsp">
            <em><%=n%>
            </em></a> <br><br>

            <em>Metabolismul bazal</em> corespunde cheltuielii energetice necesare mentinerii unor activitati
            fiziologice de baza: respiratia, activitatea inimii, functia rinichiului, activitatea cerebrala, echilibrul
            termic, echilibrul osmotic (al presiunii).

            19% din aceasta energie este folosita pentru necesitatile metabolice ale sistemului nervos, 29% este
            utilizata de ficat, 10% de catre inima, 7% de catre rinichi, 18% de catre musculatura.

            Rata metabolismului bazal la un adult normal este in medie de 70 kcal/ora (aproximativ 65-70 % din cheltuiala
            energetica totala). Pentru barbati valoarea calculata a metabolismului bazal este de 1 kcal/ora/kgcorp, iar
            la femei este de 0,9 kcal/ora/kgcorp.

            Factorii ce influenteaza metabolismul bazal sunt: varsta, sexul, starea fizica, inaltimea, greutatea,
            factorii de mediu, tipul morfo-functional, diverse stari fiziologice sau patologice.
        </p>
        <br>

    </div>

    <div class="column middle">
        <div class="header">
            <h1 class="inner" style="color: orangered">EVAN</h1>
            <br>
            <picture>
                <img src="img/runningman.jpg" alt="evan" style="width: 350px;">
            </picture>
        </div>

        <p style="font-size: 30px">Calculeaza-ti necesarul zilnic de calorii</p>
        <br>
        <center>
            <a href="user.jsp">
                <button class="button" style="vertical-align:middle"><span>Calculator</span></button>
            </a>
        </center>
    </div>
    <br><br>

    <div class="dropdown">
        <br><br>
        <button onclick="myFunction()" class="dropbtn"><i class="fa fa-bars"></i></button>
        <div id="myDropdown" class="dropdown-content">
            <a href="userProfile.jsp">Profilul meu</a>
            <a href="user.jsp">Calculator</a>
            <a href="chat.jsp">Chat</a>
            <a href="logout">Deconectare</a>
        </div>
    </div>
    <a href="userProfile.jsp"><h2 title="Mergi la profil" style="float: left" class="header"><%=n%>
    </h2></a>
    <br><br><br>
    <div>
        <p style="font-size: 20px"><em>Indicele de masa corporala </em>- IMC - este un indicator oficial de calculare a
            greutatii corporale ideale,
            pentru o inaltime data. In primul rand, indicele de masa corporala ajuta la stabilirea grupei de greutate in
            care se incadreaza o persoana - gradul de obezitate. De asemenea, indicele de masa corporala poate fi
            folosit pentru calcularea numarului de kilograme pe care o persoana trebuie sa le piarda sau sa le castige,
            pentru a ajunge la greutatea ideala, pentru inaltimea sa.
            Indicele de masa corporala este o metoda care poate fi folosita atat de barbati cat si de femei, cu varsta
            cuprinsa intre 18 si 65 ani. Indicele de masa corporala nu este o metoda de calcul a greutatii potrivita
            pentru copii, femei insarcinate, persoane cu masa musculara mare (sportivi) si varstnici si nu este menita
            stabilirii procentului de grasime corporala, a masei musculare sau osoase.</p>
    </div>
</div>

<script>
    /* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
    function myFunction() {
        document.getElementById("myDropdown").classList.toggle("show");
    }

    // Close the dropdown if the user clicks outside of it
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
</script>
</body>
</html>
