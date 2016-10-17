package com.crossbridgericenoodle.partycenter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2016/10/15 0015.
 */
public class Party implements Serializable {

    public int ID;
    public String name;//
    public String time;//
    public String location;//
    public Position location_lo_la;
    public String type;//
    public String publisher;
    public List<ProgrammeInfo> programsInfo = new ArrayList<>();
    public String host;//主办方
    public int vote;
    public String poster;//海报
    public String detail;
    public String comments;


}
