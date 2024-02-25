import {defineStore} from "pinia";
import {ref} from "vue";
import {getMessageListRequest} from "@/api/message.js";


export const useMsgStore = defineStore('msgStore', () => {
    const msgList = ref([])
    const msgTotal=ref(0)
    const totalPages=ref(1)

    const getMsgList = async (current) => {
        const res = await getMessageListRequest(current)
        if (res.data.status!==200) return
        console.log(res)
        msgList.value.push(...res.data.data.records)
        totalPages.value=res.data.data.pages
        msgTotal.value=res.data.data.total
    }

    return {
        msgList,
        totalPages,
        getMsgList
    }
});