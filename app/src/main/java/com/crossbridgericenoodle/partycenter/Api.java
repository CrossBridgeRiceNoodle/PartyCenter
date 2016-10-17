package com.crossbridgericenoodle.partycenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crossbridgericenoodle.partycenter.model.Danmu;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.crossbridgericenoodle.partycenter.model.Position;
import com.crossbridgericenoodle.partycenter.model.ProgrammeInfo;
import com.crossbridgericenoodle.partycenter.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by FENG-MASTER on 2016/10/15 0015.
 */
public class Api {
    public static final String DEFAULT_HOST = "http://192.168.1.114";
    public static final String DEFAULT_PORT = "3000";

    public static final String PARTY_URL = "party/";
    public static final String USER_URL = "user/";

    private String host = DEFAULT_HOST;
    private String port = DEFAULT_PORT;

    public static final int COMMENT=0;
    public static final int DANMU=1;

    public static final int REGISTER_USERNAME_CONFLICT=0;//注册,用户名被使用
    public static final int REGISTER_EMAIL_CONFLICT=1;//注册,邮箱已经被使用
    public static final int REGISTER_OK=2;//注册成功
    public static final int REGISTER_SYS_ERR=3;//注册,服务器错误.

    public static final int LOGIN_USERNAME_NOEXIST=0;//登录.用户不存在
    public static final int LOGIN_PASSRORD_WRONG=1;//登录,密码错误
    public static final int LOGIN_OK=2;//登录,成功


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
     * 返回的晚会信息只包括了 ID ,name,time,location,poster,其他属性不可用,获取更详细的信息请用ID去调用getPartyInfo
     *
     * @param position     当前位置 经度纬度
     * @param range        半径范围
     * @param type         搜索的类型
     * @param obtainedRows 从第几个开始(这个是分页用的,用list的话好像不需要分页,直接取0就好了)
     * @param row          需要多少个数据(最多返回数据个数)
     * @param nowDate      现在的时间
     * @param listener     回调
     */
    public void getNearbyParties(Position position, int range, int obtainedRows, int row, String type, Date nowDate, OnResultListener<Party[]> listener) {
        listener.getResult(getSomeMyParties());

        //       getNearbyParties(host+":"+port+"/"+PARTY_URL,position,range,obtainedRows,row,type,nowDate,listener);
    }

    /**
     * 通过ID获取晚会信息,返回的信息包括所有party里的信息
     *
     * @param ID       给定晚会ID
     * @param listener 返回party的回调函数
     */
    public void getPartyInfo(int ID, OnResultListener<Party> listener) {
        listener.getResult(getMyParty());

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

    /**
     * 登录方法
     * @param userNameOrEmail 用户名或者邮箱
     * @param password 密码
     * @param listener 回调, 返回状态,详细看前面的常量
     */
    public void login(String userNameOrEmail, String password, OnResultListener<Integer> listener) {
        listener.getResult(new Integer(LOGIN_OK));
   //     login((host+":"+port+"/"+USER_URL,userNameOrEmail,password,listener);

    }


    /**
     * 发送评论(需要先判断是否登录了才能执行这个函数)
     * @param partyID 评论的晚会ID
     * @param userName 发送评论的用户名
     * @param comment 评论内容
     */
    public void sendComment(int partyID,String userName,String comment,OnResultListener<Boolean> listener){
        listener.getResult(new Boolean(true));
    }

    /**
     * 发送弹幕,需要先登录才能执行这个函数
     * @param partyID 发送弹幕的晚会ID
     * @param userName 发送弹幕的用户名
     * @param danmu 弹幕信息
     */
    public void sendDanmu(int partyID, String userName, Danmu danmu,OnResultListener<Boolean> listener){
        listener.getResult(new Boolean(true));

    }

    /**
     * 注册函数
     * @param userName 注册的用户名
     * @param email 邮箱
     * @param password 密码
     * @param listener 回调 返回整数,对应不同状态,详细看前面的常量
     *
     */
    public void register(String userName,String email,String password,OnResultListener<Integer> listener){
        listener.getResult(new Integer(REGISTER_OK));
    }

    /**
     * 退出登录
     */
    public void logout(){
        JSONObject sendObj=new JSONObject();
        try {
            sendObj.put("method","logout");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, host + ":" + port + "/" + USER_URL, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    /**
     * 获取用户信息
     * @param listener
     */
    public void getUserInfo(OnResultListener<User> listener){

    }





    private void register(String url,String userName,String email,String password,OnResultListener<Integer> listener){

    }


    private void sendDanmu(String url,int partyID,String userName,Danmu danmu,OnResultListener<Boolean> listener){


    }

    private void login(String url, String userNameOrEmail, String password, OnResultListener<Integer> listener) {
        JSONObject sendObj=new JSONObject();
        try {
            sendObj.put("method","login");
            JSONObject data=new JSONObject();

            if (userNameOrEmail.contains("@")){
                data.put("email",userNameOrEmail);
                data.put("name","");
            }else {
                data.put("name",userNameOrEmail);
                data.put("email","");
            }

            sendObj.put("userInfo",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(request);



    }

    private void getNearbyParties(String url, Position position, int range, int obtainedRows, int row, String type, Date nowDate, OnResultListener<Party[]> listener) {

        JSONObject objectSend = new JSONObject();
        JSONObject date = new JSONObject();

        try {

            objectSend.put("method", "getNearbyParty");

            date.put("point", position.getJsonObj());
            date.put("distance", range);
            date.put("type", type);
            date.put("obtainedRows", obtainedRows);//这个参数分页用的,就是要显示第几个开始
            date.put("rows", row);//这个参数要多少个
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
        party.time = "2016年10月17日 18:21:22";
        party.type="歌舞";
        party.location = "成都XXXX地区";
        party.poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";


        return party;
    }

    private Party[] getSomeMyParties() {
        //TODO:假数据
        Party[] party = new Party[2];
        party[0]=new Party();
        party[1]=new Party();
        party[0].ID = 1;
        party[0].name = "没什么名字1";
        party[0].time = "2016年10月17日 18:20:29";
        party[0].location = "成都XXXX地区";
        party[0].type="歌舞";
        party[0].poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";

        party[1].ID = 2;
        party[1].name = "没什么名字2";
        party[1].type="歌舞";
        party[1].time = "2016年10月17日 18:20:36";
        party[1].location = "成都XXXX地区";
        party[1].poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";

        return party;
    }


    public interface OnResultListener<T> {
        void getResult(T t);
    }

}
