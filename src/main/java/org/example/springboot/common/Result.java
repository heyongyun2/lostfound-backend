package org.example.springboot.common;


public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result=new Result();
        result.setCode("200");
        result.setMsg("请求成功");
        //这里写result也可以写成result.code="200";和result.msg="请求成功";
        return result;
    }
    public static Result success(Object data){
        Result result=success();

        result.setData(data);
        //这里写result也可以写成result.code="200";和result.msg="请求成功";
        return result;
    }
    public static Result error(){
        Result error=new Result();
        error.code="500";
        error.msg="请求失败";
        return error;
    }
    public static Result error(Object data){
        Result errors=error();
        errors.data=data;
        return errors;
    }
    public static Result error(String code,String msg){
         Result errors=new Result();
         errors.code=code;
         errors.msg=msg;
         return errors;



    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

