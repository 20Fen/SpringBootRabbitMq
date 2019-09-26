package com.example.demo.system.controller;

import com.example.demo.system.util.AjaxResult;
import com.example.demo.system.util.CustomException;
import com.example.demo.system.util.ReturnInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * Description:组件控制器封装
 *
 * @date 2019年09月26日 16:21
 * Version 1.0
 */
@Log4j2
@ControllerAdvice
public class BaseController {
    /**
     * ============================ ajax
     * =================================================
     */
    /**
     * 返回ajaxresult
     *
     * @param data
     * @return AjaxResult
     */
    public AjaxResult json(Object data) {
        return new AjaxResult().success(data);
    }

    /**
     * 返回ajaxresult
     *
     * @param message
     * @return AjaxResult
     */
    public AjaxResult warn(String message) {
        return new AjaxResult().addWarn(message);
    }

    /**
     * 返回ajaxresult
     *
     * @param message
     * @return AjaxResult
     */
    public AjaxResult fail(String message) {
        return new AjaxResult().addFail(message);
    }

    /**
     * 描述: ajax请求响应封装
     * 时间: 2019年3月15日 上午9:02:48
     * 参数: (参数列表)
     *
     * @param message 响应消息
     * @return
     */
    public AjaxResult error(String message) {
        return new AjaxResult(AjaxResult.AJAX_STATUS_ERROR).setMessage(message);
    }

    /**
     * 描述: ajax请求响应封装
     * 时间: 2019年3月15日 上午9:02:48
     * 参数: (参数列表)
     *
     * @param message 响应消息
     * @param status  状态码
     * @return
     */
    public AjaxResult error(String message, int status) {
        return new AjaxResult(AjaxResult.AJAX_STATUS_ERROR).setMessage(message).setStatuscode(status);
    }

    /**
     * 描述: ajax请求响应封装
     * 时间: 2019年3月15日 上午8:59:50
     * 参数: (参数列表)
     *
     * @param message 响应消息
     * @return
     */
    public AjaxResult success(String message) {
        return new AjaxResult(AjaxResult.AJAX_STATUS_SUCCESS).setMessage(message);
    }

    /**
     * 描述: ajax请求响应封装
     * 时间: 2019年3月15日 上午8:59:50
     * 参数: (参数列表)
     *
     * @param message 响应消息
     * @param results 响应对象(List<T>,Map<Object,T>,T对象或基本类型等)
     * @return
     */
    public AjaxResult success(String message, Object results) {
        return new AjaxResult(AjaxResult.AJAX_STATUS_SUCCESS).setMessage(message).setResults(results);
    }

    public AjaxResult success(String message, Object results, long total, int pageNum, int pageSize) {
        return new AjaxResult.PAjaxResult(AjaxResult.AJAX_STATUS_SUCCESS).setTotal(total).setPageNum(pageNum).setPageSize(pageSize).setMessage(message).setResults(results);
    }


    /**
     * @return void
     * @Description 校验文件名称和下载路径
     * @Date 16:34 2019/5/16
     * @Param [fileName, basePath]
     **/
    private void checkFileName(String fileName, String basePath) throws CustomException {
        if (StringUtils.isEmpty(fileName)) {
            throw new CustomException(ReturnInfo.DOWNLOAD_ERROR_MSG);
        }
        if (StringUtils.isEmpty(basePath)) {
            throw new CustomException(ReturnInfo.DOWNLOAD_ERROR_MSG);
        }
    }


}
