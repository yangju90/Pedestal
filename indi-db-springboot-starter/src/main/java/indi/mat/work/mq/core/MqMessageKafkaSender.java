package indi.mat.work.mq.core;

import indi.mat.work.db.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

/**
 * Hydra消息kafka发送器。
 * <p>
 */
public class MqMessageKafkaSender implements MqMessageSender {

    private KafkaProducer<String, String> mqKafkaProducer;

    private MqProperties mqProperties;

    private final Log LOG = LogFactory.getLog(getClass());

    public MqMessageKafkaSender(KafkaProducer<String, String> mqKafkaProducer, MqProperties mqProperties) {
        this.mqKafkaProducer = mqKafkaProducer;
        this.mqProperties = mqProperties;
    }


    @Override
    public void sendStartMsg(String instanceName,String userName,String sqlText, List<MqSqlParameterBean> parameterBeans) {
        MqMessageBaseBean baseMessage = buildBaseMessage(instanceName,userName);
        MqOperationMessageStartBean mqMessageStartBean = new MqOperationMessageStartBean();
        BeanUtils.copyProperties(baseMessage, mqMessageStartBean);
        mqMessageStartBean.setSqlContent(sqlText);
        mqMessageStartBean.setParameters(parameterBeans);
        sendMsg(mqMessageStartBean);
    }

    @Override
    public void sendEndMsg(boolean success, String errorMessage, String stackTrace) {
        MqMessageBaseBean baseMessage = MqGatewayRequestHeaderContextHolder.getMsgLocal().get();
        if(baseMessage == null){
            //TODO
            return;
        }
        MqOperationMessageEndBean endBean = new MqOperationMessageEndBean();
        BeanUtils.copyProperties(baseMessage, endBean);
        endBean.setResult(success?"Succeed":"Failed");
        endBean.setEndTimeUtc(Instant.now(Clock.systemUTC()).toEpochMilli());
        if(!success){
            if (StringUtils.hasText(errorMessage)) {
                endBean.setMessage(errorMessage);
            }
            if (StringUtils.hasText(stackTrace)) {
                endBean.setStackTrace(stackTrace);
            }
        }
        sendMsg(endBean);
    }


    private void sendMsg(MqMessageBaseBean mes) {
        MqKafkaMessgeKeyBean kafkaKeyBean = new MqKafkaMessgeKeyBean("Operation", mes.getDbTraceID(), mqProperties.getVersion());
        kafkaKeyBean.setSource(mqProperties.getOrm());
        kafkaKeyBean.setLanguage(mqProperties.getLanguage());
        String keyMes = JsonUtil.toJsonString(kafkaKeyBean);
        String body = JsonUtil.toJsonString(mes);
        if (StringUtils.hasText(keyMes) && StringUtils.hasText(body)) {
            mqKafkaProducer.send(new ProducerRecord<>(String.valueOf(mqProperties.getKafkaProducer().get("topic")), keyMes, body), new HydraKafkaProducerCallback(keyMes, body, LOG));
        }
    }

    private String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLinux();
        }
    }


    private String getHostNameForLinux() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }

    /**
     *
     * @param dbInstanceName DbInstance Name
     * @param userName
     * @return
     */
    public MqMessageBaseBean buildBaseMessage(String dbInstanceName,String userName) {
        MqMessageBaseBean baseBean = new MqMessageBaseBean();
        long commandTimeout = 0L;
        baseBean.setServiceID(mqProperties.getServiceId());
        baseBean.setDbTraceID(UUID.randomUUID().toString());
        baseBean.setScriptName(MqGatewayRequestHeaderContextHolder.getMethodName().get());
        baseBean.setDbInstance(dbInstanceName);
        baseBean.setHostName(getHostName());
        baseBean.setStartTimeUtc(Instant.now(Clock.systemUTC()).toEpochMilli());
        baseBean.setCommandTimeout(commandTimeout);
        baseBean.setServer(dbInstanceName);
        baseBean.setUserID(userName);
        // 获取header 信息
        Map<String, String> headerMap = MqGatewayRequestHeaderContextHolder.getRequestHeaders();
        if (null != headerMap) {
            String traceId = headerMap.get(MqGatewayRequestHeaderConstants.TRACEID);
            String spanId = headerMap.get(MqGatewayRequestHeaderConstants.SPANID);
            if(!StringUtils.hasText(traceId)){
                LOG.debug("traceId:"+traceId+"----spanId:"+spanId);
            }
            baseBean.setTraceID(StringUtils.hasText(traceId) ? traceId : "");
            baseBean.setSpanID(StringUtils.hasText(spanId) ? spanId : "");
        }
        MqGatewayRequestHeaderContextHolder.getMsgLocal().set(baseBean);
        return baseBean;
    }

    static class HydraKafkaProducerCallback implements Callback {
        private final Log LOG;
        private String key;
        private String value;
        public HydraKafkaProducerCallback(String key, String value, final Log LOG) {
            this.key = key;
            this.value = value;
            this.LOG = LOG;
        }
        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception != null) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Cannot send hydra data to topic [" + metadata.topic() + "] with partition [" + metadata.partition() + "], key [" + key + "], value [" + value + "]", exception);
                }
            }
        }
    }

}
