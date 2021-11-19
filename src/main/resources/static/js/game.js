function SingleGame(num){
    const array=['11','12','13','21','22','23','31','32','33']
    if(document.getElementById(num+"").getAttribute("name")==="0"){
        for (i=0;i<array.length;++i){
            document.getElementById(array[i]+'_').disabled = true
        }
        document.getElementById(num+"").setAttribute("name","1")
        document.getElementById(num+"").setAttribute("src","img/O.svg")
        $.ajax({
            url: 'https://cursework.herokuapp.com/singleplayer',
            type: 'POST',
            data: num+"",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: true,
            success: function(data) {
                console.log(data)
                if (parseInt(data,10)==20){
                    console.log("Win")
                    for(i=1;i<4;++i){
                        for(j=1;j<4;++j){
                            document.getElementById(i+""+j).setAttribute("name","0")
                            document.getElementById(i+""+j).setAttribute("src","img/cell.svg")

                        }
                    }
                    document.getElementById("message1").innerText="You're Win!";
                    showModalWin('popupWin')
                }else if(parseInt(data,10)==40){
                    console.log("Pat")
                    for(i=1;i<4;++i){
                        for(j=1;j<4;++j){
                            document.getElementById(i+""+j).setAttribute("name","0")
                            document.getElementById(i+""+j).setAttribute("src","img/cell.svg")
                        }
                    }
                    document.getElementById("message3").innerText="Dead heat!";
                    showModalWin('popupPat')
                }
                else if(parseInt(data,10)==30){
                    console.log("Ai Win")
                    for(i=1;i<4;++i){
                        for(j=1;j<4;++j){
                            document.getElementById(i+""+j).setAttribute("name","0")
                            document.getElementById(i+""+j).setAttribute("src","img/cell.svg")
                        }
                    }
                    document.getElementById("message2").innerText="You're Lose!";
                    showModalWin('popupLose')
                }
                else{
                    console.log(array[parseInt(data,10)])
                    document.getElementById(array[parseInt(data,10)]).setAttribute("name","2")
                    document.getElementById(array[parseInt(data,10)]).setAttribute("src","img/X.svg")
                }
            }
        });
        for (i=0;i<array.length;++i){
            document.getElementById(array[i]+'_').disabled = false
        }
    }
}
function showModalWin(id) {

    var darkLayer = document.createElement('div'); // слой затемнения
    darkLayer.id = 'shadow'; // id чтобы подхватить стиль
    document.body.appendChild(darkLayer); // включаем затемнение

    var modalWin = document.getElementById(id); // находим наше "окно"
    modalWin.style.display = 'block'; // "включаем" его

    darkLayer.onclick = function () {  // при клике на слой затемнения все исчезнет
        darkLayer.parentNode.removeChild(darkLayer); // удаляем затемнение
        modalWin.style.display = 'none'; // делаем окно невидимым
        return false;
    };
}