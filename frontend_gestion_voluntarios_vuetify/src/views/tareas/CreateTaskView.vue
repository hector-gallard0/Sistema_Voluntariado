<template>
    <v-container class="d-flex align-center h-screen">
        <v-container class="d-flex flex-column align-center">
            <v-card width="500px" class="pa-5 text-center">
                <p class="text-h4 mb-5">Crear tarea</p>
                <v-divider></v-divider>
                <v-form @submit.prevent="submitCreateTaskForm" class="my-5">    
                <v-text-field
                    v-model="tarea.nombre"                                                       
                    prepend-inner-icon="mdi-email-outline"
                    label="Nombre"                    
                    required
                ></v-text-field>
                <v-text-field
                    v-model="tarea.voluntariosRequeridos"                                                       
                    prepend-inner-icon="mdi-lock-outline"
                    label="Voluntarios requeridos"                    
                    type="number"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="tarea.fechaInicio"                    
                    label="Fecha inicio"
                    type="date"                                        
                    required
                ></v-text-field>            
                <v-text-field
                    v-model="tarea.fechaFin"                                  
                    label="Fecha fin"
                    type="date"
                    required
                ></v-text-field>
                <v-btn
                    color="primary"
                    class="w-100"
                    type="submit"
                    size="large"
                >
                    Ingresar
                </v-btn>
                </v-form>
            </v-card>
        </v-container>
    </v-container>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import Tarea from "@/interfaces/Tarea"
import {useAuth} from '@/store/auth'
import { getEmergencias } from '@/services/EmergenciaService'
import { createTask } from '@/services/TareaService'

const auth = useAuth();

const show = ref<boolean>(false);
const tarea = ref<Tarea>({
    nombre: "",
    descripcion: "",
    voluntariosRequeridos: undefined,    
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
    }else{
        error.value = true;
        success.value = false;            
    }
    messages.value = response.messages;
}
</script>

<style scoped>

</style>