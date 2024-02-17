import {defineStore} from "pinia";
import {ref} from "vue";
import {getServerInfoRequest} from "@/api/server.js";
import {ElMessage} from "element-plus";

export const useServerStore = defineStore('server',()=>{

    const players=ref({
        online:0,
        max:0
    })
    const version=ref({
        name:"",
        protocol:0
    })
    const status=ref(0)

    async function getServerInfo(){
        const res=await getServerInfoRequest()
        ElMessage.closeAll();
        console.log(res)
        if (res.data.status!==200) return;
        players.value=res.data.data.players
        version.value=res.data.data.version
        status.value = 1;
    }
    return {
        players,
        version,
        status,
        getServerInfo
    }
})