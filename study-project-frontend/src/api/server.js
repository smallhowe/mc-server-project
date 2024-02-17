import request from "@/utils/request.js";
const config={
    headers:{
        'Content-Type': 'application/x-www-form-urlencoded'
    },
    withCredentials:true
}
export function getServerInfoRequest(){
    return request.get('/api/mc/status',config)
}