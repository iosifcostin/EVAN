<%
    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");
    if (o != null) {
        response.sendRedirect("userInfo.jsp");
    }

%>
<html>
<head>
    <meta charset="UTF-8">
    <title>EVAN</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/styleForRegAndLog.css">
    <link rel="stylesheet" href="css/styleForIndex.css">

</head>
<body class="center">

<div class="row">

    <div class="column side">
        <p>Bine ai venit la <em>EVAN</em> <br> asistentul tau in evaluarea statusului nutritional</p>
        <br>
        <p style="font-size: 20px">
            <em>Metabolismul bazal</em> corespunde cheltuielii energetice necesare mentinerii unor activitati
            fiziologice de baza: respiratia, activitatea inimii, functia rinichiului, activitatea cerebrala, echilibrul
            termic, echilibrul osmotic (al presiunii).

            19% din aceasta energie este folosite pentru necesitatile metabolice ale sistemului nervos, 29% este
            utilizata de ficat, 10% de catre inima, 7% de catre rinichi, 18% de catre muculatura.

            Rata metabolismului bazal la adultul normal este in medie de 70 kcal/ora (aproximativ 65-70 % din cheltuiala
            energetica totala). Pentru barbati valoarea calculata a metabolismului bazal este de 1 kcal/ora/kgcorp, iar
            la femei este de 0,9 kcal/ora/kgcorp.

            Factorii ce influenteaza metabolismul bazal sunt: varsta, sexul, starea fizica, inaltimea, greutatea,
            factorii de mediu, tipul morfo-functional, diverse stari fiziologice sau patologice.
        </p>

    </div>

    <div class="column middle">
        <div class="header">
            <h1 title="Evaluare nutritionala">EVAN</h1>
            <picture>
                <img src="img/runningman.jpg" alt="evan" style="width: 500px;">
            </picture>
        </div>
    </div>
    <br><br>
    <div>
        <button onclick="document.getElementById('id01').style.display='block' " style="width:150px;">Inregistreaza-te
        </button>
        <div id="id01" class="modal">

            <form class="modal-content animate" action="register" method="post">

                <div class="imgcontainer">
                    <span onclick="document.getElementById('id01').style.display='none'" class="close"
                          title="Inchide fereastra">&times;</span>
                </div>

                <div class="container">
                    <label for="nume">Nume</label>
                    <input type="text" id="nume" name="nume" required="required" placeholder="nume"></br>

                    <label for="prenume">Prenume</label>
                    <input type="text" id="prenume" name="prenume" required="required" placeholder="prenume"> </br>

                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required="required" placeholder="email@domain.com"></br>

                    <label for="parola">Parola</label>
                    <input type="password" id="parola" name="parola" required="required" placeholder="parola"></br>

                    <label for="dataNasterii">Data nasterii</label>
                    <input type="date" id="dataNasterii" name="dataNasterii" required="required"> </br>

                    <input type="radio" name="sex" value="Masculin">Masculin <br><br>
                    <input type="radio" name="sex" value="Feminin">Feminin


                    <div class="clearfix">
                        <button type="button" onclick="document.getElementById('id01').style.display='none'"
                                class="cancelbtn">Renunta
                        </button>
                        <button type="submit" class="signupbtn">Inscrie-te</button>
                    </div>
                </div>

            </form>
        </div>

        <button onclick="document.getElementById('id02').style.display='block'" style="width:150px;">Conecteaza-te
        </button>

        <div id="id02" class="login">

            <form class="login-content animate" action="connect" method="post">
                <div class="imgcontainer">
                    <span onclick="document.getElementById('id02').style.display='none'" class="close"
                          title="Inchide fereastra">&times;</span>
                </div>

                <div class="container">
                    <label for="mail"><b>Email</b></label>
                    <input id="mail" type="email" placeholder="@email.com" name="mail" required>

                    <label for="psw"><b>Parola</b></label>
                    <input id="psw" type="password" placeholder="parola" name="psw" required>
                </div>

                <div class="clearfixr">
                    <button class="cancelbtn" type="button"
                            onclick="document.getElementById('id02').style.display='none'">Renunta
                    </button>
                    <button class="signupbtn" type="submit">Conecteaza-te</button>
                    <a style="color: white" href="password.jsp">Am uitat parola</a>
                </div>
            </form>
        </div>


        <br><br><br><br>
        <p style="font-size: 20px"><em>Indicele de masa corporala </em>- IMC - este un indicator oficial de calculare a
            greutatii corporale ideale,
            pentru o inaltime data. In primul rand, indicele de masa corporala ajuta la stabilirea grupei de greutate in
            care se incadreaza o persoana - gradul de obezitate. De asemenea, indicele de masa corporala poate fi
            folosit pentru calcularea numarului de kilograme pe care o persoana trebuie sa le piarda sau sa le castige,
            pentru a ajunge la greutatea ideala, pentru inaltimea sa.
            Indicele de masa corporala este o metoda care poate fi folosita atat de barbati cat si de femei, cu varsta
            cuprinsa intre 18 si 65 ani. Indicele de masa corporala nu este o metoda de calcul a greutatii potrivita
            pentru copii, femei insarcinate, persoane cu masa musculara mare (sportivi) si varstnici si nu este menita
            stabilirii procentului de grasime corporala, a masei musculare sau osoase.
        </p>

        <div class="inner">
            <p style="font-size: 22px">Esti pregatit sa afli care este necesarul tau zilnic de calorii?</p>
            <center>
                <a href="start.jsp">
                    <button class="button" style="vertical-align:middle"><span>Start aici</span></button>
                </a>
            </center>
        </div>

    </div>

    <script>
        // Get the modal
        var modal = document.getElementById('id01');

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }


        var login = document.getElementById('id02');

        window.onclick = function (event) {
            if (event.target == login) {
                login.style.display = "none";
            }
        }


    </script>

</body>
</html>