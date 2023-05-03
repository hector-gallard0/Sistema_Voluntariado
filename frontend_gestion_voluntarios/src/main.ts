import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import piniaPersist from 'pinia-plugin-persist'
import { BootstrapVueNext } from 'bootstrap-vue-next'

const pinia = createPinia();
pinia.use(piniaPersist);
const app = createApp(App);

app.use(router).use(pinia).use(BootstrapVueNext).mount('#app');
