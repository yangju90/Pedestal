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
    private static final byte[] LINE_SEPARATOR_BYTES = System.lineSeparator().getBytes();
    private static final Pattern REGEX =  Pattern.compile("[A-Za-z]+");
    private static final List<String> CONVERTER_LIST = new ArrayList<>();
    private static HashMap<String, Converter<ILoggingEvent>> CONVERTER_MAP;

    @Override
    public void start() {
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
            Converter<ILoggingEvent> head = p.compile(t, getEffectiveConverterMap());
            if (postCompileProcessor != null) {
                postCompileProcessor.process(context, head);
            }
            ConverterUtil.setContextForConverters(getContext(), head);
            ConverterUtil.startConverters(head);
            CONVERTER_MAP = converterHashMap(head);
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
            CONVERTER_LIST.forEach(name -> write(generator, name, event));
            generator.writeEndObject();
            generator.flush();
            generator.close();
            outputStream.write(LINE_SEPARATOR_BYTES);
            return outputStream.toString();
        } catch (Throwable e) {
            this.addWarn("Error encountered while encoding log event. Event: " + event, e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException var) {
                throw new RuntimeException(var);
            }
        }

        return CoreConstants.EMPTY_STRING;
    }


    private void write(JsonGenerator generator, String name, ILoggingEvent event) {
        StringBuilder strBuilder = new StringBuilder(128);
        Converter c = CONVERTER_MAP.get(name);
        if(c != null) {
            c.write(strBuilder, event);
        }
        try {
            generator.writeObjectField(name, strBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error encountered while encoding log event." + e);
        }
    }


    private HashMap<String, Converter<ILoggingEvent>> converterHashMap(Converter<ILoggingEvent> head){
        HashMap<String, Converter<ILoggingEvent>> map = new HashMap<>();
        Converter<ILoggingEvent> c = head;
        while(c!= null && c.getNext() != null){
            if(! (c instanceof LiteralConverter)){
                throw new RuntimeException("Error encountered while convert pattern.");
            }
            String name = c.convert(null);
            c = c.getNext();
            Matcher matcher = REGEX.matcher(name);
            if(matcher.find()){
                name = matcher.group(0);
                CONVERTER_LIST.add(name);
                map.put(name, c);
            }
            c = c.getNext();
        }
        return map;
    }

}
