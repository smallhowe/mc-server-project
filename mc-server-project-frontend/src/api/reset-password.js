import request from '@/utils/request'
const config={
    headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials:true
}
export function postGetCode(email) {
    return request.post("/api/auth/valid-reset-email",email,config)
}
export function postStartReset(data){
    return request.post("/api/auth/start-reset",data,config)
}
export function postDoReset(password){
        return request.post("/api/auth/do-reset",password,config)
}