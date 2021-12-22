var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/webs');
    stompClient = Stomp.over(socket);
    socket.onclose = function() {
        console.log('close');
        stompClient.disconnect();
        location.reload();
    };
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/webs', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/webs", {}, JSON.stringify({'id':'null','author':$("#author").val(),'content': $("#name").val(),'time':'null'}));
    $("#name").val("")
}

function showGreeting(message) {
    var x=document.getElementById("user").innerText
    console.log(message.split(":")[0]+"=="+x)
    if(message.split(":")[0]==x){
        $("#greetings").append("" +
            "<div class=\"media media-chat\"> <i class=\"fa fa-user\"></i><div class=\"media-body\"> <p>"+message+"</p> <p class=\"meta\"></p></div>"
        );
    }else{
        $("#greetings").append("" +
            "<div class=\"media media-chat media-chat-reverse\"> <i class=\"fa fa-user\"></i><div class=\"media-body\"> <p>"+message+"</p> <p class=\"meta\"></p></div>"
        );
    }
    document.getElementById("conversation").scrollBy(0, document.getElementById("conversation").scrollHeight);

}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});