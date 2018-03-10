package ru.liga;

import ru.liga.songtask.content.Content;
import ru.liga.songtask.domain.*;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Всего нот: 15
 * <p>
 * Анализ диапазона:
 * верхняя: E4
 * нижняя: F3
 * диапазон: 11
 * <p>
 * Анализ длительности нот (мс):
 * 4285: 10
 * 2144: 5
 * <p>
 * Анализ нот по высоте:
 * E4: 3
 * D4: 3
 * A3: 3
 * G3: 3
 * F3: 3
 * <p>
 * Анализ интервалов:
 * 2: 9
 * 5: 3
 * 11: 2
 */
public class App {
    public static void PutInMap(Map <Object,Integer> m, Object key){
        if(m.get(key) == null){
            m.put(key, 1);
        }else{
            m.put(key, m.get(key) + 1);
        }
    }

    public static void ShowMap(Map <Object, Integer> m){
        for(Map.Entry<Object, Integer> i: m.entrySet()) {
            System.out.println(i.getKey() + ": " + i.getValue());
        }
    }

    public static void main(String[] args) {
        SimpleMidiFile simpleMidiFile = new SimpleMidiFile(Content.ZOMBIE);
        List<Note> notes = simpleMidiFile.vocalNoteList();
        System.out.println("Количество нот: " + notes.size());
        System.out.println("Длительность (сек): " + simpleMidiFile.durationMs() / 1000);
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
        System.out.println("Анализ диапазона:");
        System.out.println("верхняя: " + maxNote);
        System.out.println("нижняя: " + minNote);
        System.out.println("диапазон: " + (maxMidi - minMidi));
        System.out.println("Анализ длительности нот (мс):" );
        ShowMap(longs);
        System.out.println("Анализ нот по высоте:");
        ShowMap(high);
        System.out.println("Анализ интервалов:");
        ShowMap(inter);
    }
}
