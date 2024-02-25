import axios from "axios";

const request=axios.create({
    baseURL: 'http://127.0.0.1:8085',
    timeout: 5000
})

// 添加请求拦截器
request.interceptors.request.use(function (request) {
    // 在发送请求之前做些什么
    return request;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
request.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么

    if (response.data.msg === undefined || response.data.msg === null) {
        return response;
    }

    if (response.data.status === 200)
        ElMessage({
            message: response.data.msg,
            type: 'success'
        })
    else {
        ElMessage({
            message: response.data.msg,
            type: 'error'
        })
    }

    return response;

}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default request
