package com.example.springboot.dto;

import com.example.springboot.exception.CustomizeErrorCode;
import lombok.Data;

/**
 * 返回工具类
 */
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    /**
     * 将CustomizeErrorCode枚举对象的值转换给ResultDTO
     * @param errorCode 枚举对象
     * @return
     */
    public static ResultDTO errorInfo(CustomizeErrorCode errorCode){
        return init(errorCode.getCode(),errorCode.getMessage());
    }

    /**
     * 初始化ResultDTO对象
     * @param code 状态码
     * @param message 描述信息
     * @return
     */
    private static ResultDTO init(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }


    public static ResultDTO info(Integer code, String message) {
        return init(code,message);
    }


}
