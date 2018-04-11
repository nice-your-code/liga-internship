package ru.liga.songtask.workers;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.SimpleMidiFile;

/**
 * Created by User on 14.03.2018.
 */
public class CommandHandler {
    private static Logger logger = LoggerFactory.getLogger("std");
    public static void run(String[] args){
        if(args.length < 2){
            throw new RuntimeException("Error! Arguments at least must be 2!");
        }
        String fileName = args[0]; String command = args[1];
        SimpleMidiFile simpleMidiFile = FileWorker.readSongFromFile(fileName);
        if(command.compareTo("analise") == 0){
            String log = "analise";
            boolean inFile = false;
            for(int i=2; i < args.length; i++){
                // TODO throw error if wrong command
                if(args[i].compareTo("-f") == 0){
                    inFile = true;
                    log += " -f";
                }
            }
            logger.info(FilenameUtils.getFullPath(fileName) + " " + log);
            Analisator.analise(simpleMidiFile, fileName, inFile);
        }
        else if(command.compareTo("change") == 0){
            Integer trans = 0, tempo_percent = 0;
            String trans_log = "", tempo_log = "";
            for(int i=2; i < args.length; i++){
                //TODO throw error if wrong command
                if(args[i].compareTo("-trans") == 0){
                    trans = Integer.parseInt(args[++i]);
                    trans_log = "-trans" + trans;
                }
                if(args[i].compareTo("-tempo") == 0){
                    tempo_percent = Integer.parseInt(args[++i]);
                    tempo_log = "-tempo" + tempo_percent;
                }
            }
            logger.info(fileName + " change " + trans_log + " " + tempo_log);
            Changer.change(simpleMidiFile, trans, tempo_percent);
            FileWorker.writeSongToFile(simpleMidiFile, FilenameUtils.getFullPath(fileName) + FilenameUtils.getBaseName(fileName) + trans_log + tempo_log + "." + FilenameUtils.getExtension(fileName));
        }
    }
}
