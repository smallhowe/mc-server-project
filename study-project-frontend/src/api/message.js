import request from "@/utils/request.js";
const config={
    withCredentials:true
}

export function getMessageListRequest(current){
    return request.get(`/api/message/list?current=${current}`,config);
}