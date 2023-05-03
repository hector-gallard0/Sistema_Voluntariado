<template>
    <div class="mb-5" :aria-hidden="show ? 'true' : undefined">
        <h1 class="mb-3">Crear Tarea</h1>        
        <div class="d-flex flex-column">
            <div class="w-100" v-if="error">
            <div class="d-flex justify-content-center" v-for="(message, i) in messages" :key="i">
                <div class="d-flex justify-content-center alert alert-danger w-50 py-3 my-4" role="alert">                    
                    <ErrorSVG/>
                    <span class="mx-2">{{ message }}</span>                                                                
                </div>
            </div>                
        </div>
            <div class="d-flex justify-content-center align-items-center flex-column">
                <b-form class="w-25" @submit.prevent="submitCreateTaskForm">
                    <b-form-group label="Emergencia:" label-for="emergencia">
                        <b-form-select id="emergencia" v-model="idEmergencia" :options="emergenciaOptions">                        
                        </b-form-select>
                    </b-form-group> 
                    
                    <b-form-group label="Nombre:" label-for="nombre">
                        <b-form-input
                        id="nombre"
                        v-model="tarea.nombre"
                        type="text"
                        placeholder="Ingresa un nombre"
                        required
                        ></b-form-input>
                    </b-form-group>                    
                    
                    <b-form-group label="Voluntarios requeridos:" label-for="voluntariosRequeridos">
                        <b-form-input
                        id="voluntariosRequeridos"
                        v-model="tarea.voluntariosRequeridos"
                        type="number"
                        placeholder="Ingresa la cantidad de voluntarios requeridos"
                        required
                        ></b-form-input>
                    </b-form-group>                                            
                
                    <b-form-group label="Fecha inicio:" label-for="fechaInicio">
                        <b-form-input
                        id="fechaInicio"
                        v-model="tarea.fechaInicio"
                        type="date"                        
                        required
                        ></b-form-input>
                    </b-form-group>
                    
                    <b-form-group label="Fecha fin:" label-for="fechaFin">
                        <b-form-input
                        id="fechaFin"
                        v-model="tarea.fechaFin"
                        type="date"                        
                        required
                        ></b-form-input>
                    </b-form-group>                    
                    
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
                    <b-button type="submit" variant="primary" class="w-100 py-2 mt-3">Crear tarea</b-button>            
                </b-form>                    
            </div>
        </div>     
    </div>    
    <div>
        <button type="button" class="btn btn-primary" @click="openModal">Launch demo modal</button>
        <div class="modal fade" id="successModal" ref="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">                
                    <div class="d-flex justify-content-center" v-for="(message, i) in messages" :key="i">
                        <div class="d-flex justify-content-center text-success text-5xl my-4">
                            <SuccessSVG/>
                            <h5 class="mx-2 text-5xl">{{ message }}</h5>                                                                
                        </div>        
                    </div>    
                    <div class="modal-footer">                 
                        <b-button @click="handleAcceptSuccess" variant="success">Aceptar</b-button> 
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
import { createTask } from '@/services/TareaService'

const auth = useAuth();

const show = ref<boolean>(false);
const tarea = ref<Tarea>({
    nombre: "",
    descripcion: "",
    voluntariosRequeridos: 0,    
    fechaInicio: "",
    fechaFin: ""
});

const idEmergencia = ref<number>(-1);
const emergenciaOptions = ref<object[]>([
    {value: -1, text: "Selecciona una emergencia"}
])

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

const submitCreateTaskForm = async () => {
    const response = await createTask(tarea.value, idEmergencia.value, auth.token || "");
    if(response.status == 200){
        success.value = true;
        error.value = false;        
        show.value = true;
        openModal();
    }else{
        error.value = true;
        success.value = false;            
    }
    messages.value = response.messages;
}

const resetForm = async () => {
    show.value = false;
    success.value = false;
    idEmergencia.value = -1;
    tarea.value = {
        nombre: "",
        descripcion: "",
        voluntariosRequeridos: 0,    
        fechaInicio: "",
        fechaFin: ""
    }
}

const successModal = ref<HTMLElement>(document.createElement('div'));

const handleAcceptSuccess = () => {
    closeModal();
    resetForm();    
}

const openModal = () => {
  // Acceder al modal con la referencia
  successModal.value.classList.add('show');
  successModal.value.style.display = 'block';
  document.body.classList.add('modal-open');
};

const closeModal = () => {
  // Ocultar el modal
  successModal.value.classList.remove('show');
  successModal.value.style.display = 'none';
  document.body.classList.remove('modal-open');
};


</script>
