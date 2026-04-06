package com.helix.demo.iot.mqtt.emq.mqtt5;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.junit.Test;

/**
 * @author lijianyu
 * @date 2023/12/30 22:37
 */
@Slf4j
public class Mqtt5Test {

    String topic = "/Mqtt5Test";

    @Test
    public void simpleConn() throws MqttException {
        MqttClient mqttClient = MqttFactory.createMqttClient("test-1230");
        mqttClient.subscribe(topic, 0);
        MqttMessage message = new MqttMessage("content test".getBytes());
        mqttClient.publish(topic, message);
    }


    @Test
    public void reconn() throws Exception {

        MqttClient mqttClient = MqttFactory.createMqttClient("test-1230");
        mqttClient.subscribe(topic, 0);
        while (true) {
            log.debug("connect status:" + mqttClient.isConnected());
            Thread.sleep(10000L);
        }
    }

    @Test
    public void deliveryComplete() throws Exception {
        MqttClient mqttClient = MqttFactory.createMqttClient("test-1230");
//        mqttClient.subscribe(topic, 1);
        MqttMessage message = new MqttMessage("content test".getBytes());
        message.setQos(2);
        log.debug("publish begin");
        mqttClient.publish(topic, message);
        log.debug("publish finish");
        Thread.sleep(5000L);
    }

    /**
     * IMqttMessageListener作用跟client.setCallback(new MqttCallbackProcess())类似；
     * 但是在message.setQos(1/2)，都能收到消息回调。在message.setQos(0)时候，不会收到回调
     *
     * @throws MqttException
     */
    @Test
    public void subscribeListener() throws MqttException {
        MqttClient mqttClient = MqttFactory.createMqttClient("test-1231");
//        mqttClient.subscribe(topic, 2);

        MqttSubscription mqttSubscription = new MqttSubscription(topic, 2);
        IMqttMessageListener listener = new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                log.debug("IMqttMessageListener messageArrived，,{},{}", topic, message);
            }
        };
        mqttClient.subscribe(new MqttSubscription[]{mqttSubscription}, new IMqttMessageListener[]{listener});

        //自产自销
        MqttMessage message = new MqttMessage("content test".getBytes());
        message.setQos(1);
        mqttClient.publish(topic, message);
    }

    @Test
    public void dd() throws InterruptedException {
        Long begin = System.currentTimeMillis();
//        Thread.sleep(4000L);
        System.out.println((System.currentTimeMillis() + 3 - begin) / 1000);
    }


    /**
     *
     */
    @Test
    public void subandunsub() throws Exception {
        String str[] = new String[]{"255.255.255", "1", "1.0", "1.0.0", "1.0.01",
                "1.0.00", "1.0.1", "1.01.1", "255.1.1", "256.1.1", "0.1.1", "9.100.244", "-1.0.0"};
//        String VERSION_REGEX = "^([1-9]\\d?)(\\.(0|([1-9]\\d{0,2})))(\\.(0|([1-9]\\d?)))$";
//        String VERSION_REGEX = "^([1-255]\\d?)(\\.(0|([1-9]\\d{0,2})))(\\.(0|([1-9]\\d?)))$";
        String VERSION_REGEX = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";
        for (String s : str) {
            System.out.println(s + ":" + s.matches(VERSION_REGEX));
        }
    }

    @Test
    public void match() {
        String str[] = new String[]{"255", "1", "0", "10", "01", "256", "1000", "198", "200", "99"};
        String match = "\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]";
        for (String s : str) {
            System.out.println(s + ":" + s.matches(match));
        }
    }
}
