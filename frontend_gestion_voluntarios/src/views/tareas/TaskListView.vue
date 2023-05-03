<template>
<div class="d-flex flex-column align-items-center">
    <h1 class="mb-5">Lista de tareas</h1>
    <div class="w-75">
        <b-table striped hover :fields="fields" :items="items"></b-table>
    </div>
</div>
</template>

<script lang="ts" setup>
import { onMounted, ref, h } from 'vue';
import { getTasks } from '@/services/TareaService';
import ThreeDots from '@/components/ThreeDots.vue'
import Tarea from '@/interfaces/Tarea';
import useAuth from '@/store/auth';

const auth = useAuth();

const fields = ref<object[]>([
    {key: 'nombre', field: 'Nombre'},
    {key: 'voluntarios', field: 'Voluntarios'},
    {key: 'fechaInicio', field: 'Fecha inicio'},
    {key: 'fechaFin', field: 'Fecha fin'},
    {key: 'estado', field: 'Estado'},
     {key: 'acciones', field: 'Acciones', thClass: 'text-center', tdClass: 'text-center', formatter: (value: any, key: any, item: any) => {
        return h('svg', {xmlns: 'http://www.w3.org/2000/svg', width: '16', height: '16', fill: 'currentColor', class: 'bi bi-three-dots', viewBox: '0 0 16 16'}, [
            h('path', {d: 'M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z'})
        ]);
    }}

])

const items = ref<object[]>([]);

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
                estado: estado?.descripcion,
                ThreeDots
            })
        )
        console.log(items.value);
    }
})
</script>

<style scoped>

</style>