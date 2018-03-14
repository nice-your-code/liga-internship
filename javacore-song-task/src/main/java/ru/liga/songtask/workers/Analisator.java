package ru.liga.songtask.workers;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;

/**
 * Created by User on 14.03.2018.
 */
public class Analisator {
    private static Logger logger = LoggerFactory.getLogger("std");
    private static void PutInMap(Map<Object,Integer> m, Object key){
        if(m.get(key) == null){
            m.put(key, 1);
        }else{
            m.put(key, m.get(key) + 1);
        }
    }
    private static String ShowMap(Map <Object, Integer> m){
        String out = "\r\n";
        for(Map.Entry<Object, Integer> i: m.entrySet()) {
            out += (i.getKey() + ": " + i.getValue() + "\r\n");
        }
        return out;
    }
    public static void Analise(SimpleMidiFile simpleMidiFile, String fileName, boolean inFile){
        List<Note> notes = simpleMidiFile.vocalNoteList();
        Map<Object, Integer> high = new TreeMap<>();
        Map<Object, Integer> longs = new TreeMap<>();
        Map<Object, Integer> inter = new TreeMap<>();
        String maxNote = "", minNote = ""; int maxMidi = -2000000000, minMidi = 2000000000;
        for(int i=0; i < notes.size(); i++){
            //System.out.println(notes.get(i).sign().fullName() + "  " + notes.get(i).sign().getMidi() + " " + notes.get(i).durationTicks() + " " + notes.get(i).startTick());
            if(maxMidi < notes.get(i).sign().getMidi()) {
                maxNote = notes.get(i).sign().fullName();
                maxMidi = notes.get(i).sign().getMidi();
            }
            if(minMidi > notes.get(i).sign().getMidi()) {
                minNote = notes.get(i).sign().fullName();
                minMidi = notes.get(i).sign().getMidi();
            }
            PutInMap(high, notes.get(i).sign().fullName());
            PutInMap(longs, notes.get(i).durationTicks() * simpleMidiFile.tickInMs());
            if(i != 0) {
                PutInMap(inter, abs(notes.get(i).sign().getMidi() - notes.get(i-1).sign().getMidi()));
            }
        }
        // logs (2)
        logger.info("Количество нот: " + Integer.toString(notes.size()));
        logger.info("Длительность (сек): " + simpleMidiFile.durationMs() / 1000);
        logger.info("Анализ диапазона:");
        logger.info("верхняя: " + maxNote);
        logger.info("нижняя: " + minNote);
        logger.info("диапазон: " + (maxMidi - minMidi));
        logger.info("Анализ длительности нот (мс):");
        logger.info(ShowMap(longs));
        logger.info("Анализ нот по высоте:");
        logger.info(ShowMap(high));
        logger.info("Анализ интервалов:");
        logger.info(ShowMap(inter));
        // file (1)
        if(inFile){
            try (FileWriter fout = new FileWriter(FilenameUtils.getFullPath(fileName) + "analise.txt", false)){
                fout.write("Количество нот: " + Integer.toString(notes.size()) + "\r\n");
                fout.write("Длительность (сек): " + simpleMidiFile.durationMs() / 1000 + "\r\n");
                fout.write("Анализ диапазона:\r\n");
                fout.write("верхняя: " + maxNote + "\r\n");
                fout.write("нижняя: " + minNote + "\r\n");
                fout.write("диапазон: " + (maxMidi - minMidi) + "\r\n");
                fout.write("Анализ длительности нот (мс):");
                fout.write(ShowMap(longs));
                fout.write("Анализ нот по высоте:");
                fout.write(ShowMap(high));
                fout.write("Анализ интервалов:");
                fout.write(ShowMap(inter));
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
