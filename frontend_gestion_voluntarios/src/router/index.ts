import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { TasksListView, TaskView, CreateTaskView, EditTaskView, HomeView, LoginView, RegisterView } from '@/views/index'
import useAuth from '@/store/auth'

const routes: Array<RouteRecordRaw> = [
	{
		path: '/',
		name: 'home',
		component: HomeView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR', 'VOLUNTARIO']
		}
	},
	{
		path: '/',
		name: 'tasks',
		component: TasksListView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR']
		}
	},
	{
		path: '/task/create',
		name: 'createTask',
		component: CreateTaskView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR']
		}
	},
	{
		path: '/task/:id',
		name: 'task',
		component: TaskView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR']
		}
	},
	{
		path: '/task/:id/edit',
		name: 'editTask',
		component: EditTaskView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR']
		}
	},
	{
		path: '/login',
		name: 'login',
		component: LoginView,
		meta: {
			requireAuth: false
		}
	},
	{
		path: '/register',
		name: 'register',
		component: RegisterView,
		meta: {
			requireAuth: false
		}
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})

router.beforeEach((to, from, next) => {
	const isAuth = 
})

export default router
