function сonnect(){
    $.ajax({
        url: 'http://localhost:8080/creategame',
        type: 'POST',
        data: "1",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function (data) {
            if(data!="1"){
                window.location.replace("http://localhost:8080/gameroom/"+data);
            }
            console.log(data)
            setTimeout(сonnect, 1000);
        }

    });
}
setTimeout(сonnect, 1000);
