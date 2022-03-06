package com.example.demo.service.serviceImpl;

import com.example.demo.entity.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userName}/{roleId}")
@Component
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userName*/
    private String userName="";
    /**接收roleId*/
    private Integer roleId=-1;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName,@PathParam("roleId")Integer roleId) {
        if(!webSocketMap.containsKey(userName))
        {
            addOnlineCount(); // 在线数 +1
        }
        this.session = session;
        this.userName= userName;
        this.roleId = roleId;
        WebSocketClient client = new WebSocketClient();
        client.setSession(session);
        client.setUri(session.getRequestURI().toString());
        client.setRoleId(roleId);
        webSocketMap.put(userName, client);

        log.info("----------------------------------------------------------------------------");
        log.info("用户连接:"+userName+"角色id"+roleId+",当前在线人数为:" + getOnlineCount());
        sendAllMessage(userName,userName+"已登录");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userName)){
            webSocketMap.remove(userName);
            if(webSocketMap.size()>0)
            {
                //从set中删除
                subOnlineCount();
            }
        }
        log.info("----------------------------------------------------------------------------");
        log.info(userName+"用户退出,当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到用户消息:"+userName+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
//        if(StringUtils.isNotBlank(message)){
//
//        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userName+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 连接服务器成功后主动推送
     */
    public void sendMessage(String message) throws IOException {
        synchronized (session){
            this.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 向指定客户端发送消息
     * @param userName
     * @param message
     */
    public static void sendMessage(String userName,String message){
        try {
            WebSocketClient webSocketClient = webSocketMap.get(userName);
            if(webSocketClient!=null){
                webSocketClient.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 给所有人员发公告
     * @param message
     */
    public static void sendAllMessage(String userName,String message) {
        try {
            for (Map.Entry entry:webSocketMap.entrySet()) {
//                if (userName.equals(entry.getKey())) continue;
                WebSocketClient client = (WebSocketClient)entry.getValue();
                client.getSession().getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 给厨师发送订单消息
     * @return
     */
    public static void sendMessageToCook(String message){
        try{
            for (WebSocketClient client: webSocketMap.values()){

                if (client.getRoleId() != 2) continue;
                System.out.println("client's roleId"+client.getRoleId());
                client.getSession().getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            log.error("给后厨发送消息失败！");
        }
    }

    /**
     * 给服务员发送订单消息
     * @return
     */
    public static void sendMessageToWaiter(String message){
        try{
            for (WebSocketClient client: webSocketMap.values()){
                if (client.getRoleId() != 3) continue;
                client.getSession().getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            log.error("给服务员发送消息失败！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketService.onlineCount = onlineCount;
    }


    public static ConcurrentHashMap<String, WebSocketClient> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketClient> webSocketMap) {
        WebSocketService.webSocketMap = webSocketMap;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}