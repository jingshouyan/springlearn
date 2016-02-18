<!DOCTYPE HTML>
<html>
<head>
	<title>Java API for WebSocket (JSR-356)</title>  
</head>
<body>
<script type="text/javascript" src="//lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="//libs.cdnjs.net/sockjs-client/1.0.3/sockjs.min.js"></script>
<script type="text/javascript">
	var websocket = null;
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://localhost:8080/springlearn/websocket/chatMessageServer.do");
	} 
	else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://localhost:8080/springlearn/websocket/chatMessageServer.do");
	} 
	else {
		websocket = new SockJS("http://localhost:8080/springlearn/sockjs/chatMessageServer.do");
	}
	websocket.onopen = onOpen;
	websocket.onmessage = onMessage;
	websocket.onerror = onError;
	websocket.onclose = onClose;
	
	console.log(websocket);
	function onOpen(openEvt) {
		console.log(openEvt);
		console.log(openEvt.Data);
		//alert(openEvt.Data);
	}
	
	function onMessage(evt) {
		console.log(new Date());
		//console.log(evt.data);
		//alert(evt.data);
	}
	function onError() {}
	function onClose() {}
	
	function doSend() {
		if (websocket.readyState == 1) {  		
            var msg = document.getElementById("inputMsg").value; 
            var m = {message:msg,date: new Date()}; 
            websocket.send(JSON.stringify(m));//调用后台handleTextMessage方法
            alert("发送成功!");  
        } else {  
        	alert("连接失败!");  
        }  
	}
</script>
请输入：<textarea rows="5" cols="10" id="inputMsg" name="inputMsg"></textarea>
<button onclick="doSend();">发送</button>
</body>
</html>