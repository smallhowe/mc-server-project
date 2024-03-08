import request from "@/utils/request.js";

const config={
    headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials:true
}
export function postGetCode(data){
    return request.post('/api/auth/valid-email',data,config)
}

export function postRegister(data){
    return request.post('/api/auth/register',data,config)
}