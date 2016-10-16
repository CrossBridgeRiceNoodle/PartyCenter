package com.crossbridgericenoodle.partycenter.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qianzise on 2016/10/15 0015.
 */
public class Position {
    public double lat;//经度
    public double lng;//纬度

    public Position(String position){
        String[] lola=position.split(",");
        this.lat=Double.valueOf(lola[1]);
        this.lng=Double.valueOf(lola[0]);
    }

    public Position(int lat, int lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public JSONObject getJsonObj(){

            JSONObject object=new JSONObject();
            try {
                object.put("lat",lat);
                object.put("lng",lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return object;
    }

    @Override
    public String toString() {
        JSONObject object=new JSONObject();
        try {
            object.put("lat",lat);
            object.put("lng",lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
