package com.crossbridgericenoodle.partycenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.crossbridgericenoodle.partycenter.model.Position;
import com.crossbridgericenoodle.partycenter.model.ProgrammeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2016/10/15 0015.
 */
public class Api {
    public static final String DEFAULT_HOST = "http://192.168.1.114";
    public static final String DEFAULT_PORT = "3000";

    public static final String PARTY_URL = "party/";

    private String host = DEFAULT_HOST;
    private String port = DEFAULT_PORT;

    private RequestQueue queue;

    private static Api instance = new Api();

    public static Api getInstance() {
        return instance;
    }


    public Api() {
        queue = Volley.newRequestQueue(App.getContext());
    }

    /**
     * 获取周围的晚会信息,返回值的晚会信息不完整
     * 然后在进去详细信息的时候在用API的函数去用ID读取信息
     *
     * @param position
     * @param range
     * @param type
     * @param nowDate
     * @param listener
     */
    public void getNearbyParties(Position position, int range, String type, Date nowDate, OnResultListener<Party[]> listener) {
        listener.getResult(getSomeMyParties());
        return;
        //       getNearbyParties(host+":"+port+"/"+PARTY_URL,position,range,type,nowDate,listener);
    }

    /**
     * 通过ID获取晚会信息,返回的信息包括所有party里的信息
     *
     * @param ID       给定晚会ID
     * @param listener 返回party的回调函数
     */
    public void getPartyInfo(int ID, OnResultListener<Party> listener) {
        listener.getResult(getMyParty());
        return;
        //   getPartyInfo(host+":"+port+"/"+PARTY_URL+"info/",ID,listener);
    }

    /**
     * 获取最新的晚会信息,
     * 返回值有特殊情况,不会返回完整的晚会信息,只有ID,name,time,location,poster而已,不要在代码中调用其他属性,会报错
     *
     * @param numOfParties 最多需要多少个最新的晚会信息(不一定返回那么多个)
     * @param listener     回调
     */
    public void getNewParties(int numOfParties, OnResultListener<Party[]> listener) {
        //TODO:假数据.

        listener.getResult(getSomeMyParties());

    }

    private void getNearbyParties(String url, Position position, int range, String type, Date nowDate, OnResultListener<Party[]> listener) {

        JSONObject objectSend = new JSONObject();
        JSONObject date = new JSONObject();

        try {

            objectSend.put("method", "getNearbyParty");

            date.put("point", position.getJsonObj());
            date.put("distance", range);
            date.put("type", type);
            date.put("obtainedRows", 0);//这个参数分页用的,就是要显示第几个开始
            date.put("rows", 10);//这个参数要多少个
            date.put("partyDate", nowDate);


            objectSend.put("getNearbyParty", date);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, objectSend, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //返回值
                List<Party> partyList = new ArrayList<>();
                try {
                    JSONArray info = response.getJSONArray("partyInfo");

                    Party party = null;
                    JSONObject object = null;
                    int len = info.length();
                    for (int i = 0; i < len; i++) {
                        party = new Party();
                        object = info.getJSONObject(i);
                        party.ID = object.getInt("ID");
                        party.name = object.getString("name");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int i = 1;
                i++;
            }
        });

        queue.add(request);


    }

    private void getPartyInfo(String url, int ID, final OnResultListener<Party> listener) {
        JSONObject sendJson = new JSONObject();
        try {
            sendJson.put("method", "getPartyDetailInfo");
            sendJson.put("ID", ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url + ID, sendJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Party party = new Party();
                try {


                    party.name = response.getString("partyName");
                    party.ID = response.getInt("partyID");
                    party.vote = response.getInt("votes");
                    party.time = response.getString("partyTime");
                    party.location = response.getString("partyLocation");
                    party.location_lo_la = new Position(response.getString("partyLocation_lo_la"));
                    party.type = response.getString("partyType");
                    party.detail = response.getString("detail");
                    party.publisher = response.getString("partyPublisher");
                    party.host = response.getString("partyHosts");

                    JSONArray array = response.getJSONArray("shows");
                    for (int i = 0; i < array.length(); i++) {
                        party.programsInfo.add(new ProgrammeInfo(array.getJSONObject(i)));
                    }

                    party.comments = response.getString("comments");
                    party.poster = response.getString("posterURL");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.getResult(party);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("1", "23");
            }
        });
        queue.add(request);
    }


    private Party getMyParty() {
        //TODO:假数据.
        Party party = new Party();
        party.ID = 1;
        party.name = "没什么名字1";
        party.time = "神TM有字";
        party.location = "成都XXXX地区";
        party.poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";


        return party;
    }

    private Party[] getSomeMyParties() {
        //TODO:假数据.获取代码没写
        Party[] party = new Party[2];
        for (int i = 0; i < 2; i++) {
            party[i] = new Party();
        }
        party[0].ID = 1;
        party[0].name = "没什么名字1";
        party[0].time = "也神TM有字";
        party[0].location = "成都XXXX地区";
        party[0].poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";

        party[1].ID = 2;
        party[1].name = "没什么名字2";
        party[1].time = "";
        party[1].location = "成都XXXX地区";
        party[1].poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";

        return party;
    }


    public interface OnResultListener<T> {
        void getResult(T t);
    }

}
