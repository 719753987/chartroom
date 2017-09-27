<%--
  Created by IntelliJ IDEA.
  User: icinfo
  Date: 2017-09-08
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>jquery仿微信聊天对话窗口滚动样式</title>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<body>
username:<input type = 'text' id = 'username' /><br/>
password:<input type = 'password' id = 'password' /><br/>
<input type="button" value="登陆" id = 'login'>

<script type="text/javascript">
    $(document).ready(function () {
        $('#login').bind('click',function () {
            var data = {'username':$('#username').val(),'password':$('#password').val()};
            $.ajax(
                {
                    url:'/chartRoom/login',
                    type:'post',
                    contentType:'application/json',
                    dataType:'json',
                    data:JSON.stringify(data),
                    success:function (data) {
                        if(data.status == 'success'){
                            location.href = '/chartRoom/enChartRoom?username=' + data.msg;
                        }else{
                            alert(data.msg);
                        }
                    }
                }
            )
        })
    })
</script>
</body>
</html>
