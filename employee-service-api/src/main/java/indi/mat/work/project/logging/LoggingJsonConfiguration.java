package indi.mat.work.project.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * <p>
 * Json日志配置
 * </p>
 *
 * @author Mat
 * @since 10/2/2021
 */
public class LoggingJsonConfiguration extends ContextAwareBase implements Configurator {

    @Override
    public void configure(LoggerContext loggerContext) {
        LinkedHashMap<String, LinkedHashMap> map = parseYaml("application.yml");
        if (logSwitch(map)) {
            addInfo("Setting up LoggingJson configuration.");

            ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
            consoleAppender.setContext(loggerContext);
            consoleAppender.setName("console");
            LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = new LayoutWrappingEncoder<ILoggingEvent>();
            layoutWrappingEncoder.setContext(loggerContext);
            PatternJsonLayout layout = new PatternJsonLayout();
            layout.setPattern(getPatternOrDefault(map));
            layout.setContext(loggerContext);
            layout.start();
            layoutWrappingEncoder.setLayout(layout);

            consoleAppender.setEncoder(layoutWrappingEncoder);
            consoleAppender.start();

            Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(consoleAppender);
            rootLogger.setLevel(getRootLevelOrDefault(map));
            loggerContext.putObject("org.springframework.boot.logging.LoggingSystem", new Object());
        }
    }

    private LinkedHashMap<String, LinkedHashMap> parseYaml(String yamlName) {
        URL url = LoggingJsonConfiguration.class.getResource("/" + yamlName);
        if (url != null) {
            return new Yaml().load(LoggingJsonConfiguration.class.getResourceAsStream("/" + yamlName));
        }
        String path = LoggingJsonConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.out.println("rootPath: " + path);

        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            path = path.substring(0, path.lastIndexOf("/"));
        }

        System.out.println("path: " + path);
        File file = new File(path + yamlName);
        if (file.exists()) {
            return new Yaml().load(LoggingJsonConfiguration.class.getResourceAsStream(file.getPath()));
        }
        return new LinkedHashMap<>();
    }


    private boolean logSwitch(LinkedHashMap<String, LinkedHashMap> map) {
        LinkedHashMap<String, Object> logging = map.get("logging");
        if (logging != null) {
            String s = (String) logging.get("switch");
            if ("spi".equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private String getPatternOrDefault(LinkedHashMap<String, LinkedHashMap> map) {
        LinkedHashMap<String, Object> logging = map.get("logging");
        if (logging != null) {
            LinkedHashMap<String, String> pattern = (LinkedHashMap<String, String>) logging.get("pattern");
            if (pattern != null) {
                return pattern.get("console");
            }
        }
        return "{\"timestamp\": \"%d{yyyy-MM-dd HH:mm:ss.SSS, UTC}\", \"level\": \"%-5p\", \"thread\": \"%t\", \"class\": \"%-40.40logger{39}\", \"message\": \"%m\", \"exception\": \"%ex\"}";
    }

    private Level getRootLevelOrDefault(LinkedHashMap<String, LinkedHashMap> map) {
        LinkedHashMap<String, Object> logging = map.get("logging");
        if (logging != null) {
            LinkedHashMap<String, String> level = (LinkedHashMap<String, String>) logging.get("level");
            if (level != null) {
                return Level.toLevel(level.get("root"), Level.ERROR);
            }
        }
        return Level.toLevel(null, Level.ERROR);
    }
}
