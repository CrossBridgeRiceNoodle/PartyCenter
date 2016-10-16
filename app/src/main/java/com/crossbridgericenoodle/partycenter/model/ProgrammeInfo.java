package com.crossbridgericenoodle.partycenter.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qianzise on 2016/10/16 0016.
 */
public class ProgrammeInfo {
    public String programmeName;
    public String actors;

    public ProgrammeInfo(String programmeName, String actors) {
        this.programmeName = programmeName;
        this.actors = actors;
    }

    public ProgrammeInfo(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("show"),jsonObject.getString("actors"));
    }
}
