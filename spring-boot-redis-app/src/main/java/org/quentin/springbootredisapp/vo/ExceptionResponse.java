package org.quentin.springbootredisapp.vo;

public class ExceptionResponse {
    private String msg;

    private String recommendHref;

    public ExceptionResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRecommendHref() {
        return recommendHref;
    }

    public void setRecommendHref(String recommendHref) {
        this.recommendHref = recommendHref;
    }
}
