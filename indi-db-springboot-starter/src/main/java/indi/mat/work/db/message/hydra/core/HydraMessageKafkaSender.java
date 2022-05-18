package com.newegg.logistics.hydra.core;

import com.newegg.logistics.util.JsonUtil;
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
 *
 * @author mm92
 * @since 1.3.3 2020-01-11
 */
public class HydraMessageKafkaSender implements HydraMessageSender {

    private KafkaProducer<String, String> hydraKafkaProducer;

    private HydraProperties hydraProperties;

    private final Log LOG = LogFactory.getLog(getClass());

    public HydraMessageKafkaSender(KafkaProducer<String, String> hydraKafkaProducer,HydraProperties hydraProperties) {
        this.hydraKafkaProducer = hydraKafkaProducer;
        this.hydraProperties = hydraProperties;
    }


    @Override
    public void sendStartMsg(String instanceName,String userName,String sqlText, List<HydraSqlParameterBean> parameterBeans) {
        HydraMessageBaseBean baseMessage = buildBaseMessage(instanceName,userName);
        HydraOperationMessageStartBean hydraMqMessageStartBean = new HydraOperationMessageStartBean();
        BeanUtils.copyProperties(baseMessage, hydraMqMessageStartBean);
        hydraMqMessageStartBean.setSqlContent(sqlText);
        hydraMqMessageStartBean.setParameters(parameterBeans);
        sendMsg(hydraMqMessageStartBean);
    }

    @Override
    public void sendEndMsg(boolean success, String errorMessage, String stackTrace) {
        HydraMessageBaseBean baseMessage = HydraGatewayRequestHeaderContextHolder.getMsgLocal().get();
        if(baseMessage == null){
            //TODO
            return;
        }
        HydraOperationMessageEndBean endBean = new HydraOperationMessageEndBean();
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


    private void sendMsg(HydraMessageBaseBean mes) {
        HydraKafkaMessgeKeyBean kafkaKeyBean = new HydraKafkaMessgeKeyBean("Operation", mes.getDbTraceID(), hydraProperties.getVersion());
        kafkaKeyBean.setSource(hydraProperties.getOrm());
        kafkaKeyBean.setLanguage(hydraProperties.getLanguage());
        String keyMes = JsonUtil.toJsonString(kafkaKeyBean);
        String body = JsonUtil.toJsonString(mes);
        if (StringUtils.hasText(keyMes) && StringUtils.hasText(body)) {
            hydraKafkaProducer.send(new ProducerRecord<>(String.valueOf(hydraProperties.getKafkaProducer().get("topic")), keyMes, body), new HydraKafkaProducerCallback(keyMes, body, LOG));
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
    public HydraMessageBaseBean buildBaseMessage(String dbInstanceName,String userName) {
        HydraMessageBaseBean baseBean = new HydraMessageBaseBean();
        long commandTimeout = 0L;
        baseBean.setServiceID(hydraProperties.getServiceId());
        baseBean.setDbTraceID(UUID.randomUUID().toString());
        baseBean.setScriptName(HydraGatewayRequestHeaderContextHolder.getMethodName().get());
        baseBean.setDbInstance(dbInstanceName);
        baseBean.setHostName(getHostName());
        baseBean.setStartTimeUtc(Instant.now(Clock.systemUTC()).toEpochMilli());
        baseBean.setCommandTimeout(commandTimeout);
        baseBean.setServer(dbInstanceName);
        baseBean.setUserID(userName);
        // 获取header 信息
        Map<String, String> headerMap = HydraGatewayRequestHeaderContextHolder.getRequestHeaders();
        if (null != headerMap) {
            String traceId = headerMap.get(HydraGatewayRequestHeaderConstants.TRACEID);
            String spanId = headerMap.get(HydraGatewayRequestHeaderConstants.SPANID);
            if(!StringUtils.hasText(traceId)){
                LOG.debug("traceId:"+traceId+"----spanId:"+spanId);
            }
            baseBean.setTraceID(StringUtils.hasText(traceId) ? traceId : "");
            baseBean.setSpanID(StringUtils.hasText(spanId) ? spanId : "");
        }
        HydraGatewayRequestHeaderContextHolder.getMsgLocal().set(baseBean);
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
