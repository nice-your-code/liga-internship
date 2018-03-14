package ru.liga.songtask.workers;

import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.util.Iterator;

/**
 * Created by User on 14.03.2018.
 */
public class Changer {
    private static Logger logger = LoggerFactory.getLogger("std");
    public static void change(SimpleMidiFile midi, Integer trans, Integer tempo){
        //change tempo
        MidiTrack T = midi.getMidiFormat().getTracks().get(0);
        Iterator<MidiEvent> it = T.getEvents().iterator();
        while(it.hasNext())
        {
            MidiEvent E = it.next();

            if(E.getClass().equals(Tempo.class))
            {
                Tempo temp = (Tempo) E;
                temp.setBpm(temp.getBpm() * (1 + tempo / 100));
            }
        }
        //change tonal
        T = midi.getMidiFormat().getTracks().get(1);
        it = T.getEvents().iterator();
        while(it.hasNext())
        {
            MidiEvent E = it.next();

            if(E.getClass().equals(NoteOff.class))
            {
                NoteOff noteoff = (NoteOff) E;
                noteoff.setNoteValue(noteoff.getNoteValue() + trans);
            }
            if(E.getClass().equals(NoteOn.class)){
                NoteOn noteOn = (NoteOn) E;
                noteOn.setNoteValue(noteOn.getNoteValue() + trans);
            }
        }
    }
}
