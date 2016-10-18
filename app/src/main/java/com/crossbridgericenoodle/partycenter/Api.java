package com.crossbridgericenoodle.partycenter;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crossbridgericenoodle.partycenter.model.Comment;
import com.crossbridgericenoodle.partycenter.model.Danmu;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.crossbridgericenoodle.partycenter.model.Position;
import com.crossbridgericenoodle.partycenter.model.ProgrammeInfo;
import com.crossbridgericenoodle.partycenter.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final int COMMENT = 0;
    public static final int DANMU = 1;

    public static final int REGISTER_USERNAME_CONFLICT = 0;//注册,用户名被使用
    public static final int REGISTER_EMAIL_CONFLICT = 1;//注册,邮箱已经被使用
    public static final int REGISTER_OK = 2;//注册成功
    public static final int REGISTER_SYS_ERR = 3;//注册,服务器错误.


    public static final int LOGIN_USERNAME_NOEXIST = 0;//登录.用户不存在
    public static final int LOGIN_PASSRORD_WRONG = 1;//登录,密码错误
    public static final int LOGIN_OK = 2;//登录,成功


    private RequestQueue queue;
    private String connectID = null;

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
    public void getNearbyParties(Position position, int range, int obtainedRows, int row, String type, Date nowDate, OnResultListener<List<Party>> listener) {

        getNearbyParties(host + ":" + port + "/" + PARTY_URL, position, range, obtainedRows, row, type, nowDate, listener);
    }

    /**
     * 通过ID获取晚会信息,返回的信息包括所有party里的信息
     *
     * @param ID       给定晚会ID
     * @param listener 返回party的回调函数
     */
    public void getPartyInfo(int ID, OnResultListener<Party> listener) {

        getPartyInfo(host + ":" + port + "/" + PARTY_URL + "info/", ID, listener);
    }

    /**
     * 获取最新的晚会信息,
     * 返回值有特殊情况,不会返回完整的晚会信息,只有ID,name,time,type,location,poster而已,不要在代码中调用其他属性,会报错
     *
     * @param numOfParties 最多需要多少个最新的晚会信息(不一定返回那么多个)
     * @param listener     回调
     */
    public void getNewParties(int numOfParties, OnResultListener<List<Party>> listener) {

        getNewParties(host + ":" + port + "/" + PARTY_URL, numOfParties, listener);

    }

    /**
     * 登录方法
     *
     * @param userNameOrEmail 用户名或者邮箱
     * @param password        密码
     * @param listener        回调, 返回状态,详细看前面的常量
     */
    public void login(String userNameOrEmail, String password, OnResultListener<Integer> listener) {

        login(host + ":" + port + "/" + USER_URL, userNameOrEmail, password, listener);

    }


    /**
     * 发送评论(需要先判断是否登录了才能执行这个函数)
     *
     * @param partyID 评论的晚会ID
     * @param comment 评论内容
     */
    public void sendComment(int partyID, String comment, OnResultListener<Boolean> listener) {

        sendComment(host + ":" + port + "/" + PARTY_URL, partyID, comment, listener);
    }

    /**
     * 发送弹幕,需要先登录才能执行这个函数
     *
     * @param partyID  发送弹幕的晚会ID
     * @param danmu    弹幕信息
     * @param listener 回调
     */
    public void sendDanmu(int partyID, Danmu danmu, OnResultListener<Boolean> listener) {
        sendDanmu(host + ":" + port + "/" + PARTY_URL, partyID, danmu, listener);
    }

    /**
     * 注册函数
     *
     * @param userName 注册的用户名
     * @param email    邮箱
     * @param password 密码
     * @param type     注册种类,user里的常量
     * @param sex      性别
     * @param listener 回调 返回整数,对应不同状态,详细看前面的常量
     */
    public void register(String userName, String email, String password, int type, int sex, OnResultListener<Integer> listener) {

        register(host + ":" + port + "/" + USER_URL, userName, email, password, type, sex, listener);
    }

    /**
     * 退出登录,完成
     */
    public void logout() {
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "logout");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, host + ":" + port + "/" + USER_URL, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (connectID != null && connectID.length() > 0) {
                    Map<String, String> header = new HashMap<>();
                    header.put("cookie", connectID);
                    connectID=null;
                    return header;
                } else {
                    return super.getHeaders();
                }

            }

        };

        queue.add(request);
    }

    /**
     * 获取当前用户信息(登录后再调用)
     *
     * @param listener 回调
     */
    public void getUserInfo(OnResultListener<User> listener) {
//        User user=new User();
//        user.email="asdwsa@qwq.com";
//        user.type=User.TYPE_AUDIENCE;
//        user.ID=1;
//        user.name="什么鬼名字";
//        user.sex=User.SEX_MAN;
//        listener.getResult(user);
        getUserInfo(host + ":" + port + "/" + USER_URL, listener);

    }

    /**
     * 修改晚会信息,该接口未开放
     *
     * @param url
     * @param party
     * @param listener
     */
    public void editParty(String url, Party party, OnResultListener<Integer> listener) {
        JSONObject sendObj = new JSONObject();

        JSONObject partyInfo = new JSONObject();
        try {
            partyInfo.put("ID", party.ID);
            partyInfo.put("name", party.name);
            partyInfo.put("time", party.time);
            partyInfo.put("location", party.location);
            partyInfo.put("location_lo_la", party.location_lo_la);
            partyInfo.put("show_actors", party.programsInfo);
            partyInfo.put("detail", party.detail);

            sendObj.put("reNewPartyInfo", partyInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO:返回值的获取
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO:错误
            }
        });

        queue.add(request);

    }

    private void getUserInfo(String url, final OnResultListener<User> listener) {
        //测试通过
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "getUserInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user = new User();
                try {
                    JSONObject obj = response.getJSONObject("userInfo");
                    user.ID = obj.getInt("ID");
                    user.type = obj.getInt("type");
                    user.name = obj.getString("name");
                    user.email = obj.getString("email");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.getResult(user);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(null);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (connectID != null && connectID.length() > 0) {
                    Map<String, String> header = new HashMap<>();
                    header.put("cookie", connectID);
                    return header;
                } else {
                    return super.getHeaders();
                }

            }
        };

        queue.add(request);

    }

    private void sendComment(String url, int partyID, String comment, final OnResultListener<Boolean> listener) {
        //测试通过
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "sendComment");
            JSONObject commentInfo = new JSONObject();
            commentInfo.put("partyID", partyID);
            commentInfo.put("type", COMMENT);

            JSONObject contentInfo = new JSONObject();
            contentInfo.put("content", comment);
            commentInfo.put("contentInfo", contentInfo);
            sendObj.put("commentInfo", commentInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.getResult(response.getInt("commentRes") == 1);//返回0是错误,1是正确发送
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(false);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (connectID != null && connectID.length() > 0) {
                    Map<String, String> header = new HashMap<>();
                    header.put("cookie", connectID);
                    return header;
                } else {
                    return super.getHeaders();
                }

            }
        };

        queue.add(request);

    }

    private void sendDanmu(String url, int partyID, Danmu danmu, final OnResultListener<Boolean> listener) {
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "sendComment");
            JSONObject commentInfo = new JSONObject();
            commentInfo.put("partyID", partyID);
            commentInfo.put("type", DANMU);

            JSONObject contentInfo = new JSONObject();
            contentInfo.put("content", danmu.content);
            contentInfo.put("danmuType", danmu.type);
            contentInfo.put("danmuSize", danmu.size);
            contentInfo.put("danmuColor", danmu.color);


            commentInfo.put("contentInfo", contentInfo);
            sendObj.put("commentInfo", commentInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("commentRes") == 1) {
                        listener.getResult(new Boolean(true));
                    } else {
                        listener.getResult(new Boolean(false));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("1", "1");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                if (connectID != null && connectID.length() > 0) {
                    Map<String, String> header = new HashMap<>();
                    header.put("cookie", connectID);
                    return header;
                } else {
                    return super.getHeaders();
                }

            }
        };

        queue.add(request);


    }

    private void register(String url, String userName, String email, String password, int type, int sex, final OnResultListener<Integer> listener) {
        //完成
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "register");
            JSONObject userInfo = new JSONObject();
            userInfo.put("name", userName);
            userInfo.put("userType", type);
            userInfo.put("email", email);
            userInfo.put("password", password);
            userInfo.put("sex", sex);
            sendObj.put("userInfo", userInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.getResult(response.getInt("state"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(REGISTER_SYS_ERR);
            }
        });

        queue.add(request);


    }

    private void login(String url, String userNameOrEmail, String password, final OnResultListener<Integer> listener) {
        //完成,通过测试
        JSONObject sendObj = new JSONObject();
        try {
            sendObj.put("method", "login");
            JSONObject data = new JSONObject();

            if (userNameOrEmail.contains("@")) {
                data.put("email", userNameOrEmail);
                data.put("name", "");
            } else {
                data.put("name", userNameOrEmail);
                data.put("email", "");
            }
            data.put("password", password);

            sendObj.put("userInfo", data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, sendObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.getResult(response.getJSONObject("login").getInt("state"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(Integer.valueOf(LOGIN_USERNAME_NOEXIST));
            }
        }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                String cookies = response.headers.get("Set-Cookie");
                connectID = cookies.substring(0, cookies.indexOf(";"));
                return super.parseNetworkResponse(response);
            }
        };


        queue.add(request);


    }

    private void getNearbyParties(String url, Position position, int range, int obtainedRows, int row, String type, Date nowDate, final OnResultListener<List<Party>> listener) {
        //已经测试通过
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

                listener.getResult(partyList);


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

    private void getPartyInfo(final String url, int ID, final OnResultListener<Party> listener) {
        //完成,通过测试
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
                    party.poster = host + ":" + port + response.getString("posterURL");
                    JSONArray array = response.getJSONArray("shows");
                    for (int i = 0; i < array.length(); i++) {
                        party.programsInfo.add(new ProgrammeInfo(array.getJSONObject(i)));
                    }

                    JSONArray commentsArr = response.getJSONArray("comments");
                    int len = commentsArr.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject comment = commentsArr.getJSONObject(i);
                        party.comments.add(new Comment(comment.getString("userName"), comment.getString("content")));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.getResult(party);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(null);
            }
        });
        queue.add(request);
    }

    private void getNewParties(String url, int num, final OnResultListener<List<Party>> listener) {
        //通过测试

        /*
        * 传出格式
        * method:getNewParties
        * num:要获取最新多少的晚会
        * */
        JSONObject sendobj = new JSONObject();

        try {
            sendobj.put("method", "getNewParties");
            sendobj.put("num", num);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, sendobj, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Party> list = new ArrayList<>();
                int len = response.length();
                try {
                    for (int i = 0; i < len; i++) {
                        Party party = new Party();

                        party.ID = response.getJSONObject(i).getInt("ID");
                        party.name = response.getJSONObject(i).getString("name");
                        party.time = response.getJSONObject(i).getString("time");
                        party.type = response.getJSONObject(i).getString("type");
                        party.poster = host + ":" + port + response.getJSONObject(i).getString("poster");

                        list.add(party);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.getResult(list);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getResult(null);
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
        party.type = "歌舞";
        party.location = "成都XXXX地区";

        party.poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";
        return party;
    }

    private List<Party> getSomeMyParties() {
        //TODO:假数据

        List<Party> parties = new ArrayList<>();
        Party party = new Party();

        party.ID = 1;
        party.name = "没什么名字1";
        party.time = "2016年10月17日 18:20:29";
        party.location = "成都XXXX地区";
        party.type = "歌舞";
        party.poster = "http://img4q.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";

        parties.add(party);


        return parties;
    }


    public interface OnResultListener<T> {
        void getResult(T t);
    }

}
