package com.example.notesapp.Converters;

import com.example.notesapp.POJO.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ToArrayListConverter {

    public static ArrayList<NoteModel> toArrayList(List<NoteModel> list){
        return new ArrayList<NoteModel>(list);
    }
}
