package com.jt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
* @author fangchen
* @date 2020/1/18 3:59 下午
*/
public class ObejctMapperUtils {
    private static final ObjectMapper Mapper=new ObjectMapper();


//将目标类转化为json
    public static String toJson(Object target){


        String json=null;
        try{
            json =Mapper.writeValueAsString(target );
        }catch (Exception e){//将检查异常转化为运行时异常
            e.printStackTrace();
            throw new RuntimeException();
        }
        return json;
    }
    public static <T> T  toObject(String json,Class<T> target){
            T t = null;
            try {
                t = Mapper.readValue(json, target);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return t;


    }
}
