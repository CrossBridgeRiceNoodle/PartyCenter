package com.crossbridgericenoodle.partycenter.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 晚会模型
 */
public class Party implements Serializable {

    public int ID;
    public String name;
    public String time;
    public String location;
    public Position location_lo_la;
    public String type;
    public String publisher;
    public List<ProgrammeInfo> programsInfo = new ArrayList<>();
    public String host;
    public int vote;
    public String poster;
    public String detail;
    public List<Comment> comments=new ArrayList<>();


}
