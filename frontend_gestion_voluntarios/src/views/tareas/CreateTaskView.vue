<template>
    <div class="mb-5">
        <h1 class="mb-3">Crear Tarea</h1>        
        <div class="d-flex flex-column">
            <div class="d-flex justify-content-center align-items-center flex-column">
                <b-form class="w-50" @submit.prevent="submitCreateTaskForm">
                <b-form-group label="Emergencia:" label-for="emergencia">
                    <b-form-select id="emergencia" v-model="idEmergencia" :options="emergenciaOptions">                        
                    </b-form-select>
                </b-form-group> 

                <div class="d-flex">
                    <b-form-group class="me-5 w-50" label="Nombre:" label-for="nombre">
                        <b-form-input
                        id="nombre"
                        v-model="tarea.nombre"
                        type="text"
                        placeholder="Ingresa un nombre"
                        required
                        ></b-form-input>
                    </b-form-group>                    
                    
                    <b-form-group class="w-50" label="Voluntarios requeridos:" label-for="voluntariosRequeridos">
                        <b-form-input
                        id="voluntariosRequeridos"
                        v-model="tarea.voluntariosRequeridos"
                        type="number"
                        placeholder="Ingresa la cantidad de voluntarios requeridos"
                        required
                        ></b-form-input>
                    </b-form-group>
                </div>
                    
                <div class="d-flex">
                    <b-form-group class="me-5 w-50" label="Fecha inicio:" label-for="fechaInicio">
                        <b-form-input
                        id="fechaInicio"
                        v-model="tarea.fechaInicio"
                        type="date"                        
                        required
                        ></b-form-input>
                    </b-form-group>
                    
                    <b-form-group class="w-50" label="Fecha fin:" label-for="fechaFin">
                        <b-form-input
                        id="fechaFin"
                        v-model="tarea.fechaFin"
                        type="date"                        
                        required
                        ></b-form-input>
                    </b-form-group>
                </div>

                <b-form-group label="Descripción:" label-for="descripcion">
                    <b-form-textarea
                    id="descripcion"
                    v-model="tarea.descripcion"
                    placeholder="Descripción de la tarea"
                    rows="6"
                    max-rows="6"
                    no-resize
                    ></b-form-textarea>
                </b-form-group>

                    <!-- <b-form-group label="Fecha inicio:" label-for="fechaInicio">
                        <b-form-datepicker id="fechaInicio" v-model="tarea.fechaInicio"></b-form-datepicker>
                    </b-form-group>
                    <b-form-group label="Fecha fin:" label-for="fechaFin">
                        <b-form-datepicker id="fechaFin" v-model="tarea.fechaFin"></b-form-datepicker>
                    </b-form-group>
                 -->
                <b-button type="submit" variant="primary" class="w-100 py-2 mt-3">Crear tarea</b-button>            
                    
                </b-form>
                <div class="w-100" v-if="error">
                    <div class="d-flex justify-content-center" v-for="(message, i) in messages" :key="i">
                        <div class="d-flex justify-content-center alert alert-danger w-50 py-3 my-4" role="alert">                    
                            <ErrorSVG/>
                            <span class="mx-2">{{ message }}</span>                                                                
                        </div>
                    </div>
                </div>
                <div class="w-100" v-if="success">
                    <div class="d-flex justify-content-center" v-for="(message, i) in messages" :key="i">
                        <div class="d-flex justify-content-center alert alert-success w-50 py-3 my-4" role="success">
                            <SuccessSVG/>
                            <span class="mx-2">{{ message }}</span>                                                                
                        </div>        
                    </div>
                </div>
            </div>
        </div>            
    </div>    
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import Tarea from "@/interfaces/Tarea"
import ErrorSVG from '@/components/ErrorSVG.vue'
import SuccessSVG from '@/components/SuccessSVG.vue'
import useAuth from '@/store/auth'
import { getEmergencias } from '@/services/EmergenciaService'

const auth = useAuth();

const tarea = ref<Tarea>({
    nombre: "",
    descripcion: "",
    voluntariosRequeridos: 0,
    voluntariosInscritos: 0,
    fechaInicio: "",
    fechaFin: ""
});

const idEmergencia = ref<number>(-1);
const emergenciaOptions = ref<object[]>([
    {value: -1, text: "Selecciona una emergencia"}
])


const idEstado = ref<number>(-1);

const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

onMounted(async () => {
    const emergencias = await getEmergencias(auth.token || "");
    
    emergencias.map(emergencia => 
        emergenciaOptions.value.push({
            value: emergencia.id,
            text: emergencia.nombre
        })
    )
    
})

const submitCreateTaskForm = () => {
    console.log("Form enviado");
}


</script>
