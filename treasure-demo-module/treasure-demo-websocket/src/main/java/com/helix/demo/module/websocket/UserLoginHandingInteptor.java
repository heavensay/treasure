package com.helix.demo.module.websocket;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 拦截器用来管理握手和握手后的事情，我们可以通过请求信息，
 * 比如token、或者session判用户是否可以连接，这样就能够防范非法用户
 * @author lijianyu
 * @date 2023/8/21 21:09
 */
public class UserLoginHandingInteptor extends HttpSessionHandshakeInterceptor {

    /**
     * 握手前执行
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("beforeHandshake：");

        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;

            HttpSession session = servletRequest.getServletRequest().getSession();
            if(session != null){
//                UserInfo userInfo = (UserInfo) session.getAttribute("USER_INFO");
//                request.
//                attributes.put("WEBSOCKET_ID", userInfo.getId());
//                System.out.println("用户ID为"+userInfo.getId());
                System.out.println(session.getId());
            }
            System.out.println("getQueryString:"+ JSON.toJSON(servletRequest.getServletRequest().getQueryString()));
            System.out.println("getHeaderNames："+JSON.toJSON(servletRequest.getServletRequest().getHeaderNames()));
            response.setStatusCode(HttpStatus.ACCEPTED);
            serverHttpResponse.getServletResponse().getWriter().write("dddd");
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    /**
     * 握手后执行
     * @param request
     * @param response
     * @param wsHandler
     * @param ex
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
