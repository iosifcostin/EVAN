<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");

    if (o == null) {
        response.sendRedirect("index.jsp");
    }
%>


<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<html>
<head>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css"
          rel="stylesheet">
    <link rel="stylesheet" href="css/styleForChat.css">
</head>
<body>
<div class="container">
    <h3 class=" text-center">Chat</h3>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="mesgs">
                <div class="type_msg">
                    <div class="input_msg_write">
                        <input id="message" type="text" class="write_msg" placeholder="Type a message"/>
                        <button onclick="sendMessage()" class="msg_send_btn" type="button"><i
                                class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>

                <div id="chat" class="msg_history">

                </div>

            </div>
        </div>
    </div>
</div>
</body>
<script>

    loadResult();

    function loadResult() {
        $.ajax({
            url: 'chat?action=read'
        }).done(function (response) {
            chat(response.chat);
        });
    }

    function chat(chat) {
        var divChat = document.getElementById('chat');
        if (chat != null) {
            divChat.innerHTML = chat
        }

    }

    function sendMessage() {
        var message = document.getElementById('message').value;

        $.ajax({
            url: '/chat?action=write&message=' + message

        }).done(function (response) {
            location.href = "chat.jsp";
        });
    }

</script>
</html>