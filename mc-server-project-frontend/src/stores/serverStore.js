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

    function getServerInfo(){
        return new Promise(async (resolve,reject)=>{
            const res=await getServerInfoRequest()
            ElMessage.closeAll();
            // console.log(res)
            if (res.data.status!==200){
                players.value={online:0,max:0}
                version.value={name:"",protocol:0}
                status.value = 0
                reject()
                return
            }
            players.value=res.data.data.players
            version.value=res.data.data.version
            status.value = 1;
            resolve()
        })
    }
    return {
        players,
        version,
        status,
        getServerInfo
    }
})