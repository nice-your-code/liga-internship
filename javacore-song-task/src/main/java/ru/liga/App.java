package ru.liga;

import ru.liga.songtask.workers.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static Logger logger = LoggerFactory.getLogger("std");
    public static void main(String[] args) {
        CommandHandler.run(args);
    }
}
