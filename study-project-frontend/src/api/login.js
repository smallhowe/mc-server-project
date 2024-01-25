import request from "@/utils/request.js";

const config={
    headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials:true
}
// 登录请求
export function postLogin(data) {
    return request.post('/api/auth/login',data,config)
}

export function getLogOut(){
    return request.get('/api/auth/logout')
}

export function postGetCode(data){
    return request.post('/api/auth/valid-email',data,config)
}

export function postRegister(data){
    return request.post('/api/auth/register',data,config)
}