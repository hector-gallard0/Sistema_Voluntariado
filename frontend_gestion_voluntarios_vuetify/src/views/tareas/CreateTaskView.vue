<template>
    <v-container class="d-flex align-center h-screen">
        <v-container class="d-flex flex-column align-center">
            <v-card width="500px" class="pa-5 text-center">
                <p class="text-h4 mb-5">Crear tarea</p>
                <v-divider></v-divider>
                <v-form @submit.prevent="submitCreateTaskForm" class="my-5">    

                <v-select
                    v-model="idEmergencia"
                    prepend-inner-icon="mdi-account-wrench-outline"
                    :items="items"
                    item-title="label"
                    item-value="value"                    
                    label="Emergencia"
                    required
                ></v-select>
                
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
                    prepend-inner-icon="mdi-calendar-start-outline"                                                                                     
                    label="Fecha inicio"                                                         
                    readonly
                    required                            
                >            
                    <v-menu                         
                        v-model="menu"
                        :close-on-content-click="false"
                        activator="parent">                        
                        <VDatePicker v-model="selectedDate"/>
                    </v-menu>
                </v-text-field>
            
                <v-text-field
                    v-model="tarea.fechaFin"   
                    prepend-inner-icon="mdi-calendar-end-outline"                                                                                     
                    label="Fecha fin"                                                         
                    readonly
                    required                            
                >            
                    <v-menu                         
                        v-model="menu2"
                        :close-on-content-click="false"
                        activator="parent">                        
                        <VDatePicker v-model="selectedDate2"/>
                    </v-menu>
                </v-text-field> 
                
                <v-textarea 
                    v-model="tarea.descripcion"
                    prepend-inner-icon="mdi-text-long"    
                    label="Descripción"                
                />

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
import { ref, onMounted, watch } from 'vue'
import Tarea from "@/interfaces/Tarea"
import {useAuth} from '@/store/auth'
import { getEmergencias } from '@/services/EmergenciaService'
import { createTask } from '@/services/TareaService'

const auth = useAuth();
const selectedDate = ref<Date|null>(null);
const selectedDate2 = ref<Date|null>(null);
const menu = ref<boolean>(false);
const menu2 = ref<boolean>(false);
const tarea = ref<Tarea>({
    nombre: "",
    descripcion: "",
    voluntariosRequeridos: undefined,    
    fechaInicio: "",
    fechaFin: ""
});

const idEmergencia = ref<number|undefined>(undefined);
const items = ref<object[]>([])

const show = ref<boolean>(false);
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

onMounted(async () => {
    const emergencias = await getEmergencias(auth.token || "");    
    emergencias.map(emergencia => 
        items.value?.push({
            value: emergencia.id,
            label: emergencia.nombre
        })
    )
    
})

const submitCreateTaskForm = async () => {
    const response = await createTask(tarea.value, idEmergencia.value ?? -1, auth.token || "");    
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

watch(selectedDate, (newSelectedDate) => {
    tarea.value.fechaInicio = newSelectedDate?.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }).split('/').join('-');
;
    menu.value = false;
})

watch(selectedDate2, (newSelectedDate) => {
    tarea.value.fechaFin = newSelectedDate?.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }).split('/').join('-');;
    menu2.value = false;
})

</script>

<style scoped>
</style>