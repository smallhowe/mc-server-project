import request from "@/utils/request.js";

const config={
    headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials:true
}
export function getUserInfo(){
    return request.get('/api/user/me',config)
}
export function userSignIn(){
    return request.get('/api/user/sign',config)
}

export function postUserUploadAvatar(data){
    config.headers["Content-Type"]="multipart/form-data"
    return request.post('/api/user/uploadAvatar',data,config)
}

export function postBindUserGameId(gameId){
    return request.post('/api/user/bind',{gameId},config)
}

export function postResetPassword(oldPassword,newPassword){
    return request.post('/api/user/re-password',{oldPassword,newPassword},config)
}