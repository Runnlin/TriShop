package cn.hstc.trishop.result;

public class Result {

    //响应码
    private int code;
    // 响应说明
    private String resultString;

    public Result(int code, String resultString) {
        this.code = code;
        this.resultString = resultString;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }
}
