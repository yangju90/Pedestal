package indi.mat.work.project.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.pattern.*;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;

import java.util.Map;

public class LoggingJsonConfiguration extends ContextAwareBase implements Configurator {

    @Override
    public void configure(LoggerContext loggerContext) {
        addInfo("Setting up LoggingJson configuration.");

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("console");
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = new LayoutWrappingEncoder<ILoggingEvent>();
        layoutWrappingEncoder.setContext(loggerContext);

        PatternLayout layout = new PatternLayout();
        resetConverterMap(layout.DEFAULT_CONVERTER_MAP);
        resetConverterClass(layout.CONVERTER_CLASS_TO_KEY_MAP);

        layout.setPattern("{\"timestamp\": \"%d{yyyy-MM-dd HH:mm:ss.SSS, UTC}\", \"level\": \"%-5p\", \"thread\": \"%t\", \"class\": \"%-40.40logger{39}\", \"message\": \"%m\", \"exception\": \"%ex\"}%n");
//        TTLLLayout layout = new TTLLLayout();

        layout.setContext(loggerContext);
        layout.start();
        layoutWrappingEncoder.setLayout(layout);

        consoleAppender.setEncoder(layoutWrappingEncoder);
        consoleAppender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(consoleAppender);
        rootLogger.setLevel(Level.ERROR);
        loggerContext.putObject("org.springframework.boot.logging.LoggingSystem", new Object());
    }

    private void resetConverterMap(Map<String, String> map){
        map.put("ex", ThrowableProxyConverter.class.getName());
        map.put("exception", ThrowableProxyConverter.class.getName());
        map.put("rEx", RootCauseFirstThrowableProxyConverter.class.getName());
        map.put("rootException", RootCauseFirstThrowableProxyConverter.class.getName());
        map.put("throwable", ThrowableProxyConverter.class.getName());

        map.put("xEx", ExtendedThrowableProxyConverter.class.getName());
        map.put("xException", ExtendedThrowableProxyConverter.class.getName());
        map.put("xThrowable", ExtendedThrowableProxyConverter.class.getName());

        map.put("nopex", NopThrowableInformationConverter.class.getName());
        map.put("nopexception", NopThrowableInformationConverter.class.getName());

        map.put("m", MessageConverter.class.getName());
        map.put("msg", MessageConverter.class.getName());
        map.put("message", MessageConverter.class.getName());
    }

    private void resetConverterClass(Map<String, String> map){
        map.put(MessageConverter.class.getName(), "message");
    }

}
