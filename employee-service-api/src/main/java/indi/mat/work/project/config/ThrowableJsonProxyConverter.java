package indi.mat.work.project.config;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import net.logstash.logback.composite.JsonWritingUtils;

public class ThrowableJsonProxyConverter extends ThrowableHandlingConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {

        IThrowableProxy tp = iLoggingEvent.getThrowableProxy();

        if (tp != null) {
            String throwableClassName = this.determineClassName(throwable);
            JsonWritingUtils.writeStringField(generator, this.getFieldName(), throwableClassName);
        }
        return null;
    }
}
