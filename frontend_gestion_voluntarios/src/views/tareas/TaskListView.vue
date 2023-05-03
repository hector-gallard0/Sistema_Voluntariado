<template>
<div class="d-flex flex-column align-items-center">
    <h1 class="mb-5">Lista de tareas</h1>
    <div class="w-75" v-if="items.length > 0 && fields.length > 0 && rows > 0">
        <b-table                       
        striped hover            
        id="task-table" 
            :current-page="currentPage"
            :per-page="perPage"
            :items="items"
            :fields="fields" 
            >
            <template #cell(acciones)>
                <ThreeDots/>
            </template>
        </b-table>
        <b-pagination        
            v-model="currentPage"            
            :total-rows="rows"
            :per-page="perPage"
            aria-controls="task-table"
        ></b-pagination>

    </div>
</div>
</template>

<script lang="ts" setup>
import { onMounted, ref, computed } from 'vue';
import { TableField, TableItem } from 'bootstrap-vue-next';
import { getTasks } from '@/services/TareaService';
import ThreeDots from '@/components/ThreeDots.vue'
import Tarea from '@/interfaces/Tarea';
import useAuth from '@/store/auth';

const fields = ref<TableField[]>([
    {key: 'nombre', label: 'Nombre'},
    {key: 'voluntarios', label: 'Voluntarios'},
    {key: 'fechaInicio', label: 'Fecha inicio'},
    {key: 'fechaFin', label: 'Fecha fin'},
    {key: 'estado', label: 'Estado'},
    {key: 'acciones', label: 'Acciones', thClass: 'text-center', tdClass: 'text-center'}
])
const items = ref<TableItem[]>([]);
const auth = useAuth();
const currentPage = ref<number>(1);
const perPage = ref<number>(10);
const rows = computed<number>(() => items.value.length);

onMounted(async () => {    
    if(auth.token != null){
        const response = await getTasks(auth.token);        
        const tareas:Tarea[] = response;
        tareas.map(({nombre, voluntariosRequeridos, voluntariosInscritos, fechaInicio, fechaFin, estado}) => 
            items.value.push({
                isActive: 'true',
                nombre,
                voluntarios: `${voluntariosInscritos}/${voluntariosRequeridos}`,
                fechaInicio,
                fechaFin,
                estado: estado?.descripcion
            })
        )
    }    
})

</script>

<style scoped>

</style>