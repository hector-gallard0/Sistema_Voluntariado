import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { TaskListView, TaskView, CreateTaskView, EditTaskView, HomeView, LoginView, RegisterView } from '@/views/index'
import useAuth from '@/store/auth'

interface RouteMeta extends Record<string, unknown> {
	requireAuth?: boolean;
	roles?: string[];
}

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
		component: TaskListView,
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
			requireAuth: false,
			roles: []
		}
	},
	{
		path: '/register',
		name: 'register',
		component: RegisterView,
		meta: {
			requireAuth: false,
			roles: []
		}
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})

router.beforeEach((to, from, next) => {
	const auth = useAuth();
	const meta = to.meta as RouteMeta;
	if(to.meta.requireAuth && auth.token == null){
		next('login');
	} else if(to.meta.requireAuth && meta.roles?.includes(auth.rol)){
		next();
	} else{
		next();
	}
})

export default router
