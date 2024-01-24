import request from "@/utils/request.js";


// 登录请求
export function postLogin(data) {
    return request.post('/api/auth/login',data,{
        headers:{
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials:true
    })
}

export function getLogOut(){
    return request.get('/api/auth/logout')
}

export function postGetCode(data){
    return request.post('/api/auth/valid-email',data,{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials:true
    })
}