import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { TaskListView, TaskView, CreateTaskView, EditTaskView, HomeView, LoginView, RegisterView } from '@/views/index'
import useAuth from '@/store/auth'

interface RouteMeta extends Record<string, unknown> {
	requireAuth?: boolean;
	roles?: string[];
}

const routes: Array<RouteRecordRaw> = [
	{
		path: '/home',
		name: 'home',
		component: HomeView,
		meta: {
			requireAuth: true,
			roles: ['COORDINADOR', 'VOLUNTARIO']
		}
	},
	{
		path: '/tasks',
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

router.beforeEach((to) => {
	const auth = useAuth();
	const meta = to.meta as RouteMeta;
	console.log(to.meta.requireAuth, auth.authData.token);
	console.log(auth.authData.token.length)
	if(to.meta.requireAuth && (auth.authData.token == null || auth.authData.token.length === 0)){		
		return { name: 'login' }
	} else if(to.meta.requireAuth && meta.roles?.find(r => r === auth.authData.tokenPayload.rol?.nombre)){
		console.log("Roles requeridos", meta.roles);
		console.log("Roles que posee ", auth.authData.tokenPayload.rol?.nombre);
		console.log("Resultado find ", meta.roles?.find(r => r === auth.authData.tokenPayload.rol?.nombre));
		return true;
	} else if(to.meta.requireAuth && !meta.roles?.find(r => r === auth.authData.tokenPayload.rol?.nombre)){
		console.log("Roles requeridos", meta.roles);
		console.log("Roles que posee ", auth.authData.tokenPayload.rol?.nombre);
		console.log("Resultado find ", meta.roles?.find(r => r === auth.authData.tokenPayload.rol?.nombre));
		alert('No posee permisos');
		return false;
	} 
	return true;
})

export default router