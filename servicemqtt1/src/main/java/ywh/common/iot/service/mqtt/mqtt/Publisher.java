package ywh.common.iot.service.mqtt.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ywh.common.mqtt.MQTTPublisher;
import ywh.common.mqtt.MqttConfig;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class Publisher implements MqttConfig, MQTTPublisher,MqttCallback {

    @Autowired
    MqttParameters mqttParameters;

    final private String colon = ":";
    final private String TCP = "tcp://";
    private String brokerUrl = "";
    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;

    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    /**
     * Private default constructor
     */
    public Publisher() {
        this.config();
    }

    /**
     * Private constructor
     */
    public Publisher(String broker, Integer port, Boolean withUserNamePass) {
        this.config(broker, port, withUserNamePass);
    }

    /**
     * Factory method to get instance of MQTTPublisher
     *
     * @return MQTTPublisher
     */
    public static Publisher getInstance() {
        return new Publisher();
    }

    /**
     * Factory method to get instance of MQTTPublisher
     *
     * @param broker
     * @param port
     * @param withUserNamePass
     * @return MQTTPublisher
     */
    public static Publisher getInstance(String broker, Integer port, Boolean withUserNamePass) {
        return new Publisher(broker, port,  withUserNamePass);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjitgroup.jasmysp.mqtt.publisher.MQTTPublisherBase#configurePublisher()
     */
    @Override
    public void config() {

        this.brokerUrl = this.TCP + "118.190.202.155" + colon + "1883";
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence);
            this.connectionOptions.setCleanSession(true);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjitgroup.jasmysp.mqtt.publisher.MQTTPublisherBase#configurePublisher(
     * java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    public void config(String broker, Integer port,  Boolean withUserNamePass) {

        String protocal = this.TCP;
        this.brokerUrl = protocal + broker + colon + port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();

        try {
            this.mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence);
            this.connectionOptions.setCleanSession(true);
            if (true == withUserNamePass) {
                if (mqttParameters.getPassword() != null) {
                    this.connectionOptions.setPassword(mqttParameters.getPassword().toCharArray());
                }
                if (mqttParameters.getUserName() != null) {
                    this.connectionOptions.setUserName(mqttParameters.getUserName());
                }
            }
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }


    /*
     * (non-Javadoc)
     * @see com.monirthought.mqtt.publisher.MQTTPublisherBase#publishMessage(java.lang.String, java.lang.String)
     */
    @Override
    @Async("taskExecutor")
    public void publishMessage(String topic, String message) {

        try {
            MqttMessage mqttmessage = new MqttMessage(message.getBytes());
            mqttmessage.setQos(mqttParameters.getQos());
            this.mqttClient.publish(topic, mqttmessage);
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable arg0) {
        logger.info("Connection Lost");
        try {
            this.mqttClient.connect(this.connectionOptions);
        }catch (Exception e){
            logger.error("ERROR", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        logger.info("delivery completed");

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        // Leave it blank for Publisher

    }

    /*
     * (non-Javadoc)
     * @see com.monirthought.mqtt.publisher.MQTTPublisherBase#disconnect()
     */
    @Override
    public void disconnect() {
        try {
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @PreDestroy
    public void preDestroy(){
        this.disconnect();
        logger.info("disconnect before destroy");
    }


}
