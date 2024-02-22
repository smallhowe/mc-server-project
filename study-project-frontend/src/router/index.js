import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import {useUserStore} from "@/stores/userStore.js";


const router = createRouter({
    // history: createWebHistory(import.meta.env.BASE_URL),
    history: createWebHashHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            redirect: '/login',
            component: () => import("@/views/LoginOrRegister.vue"),
            children:[
                {
                    path:'/login',
                    name: 'wel-login',
                    component: () => import('@/components/LoginPage.vue'),
                },
                {
                    path:'/register',
                    name: 'wel-register',
                    component: () => import('@/components/RegisterPage.vue'),
                },
                {
                    path:'/forget',
                    name: 'wel-forget',
                    component: () => import('@/components/ForgetPage.vue'),
                },
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
            redirect:'/index/home',
            children:[
                {
                    name:'home',
                    path: 'home',
                    component: () => import('@/views/IndexMain/HomeView.vue'),
                },
                {
                    name: 'message',
                    path:'message',
                    component: () => import('@/views/IndexMain/MessageView.vue'),
                },
                {
                    name: 'download',
                    path: 'download',
                    component: () => import('@/views/IndexMain/DownloadView.vue'),
                },
                {
                    name: 'user',
                    path: 'user',
                    component: () => import('@/views/IndexMain/UserInfoView.vue'),
                }
            ]
        }

    ]
})

router.beforeEach((to,from,next)=>{
    const store = useUserStore();

    if (to.name===undefined){
        // console.log('to.name is undefined',to,from)
        next()
    }

    if (store.user === null && (!to.name.startsWith('wel-'))) {
        // console.log('前往登录页',store.user,to)
        next('/login')
    }else if(store.user !== null && to.name.startsWith('wel-')){
        // console.log('前往主页')
        next('/index')
    }else{
        // console.log('next')
        next();
    }

})

export default router
