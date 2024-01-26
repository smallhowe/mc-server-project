import {createRouter, createWebHistory} from 'vue-router'

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
                    name: 'login',
                    component:()=> import('@/components/LoginPage.vue')
                },
                {
                    path:'/register',
                    name: 'register',
                    component:()=>import('@/components/RegisterPage.vue')
                },
                {
                    path:'/forget',
                    name: 'forget',
                    component:()=>import('@/components/ForgetPage.vue')
                },
            ]
        },
        {
            path: '/index',
            component: () => import('@/views/IndexView.vue')
        }

    ]
})

export default router
