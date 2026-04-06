package com.helix.demo.iot.mqtt.emq.mqtt5;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

/**
 * @author lijianyu
 * @date 2023/12/30 22:41
 */
@Slf4j
public class MqttFactory {
    private static String userName = "admin";
    private static String password = "qnsz2023";
    private static String broker = "tcp://172.25.11.205:1883";

    public static MqttClient createMqttClient(String clientId) {
        MqttClientPersistence persistence = new MemoryPersistence();
        MqttClient client = null;
        try {
            client = new MqttClient(broker, clientId, persistence);

            // MQTT 连接选项
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName(userName);
//            connOpts.setPassword(password.toCharArray());
            connOpts.setPassword(password.getBytes());
            //ping包发送间隔时间默认60s
//            connOpts.setKeepAliveInterval(60);
            /*mqtt自动重连*/
            connOpts.setAutomaticReconnect(true);
            //1秒间隔发送重连请求，每次重连失败，重连间隔double，直到2分钟；之后每2分钟重试一次；第2个参数没起作用，setMaxReconnectDelay能起作用；
            connOpts.setAutomaticReconnectDelay(1, 30);
            //重连最大时间，默认128秒；
            connOpts.setMaxReconnectDelay(30000);
            /*mqtt自动重连*/
//            /*mqtt会话持久保留时间*/
//            connOpts.setCleanStart(false);
//            //x秒内重连上mqtt服务，订阅关系还继续存在
//            connOpts.setSessionExpiryInterval(60*60L);
            /*mqtt会话持久保留时间*/
//            // 设置回调
//            client.setCallback(new MqttCallbackProcess());
            // 建立连接
            log.debug("Connecting to broker:{},clinetid:{} ", broker, clientId);
            client.connect(connOpts);
            client.setCallback(new MqttCallbackProcess());

            log.debug("Connected {}", clientId);
            return client;
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private static class MqttCallbackProcess implements MqttCallback {
        @Override
        public void disconnected(MqttDisconnectResponse disconnectResponse) {
            //创建mqtt连接后，设置了connOpts.setAutomaticReconnect(true);MqttReconnectCallback会重连
            // 连接丢失后，一般在这里面进行重连
            log.debug("连接断开，可以做重连:{}", disconnectResponse);
//            this.connectComplete();
//            if(){
//
//            }
        }

        @Override
        public void mqttErrorOccurred(MqttException exception) {
            log.debug("mqtt mqttErrorOccurred", exception);
        }

        /**
         * 本方法如果抛出异常，那么mqtt客户端将被关闭
         *
         * @param topic   name of the topic on the message was published to
         * @param message the actual message.
         * @throws Exception
         */
        @Override
        public void messageArrived(String topic, MqttMessage message) {
            // subscribe后得到的消息会执行到这里面
            String payload = new String(message.getPayload());
            log.debug("接收消息主题:{},qos:{},内容:{}", topic, message.getQos(), payload);
        }

        @Override
        public void deliveryComplete(IMqttToken token) {
            //当消息的传递已完成并且已接收到所有确认时调用。
            // 对于QoS 0消息，它在消息被传递给网络后被调用。token.getMessage()为null
            // 对于QoS 1，当接收到PUBACK时调用它，token.getMessage()为null
            // 对于QoS 2，当接收到PUBCOMP时调用它。令牌将与发布消息时返回的令牌相同,token.getMessage()才有值；
            try {
                log.debug("deliveryComplete---------{},{},{},{}", token.isComplete(),
                        token.getTopics(), token.getMessage(), token.getRequestMessage());
                log.debug("deliveryComplete2---------{}", new String(token.getRequestMessage().getPayload()));
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            // 是因为如果emq服务出现异常导致客户端断开连接后，重连后会自动调用connectComplete方法
            log.debug("mqtt connectComplete reconnect:{} serverURI:{}", reconnect, serverURI);
        }

        @Override
        public void authPacketArrived(int reasonCode, MqttProperties properties) {
            log.debug("mqtt authPacketArrived:{}", reasonCode);
        }
    }
}
