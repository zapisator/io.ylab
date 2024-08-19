package io.ylab.frontend.infrastructure.console;

import java.util.Scanner;

/**
 * Класс-помощник для работы с консолью.
 * Предоставляет методы для чтения и  записи строк в консоль.
 */
public class ConsoleHelper {
    /**
     * Сканер для чтения ввода из консоли.
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Читает строку из консоли.
     *
     * @return Считанная строка.
     */
    public String readLine() {
        return scanner.nextLine();
    }
    /**
     * Записывает строку в консоль.
     *
     * @param text Строка для записи.
     */
    public void writeLine(String text) {
        System.out.println(text);
    }
}