import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "@/stores/userStore.js";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            redirect: '/login',
            component: ()=>import("@/views/LoginOrRegister.vue"),
            children:[
                {
                    path:'/login',
                    name: 'wel-login',
                    component:()=> import('@/components/LoginPage.vue'),
                },
                {
                    path:'/register',
                    name: 'wel-register',
                    component:()=>import('@/components/RegisterPage.vue')
                },
                {
                    path:'/forget',
                    name: 'wel-forget',
                    component:()=>import('@/components/ForgetPage.vue')
                },
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue')
        }

    ]
})

router.beforeEach((to,from,next)=>{
    const store = useUserStore();

    if (store.user === null && (!to.name.startsWith('wel-'))) {
        next('/login')
    }else if(store.user !== null && to.name.startsWith('wel-')){
        next('/index')
    }else{

        next();
    }
})

export default router
