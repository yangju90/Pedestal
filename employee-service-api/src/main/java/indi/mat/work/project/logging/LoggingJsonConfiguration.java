package indi.mat.work.project.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;

public class LoggingJsonConfiguration extends ContextAwareBase implements Configurator {

    @Override
    public void configure(LoggerContext loggerContext) {
        addInfo("Setting up LoggingJson configuration.");

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("console");
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = new LayoutWrappingEncoder<ILoggingEvent>();
        layoutWrappingEncoder.setContext(loggerContext);

        PatternJsonLayout layout = new PatternJsonLayout();
        layout.setPattern("{\"timestamp\": \"%d{yyyy-MM-dd HH:mm:ss.SSS, UTC}\", \"level\": \"%-5p\", \"thread\": \"%t\", \"class\": \"%-40.40logger{39}\", \"message\": \"%m\", \"exception\": \"%ex\"}%n");

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

}
