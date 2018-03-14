package ru.liga.songtask.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;

/**
 * Created by User on 14.03.2018.
 */
public class Analisator {
    private static Logger logger = LoggerFactory.getLogger( Analisator.class );
    private static void PutInMap(Map<Object,Integer> m, Object key){
        if(m.get(key) == null){
            m.put(key, 1);
        }else{
            m.put(key, m.get(key) + 1);
        }
    }
    private static void ShowMap(Map <Object, Integer> m){
        for(Map.Entry<Object, Integer> i: m.entrySet()) {
            logger.info(i.getKey() + ": " + i.getValue());
        }
    }
    public static void Analise(SimpleMidiFile simpleMidiFile){
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
        logger.info("Количество нот: " + Integer.toString(notes.size()));
        logger.info("Длительность (сек): " + simpleMidiFile.durationMs() / 1000);
        logger.info("Анализ диапазона:");
        logger.info("верхняя: " + maxNote);
        logger.info("нижняя: " + minNote);
        logger.info("диапазон: " + (maxMidi - minMidi));
        logger.info("Анализ длительности нот (мс):");
        ShowMap(longs);
        logger.info("Анализ нот по высоте:");
        ShowMap(high);
        logger.info("Анализ интервалов:");
        ShowMap(inter);
    }
}
