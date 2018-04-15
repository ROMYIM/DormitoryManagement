<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<script type="text/javascript">
	function webSocketTest() {
		var host = window.location.host;
		var ws = new WebSocket("ws://" + host + "/DormitoryManagement/webSocket?userId=140201021012");
		ws.onopen = function() {
			console.log("open");
			ws.send("send test");
		}
		ws.onclose = function() {
			console.log("close");
		}
		ws.onmessage = function(msg) {
			console.log("get message");
		}
	}
</script>
</head>
<body>
<p>hello world</p>
</body>
</html>