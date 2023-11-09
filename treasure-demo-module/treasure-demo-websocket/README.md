# treasure-demo-websocket

demo例子来自 https://www.cnblogs.com/wupeixuan/p/13389757.html

用户A通过ws，发送信息给用户B；


## 使用说明
可通过postman来进行测试。

用户A
~~~html
ws地址:ws://127.0.0.1:8080/websocket/wupx
参数：{"toUserId":"huxy","message":"i love you"}
~~~

用户B
~~~html
ws地址:ws://127.0.0.1:8080/websocket/huxy
~~~

用户A先发送请求，这时候在用户B界面端可以看到A发送过来的信息