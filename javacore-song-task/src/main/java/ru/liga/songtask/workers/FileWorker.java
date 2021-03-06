package ru.liga.songtask.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.io.File;

/**
 * Created by User on 14.03.2018.
 */
public class FileWorker {
    private static Logger logger = LoggerFactory.getLogger("std");
    public static void writeSongToFile(SimpleMidiFile midi, String filename){
        try{
            midi.getMidiFormat().writeToFile(new File(filename));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static SimpleMidiFile readSongFromFile(String filename){
            return  new SimpleMidiFile(new File(filename));
    }
}
