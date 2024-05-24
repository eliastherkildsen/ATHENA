package org.apollo.template.Service.Logger;

import org.apollo.template.Service.AnsiColorCode;

/**
 * LogLevel for Logger
 */
public enum LogLevel {
    TRACE(4, AnsiColorCode.PURPLE_BOLD, AnsiColorCode.PURPLE),
    INFO(2, AnsiColorCode.WHITE_BOLD, AnsiColorCode.WHITE),
    DEBUG(3, AnsiColorCode.YELLOW_BOLD, AnsiColorCode.YELLOW),
    ERROR(1, AnsiColorCode.RED_BOLD, AnsiColorCode.RED),
    WARNING(1, AnsiColorCode.RED_BOLD, AnsiColorCode.RED);

    private final int debugLevel;
    private final String colorLevel;
    private final String colorMessage;

    LogLevel(int level, String color, String colorMessage) {
        this.debugLevel = level;
        this.colorLevel = color;
        this.colorMessage = colorMessage;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public String getColorLevel() {
        return colorLevel;
    }

    public String getColorMessage() {
        return colorMessage;
    }
}
