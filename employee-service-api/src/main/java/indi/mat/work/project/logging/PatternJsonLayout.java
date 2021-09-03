package indi.mat.work.project.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.pattern.ConverterUtil;
import ch.qos.logback.core.pattern.parser.Node;
import ch.qos.logback.core.pattern.parser.Parser;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.CachingDateFormatter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TimeZone;

public class PatternJsonLayout extends PatternLayout {
    private byte[] lineSeparatorBytes = System.lineSeparator().getBytes();
    Converter<ILoggingEvent> head;

    @Override
    public void start() {
        // 读取配置文件
        String pattern = getPattern();
        if (pattern == null || pattern.length() == 0) {
            addError("Empty or null pattern.");
            return;
        }
        try {
            Parser<ILoggingEvent> p = new Parser<ILoggingEvent>(pattern);
            if (getContext() != null) {
                p.setContext(getContext());
            }
            Node t = p.parse();
            this.head = p.compile(t, getEffectiveConverterMap());
            if (postCompileProcessor != null) {
                postCompileProcessor.process(context, head);
            }
            ConverterUtil.setContextForConverters(getContext(), head);
            ConverterUtil.startConverters(this.head);
            super.start();
        } catch (ScanException sce) {
            StatusManager sm = getContext().getStatusManager();
            sm.add(new ErrorStatus("Failed to parse pattern \"" + getPattern() + "\".", this, sce));
        }
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        if (!isStarted()) {
            return CoreConstants.EMPTY_STRING;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);

        try {
            JsonGenerator generator = new JsonFactory().createGenerator(outputStream);
            generator.writeStartObject();
//            this.jsonProviders.writeTo(generator, event);
//            generator.writeObjectField("timestamp", cachingDateFormatter.format(event.getTimeStamp()));
//            generator.writeObjectField("level", null);
//            generator.writeObjectField("thread", null);
//            generator.writeObjectField("class", null);
//
//            generator.writeObjectField("message", event.getFormattedMessage());
//            if(null != event.getThrowableProxy()) {
//                StringBuilder strBuilder = new StringBuilder(128);
//                tpc.write(strBuilder, event);
//                generator.writeObjectField("exception", strBuilder.toString());
//            };
            generator.writeEndObject();
            generator.flush();
            generator.close();

            outputStream.write(this.lineSeparatorBytes);
            return outputStream.toString();
        } catch (IOException e) {
            this.addWarn("Error encountered while encoding log event. Event: " + event, e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException var15) {
                throw new RuntimeException(var15);
            }
        }

        return CoreConstants.EMPTY_STRING;
    }

}
