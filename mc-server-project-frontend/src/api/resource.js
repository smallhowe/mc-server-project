import request from "@/utils/request.js";
const config={
    headers:{
        'Content-Type': 'application/form-data'
    },
    withCredentials:true
}
export const getDownloadSourceList=()=>{
    return request.get("/api/res/list",config)
}
export const postUploadDonwloadSource=(data)=>{
    return request.post("/api/res/upload",data,config)
}