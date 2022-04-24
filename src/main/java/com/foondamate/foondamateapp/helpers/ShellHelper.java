package com.foondamate.foondamateapp.helpers;

import lombok.Getter;
import lombok.Setter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class ShellHelper {

    @Value("${shell.out.info}")
    private String infoColor;

    @Value("${shell.out.success}")
    private String successColor;

    @Value("${shell.out.warning}")
    private String warningColor;

    @Value("${shell.out.error}")
    private String errorColor;

    private Terminal terminal;

    public ShellHelper(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Generic Print to the console method.
     *
     * @param message message to print
     * @param color   (optional) prompt color
     */
    private void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    private String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String info(String message) {
        return getColored(message, PromptColor.valueOf(infoColor));
    }

    public String success(String message) {
        return getColored(message, PromptColor.valueOf(successColor));
    }

    public String warning(String message) {
        return getColored(message, PromptColor.valueOf(warningColor));
    }

    public String error(String message) {
        return getColored(message, PromptColor.valueOf(errorColor));
    }

    /**
     * Print message to the console in the default color.
     *
     * @param message message to print
     */
    public void print(String message) {
        print(message, null);
    }

    /**
     * Print message to the console in the success color.
     *
     * @param message message to print
     */
    public void printSuccess(String message) {
        print(message, PromptColor.valueOf(successColor));
    }

    /**
     * Print message to the console in the info color.
     *
     * @param message message to print
     */
    public void printInfo(String message) {
        print(message, PromptColor.valueOf(infoColor));
    }

    /**
     * Print message to the console in the warning color.
     *
     * @param message message to print
     */
    public void printWarning(String message) {
        print(message, PromptColor.valueOf(warningColor));
    }

    /**
     * Print message to the console in the error color.
     *
     * @param message message to print
     */
    public void printError(String message) {
        print(message, PromptColor.valueOf(errorColor));
    }
}