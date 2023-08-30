package com.example.notesapp.Converters;

import com.example.notesapp.POJO.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ToArrayList {

    public static ArrayList<NoteModel> convert(List<NoteModel> list){
        return new ArrayList<>(list);
    }
}
