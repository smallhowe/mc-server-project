import request from "@/utils/request.js";
const config={
    withCredentials:true
}
export function getNewsList(page) {
    return request.get(`/api/news/list?current=${page}`,config)
}