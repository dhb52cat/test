package vo;
/**
 * Copyright 2020 bejson.com
 */

/**
 * Auto-generated: 2020-01-09 16:19:24
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {
    private String api;
    private String request_id;
    private int code;
    private String msg;
    private String fields;
    private Data data;

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    public String getRequest_id() {
        return request_id;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }


}

