<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");
    if (o != null) {
        response.sendRedirect("user.jsp");
    }
%>

<html class="fontColor">
<head>
    <meta charset="UTF-8">
    <title>EVAN</title>
    <link rel="stylesheet" href="css/styleForStart.css">
    <link rel="stylesheet" href="css/styleForIndex.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body class="center">
<div class="row">
    <div class="column side">
        <a href="index.jsp"><h1 title="Evaluare nutritionala">EVAN</h1></a>
        <br><br>

        <div>
            <p id="Rmb">

            </p>

        </div>
    </div>


    <br><br>


    <div class="column middle">
        <div class="div">
            <p>Introdu datele tale</p>

            <label for="varsta">Varsta</label>
            <input type="number" id="varsta" name="varsta" placeholder="varsta"  required><br>

            <label for="inaltime">Inaltimea in centimetri</label>
            <input type="number" id="inaltime" name="inaltime" placeholder="inaltimea" required><br>

            <label for="masa">Masa in kilograme</label>
            <input type="number" id="masa" name="masa" placeholder="masa" required><br>
            <ul>
                <li>
                    <label for="sex">Sexul</label>
                    <select name="sex" id="sex">
                        <option value="b">Barbatesc</option>
                        <option value="f">Femeiesc</option>
                    </select>
                </li>
                <br>
                <li>
                    <label for="tip">Tipul de activitate</label>
                    <select name="tip" id="tip">
                        <option value="sed">Sedentara</option>
                        <option value="usor">Usoara</option>
                        <option value="mod">Moderata</option>
                        <option value="ints">Intensa</option>
                        <option value="fri">Foarte intensa</option>
                    </select>
                </li>
            </ul>
            <div id="outer">
                <input class="inner" type="button" value="Calcul" onclick="sendInputs()">
            </div>
        </div>
    </div>

    <div>
        <p id="right">

        </p>
        <br>
        <p id="Ncv">

        </p>

    </div>
</div>
<br><br><br>
<div>
    <p id="Imc">

    </p>
</div>
<br><br><br>

<script>

    loadResult();

    function loadResult() {
        $.ajax({
            url: 'calculator?action=read'
        }).done(function (response) {
            putResultInPage(response.imc);
            putMesageInPage(response.inf);
            putRmbInPage(response.rmb);
            putNcInPage(response.nc);
        });
    }

    function putResultInPage(imc) {
        var divIMC = document.getElementById('Imc');
        if (imc != null) {
            divIMC.innerText = imc
        }


    }

    function putRmbInPage(rmb) {
        var divRMB = document.getElementById('Rmb');
        if (rmb != null) {
            divRMB.innerText = rmb
        }


    }

    function putNcInPage(nc) {
        var divNC = document.getElementById('Ncv');
        if (nc != null) {
            divNC.innerText = nc
        }

    }

    function putMesageInPage(inf) {
        var divRight = document.getElementById('right');
        if (inf != null) {
            divRight.innerText = inf
        }

    }

    function sendInputs() {
        var varsta = document.getElementById('varsta').value;
        var inaltime = document.getElementById('inaltime').value;
        var masa = document.getElementById('masa').value;
        var sex = document.getElementById('sex').value;
        var tip = document.getElementById('tip').value;

        $.ajax({
            url: '/calculator?action=write&varsta=' + varsta + "&inaltime="
                + inaltime + '&masa=' + masa + '&sex=' + sex + '&tip=' + tip

        }).done(function (response) {
            location.href = "start.jsp";
        });
    }

</script>

</body>
</html>