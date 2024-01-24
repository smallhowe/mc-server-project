import {createRouter, createWebHistory} from 'vue-router'
import LoginOrRegister from "@/views/LoginOrRegister.vue";

const LoginPage = ()=> import('@/components/LoginPage.vue')
const RegisterPage=()=>import('@/components/RegisterPage.vue')
const Index = () => import('@/views/IndexView.vue')

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            redirect: '/login',
            component: LoginOrRegister,
            children:[
                {
                    path:'/login',
                    name: 'login',
                    component:LoginPage
                },
                {
                    path:'/register',
                    name: 'register',
                    component:RegisterPage
                }
            ]
        },
        {
            path: '/index',
            component: Index
        }

    ]
})

export default router
