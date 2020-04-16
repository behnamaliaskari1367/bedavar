package com.behdavar.backservices.common.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.EntryMessage;

import java.util.Objects;

/**
 * @author Abbas Alishirvani
 */
public final class LogUtil {

    private static final Logger LOGGER = LogManager.getLogger(LogUtil.class);

    private LogUtil() {
    }

    /**
     * Sets the logging Level
     *
     * @param level the logging Level
     * @param name  the logger name
     */
    public static void setLogLevel(final Level level, final String name) {
        EntryMessage traceEntry = LOGGER.traceEntry("level: {}, name: {}", level, name);

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(name);
        loggerConfig.setLevel(level);
        ctx.updateLoggers();

        LOGGER.traceExit(traceEntry);
    }

    public static String getFormattedMessage(EntryMessage param) {
        EntryMessage traceEntry = LOGGER.traceEntry("getFormattedMessage(traceEntry:{})", param);

        String result = "";
        if (Objects.nonNull(param)) {
            result = param.getFormattedMessage();
        }

        return LOGGER.traceExit(traceEntry, result);
    }

    /**
     * add JdbcAppender to logger name
     *
     * @param productName      to set in column
     * @param appenderName     optional - if it is null default is  'JDBC_APPENDER'
     * @param loggerName       The Logger name
     * @param connectionSource The connections source
     */
    public static void addJDBCAppender(String productName, String appenderName, String loggerName, ConnectionSource connectionSource) {
        EntryMessage traceEntry = LOGGER.traceEntry("addJDBCAppender(productName: {}, appenderName: {}, loggerName: {}, connectionSource: {})", productName, appenderName, loggerName, connectionSource);

        if (Objects.isNull(appenderName)) {
            appenderName = "JDBC_APPENDER";
        }

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        ColumnConfig[] columnConfigs = new ColumnConfig[]{
                ColumnConfig.newBuilder()
                        .setName("CreatedTimeStamp")
                        .setPattern(null)
                        .setLiteral(null)
                        .setEventTimestamp(true)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("Source")
                        .setPattern("%class")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("Line")
                        .setPattern("%L")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("METHOD")
                        .setPattern("%M")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("LLevel")
                        .setPattern("%level")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("Message")
                        .setPattern("%msg")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("Content")
                        .setPattern("%ex")
                        .setLiteral(null)
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build(),
                ColumnConfig.newBuilder()
                        .setName("ProductName")
                        .setPattern(null)
                        .setLiteral("'" + productName + "'")
                        .setEventTimestamp(false)
                        .setUnicode(false)
                        .setClob(false).build()
        };

        Appender jdbcAppender = JdbcAppender.newBuilder()
                .setBufferSize(0)
                .setColumnConfigs(columnConfigs)
                .setColumnMappings()
                .setTableName("BSTBL_LOGS")
                .setName(appenderName)
                .setIgnoreExceptions(true)
                .setFilter(null)
                .setConnectionSource(connectionSource)
                .build();

        jdbcAppender.start();
        config.addAppender(jdbcAppender);

        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
        if (Objects.isNull(loggerConfig)) {
            loggerConfig = LoggerConfig.createLogger(false, Level.WARN, loggerName, null, new AppenderRef[]{}, null, config, null);
        }
        loggerConfig.addAppender(jdbcAppender, null, null);

        ctx.updateLoggers();
    }
}
