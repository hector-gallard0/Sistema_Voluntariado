<template>
    <div class="d-flex flex-column align-center mb-10">
        <v-sheet width="500px" class="my-5">
            <v-btn color="terciary"  @click="$router.push('/tasks')">Volver</v-btn>
        </v-sheet>
        <v-card 
            width="500px" 
            class="pa-5 text-center"                
        >
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
        
            <v-select
                v-model="idsHabilidades"
                chips
                prepend-inner-icon="mdi-tools"
                label="Habilidades"
                :items="emergencias.find(e => e.id == idEmergencia)?.habilidades"
                item-title="descripcion"
                item-value="id"   
                multiple            
            ></v-select>            
                            
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
    </div>
    
</template>

<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue'
import Tarea from "@/interfaces/Tarea"
import {useAuth} from '@/store/auth'
import { getEmergencias } from '@/services/EmergenciaService'
import { createTask } from '@/services/TareaService'
import Emergencia from '@/interfaces/Emergencia'

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

const idsHabilidades = ref<number[]|undefined>(undefined);
const idEmergencia = ref<number|undefined>(undefined);
const items = ref<object[]>([])
const emergencias = ref<Emergencia[]>([]);

const show = ref<boolean>(false);
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

onMounted(async () => {
    emergencias.value = await getEmergencias(auth.token || "");    
    emergencias.value.map(emergencia => {
        items.value?.push({
            value: emergencia.id,
            label: emergencia.nombre
        })
    })
    console.log(emergencias);
})

const submitCreateTaskForm = async () => {
    console.log("Habilidades = ", idsHabilidades.value);
    const response = await createTask(tarea.value, idEmergencia.value ?? -1, idsHabilidades.value ?? [], auth.token || "");    
    // if(response.status == 200){
    //     success.value = true;
    //     error.value = false;        
    //     show.value = true;        
    // }else{
    //     error.value = true;
    //     success.value = false;            
    // }
    // messages.value = response.messages;
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