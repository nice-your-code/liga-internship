package ru.liga;

import ru.liga.songtask.workers.Analisator;
import ru.liga.songtask.workers.FileWorker;
import ru.liga.songtask.domain.*;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.abs;




public class App {
    private static Logger logger = LoggerFactory.getLogger( App.class );
    public static void main(String[] args) {
        SimpleMidiFile simpleMidiFile = FileWorker.ReadSongFromFile("zombie.mid");
        Analisator.Analise(simpleMidiFile);
    }
}
