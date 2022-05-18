package com.newegg.logistics.hydra.core;


import java.util.List;

/**
 * Hydra消息发送器。
 *
 * @author nl67
 * @since 1.2.9 2019-10-22
 */
public interface HydraMessageSender {
    /**
     * if sql ready to execute send message to hydra
     * @param instanceName dbInstantName for example D2HIS 01  or  127.0.0.1:9999
     * @param userName  database username
     * @param sqlText  current sql Text
     * @param parameterBeans sql params
     */
    void sendStartMsg(String instanceName,String userName,String sqlText, List<HydraSqlParameterBean> parameterBeans);

    /**
     * if execution were completed send message to hydra
     * @param success
     * @param errorMessage
     * @param stackTrace
     */
    void sendEndMsg(boolean success, String errorMessage, String stackTrace) ;
}
