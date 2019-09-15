package com.xuecheng.framework.domain.learning.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * @FileName: LearningCode
 * @Author: DELL
 * @Date: 2019/8/22 9:39
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@ToString
public enum LearningCode implements ResultCode {
    LEARNING_GETMEDIA_ERROR(false,45001,"搜索课程计划视频失败"),
    CHOOSECOURSE_USERISNULl(false,45002,"选课时用户id为空"),
    CHOOSECOURSE_TASKISNULL(false,45002,"选课时任务id为空"),
    CHOOSECOURSE_COURSEISNULl(false,45003,"选课时课程id为空");

    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    private LearningCode(boolean success,int code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private static final ImmutableMap<Integer, LearningCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, LearningCode> builder = ImmutableMap.builder();
        for (LearningCode learningCode : values()) {
            builder.put(learningCode.code(), learningCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success () {
        return success;
    }

    @Override
    public int code () {
        return code;
    }

    @Override
    public String message () {
        return message;
    }


}
