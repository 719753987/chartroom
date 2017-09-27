$(document).ready(function () {

    $('.talk_send').bind('click', function () {
        send();
    });

    $('#content').keydown(function (event) {
        if (event.keyCode == 13) {
            $('.talk_send').click();
        }
    });

    var sock = new SockJS("http://" + location.host + "/chartroom");

//连接成功建立的回调方法
    sock.onopen = function () {
        // alert("连接成功");
    }

//接收到消息的回调方法
    sock.onmessage = function (event) {
        var messageBody = JSON.parse(event.data);
        if(messageBody.type == 1){
            setMessageInnerHTML(messageBody);
        }else if(messageBody.type == 2){
            $("#onlineCount").text("当前在线" + messageBody.onlineCount + "人");
        }
    }

//连接关闭的回调方法
    sock.onclose = function () {
        // alert("退出连接");
    }

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        sock.close();
    }

//将消息显示在网页上
    function setMessageInnerHTML(messageBody) {
        if ($("#name").val() == messageBody.name) {
            var div = '<div class="talk_recordboxme">';
        } else {
            var div = '<div class="talk_recordbox">';
        }
        div = div +
            '<div class="user"><img src="/images/thumbs/11.jpg"/>' + messageBody.name + '</div>' +
            '<div class="talk_recordtextbg">&nbsp;</div>' +
            '<div class="talk_recordtext">' +
            '<h3>' + messageBody.message + '</h3>' +
            '<span class="talk_time">' + messageBody.sendTime + '</span>' +
            '</div>' +
            '</div>';
        $(".jspPane").append(div);
    }

//发送消息
    function send() {
        var message = $("#content").val();
        $("#content").val("");
        sock.send(message);
    }
})
