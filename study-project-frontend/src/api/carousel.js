import request from "@/utils/request.js";

const config={
    withCredentials:true
}

export function getCarouselListRequest() {
    return request.get("/api/carousel/list",config)
}
