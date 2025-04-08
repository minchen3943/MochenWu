package com.mochenwu.article.response;

/**
 * 通用 API 响应封装类。
 *
 * <p>该类用于统一封装 API 接口的响应数据，包括响应状态码、消息和返回的数据。</p>
 *
 * @param <T> 泛型类型，表示返回的数据类型
 * @author 瞑尘
 * @date 2025/04/06
 */
public class ApiResponse<T> {

    /**
     * 响应的状态码。
     */
    private int code;

    /**
     * 响应的提示信息。
     */
    private String message;

    /**
     * 响应的数据内容。
     */
    private T data;

    /**
     * 无参构造函数。
     *
     * <p>默认构造函数，通常用于框架的反射创建对象。</p>
     */
    public ApiResponse() {
    }

    /**
     * 带参构造函数。
     *
     * <p>用于创建具有指定状态码、消息和数据的 API 响应。</p>
     *
     * @param code    响应状态码
     * @param message 响应提示信息
     * @param data    响应数据内容
     */
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 获取响应状态码。
     *
     * @return 响应状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置响应状态码。
     *
     * @param code 响应状态码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取响应消息。
     *
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置响应消息。
     *
     * @param message 响应消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取响应数据。
     *
     * @return 响应数据内容
     */
    public T getData() {
        return data;
    }

    /**
     * 设置响应数据。
     *
     * @param data 响应数据内容
     */
    public void setData(T data) {
        this.data = data;
    }
}
