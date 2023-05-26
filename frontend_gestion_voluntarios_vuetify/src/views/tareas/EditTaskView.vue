<template>
    <v-container>
        <v-container class="d-flex flex-column align-center">
            <v-sheet width="500px" class="my-5">
                <v-btn color="terciary" variant="outlined" @click="$router.push('/tasks')">Volver</v-btn>
            </v-sheet>
            <v-card width="500px" class="pa-5 text-center">
                <p class="text-h4 mb-5">Editar tarea</p>
                <v-divider></v-divider>
                <v-form @submit.prevent="submitEditTaskForm" class="my-5">    

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
                    v-model="task.nombre"                                                       
                    prepend-inner-icon="mdi-email-outline"
                    label="Nombre"                    
                    required
                ></v-text-field>

                <v-text-field
                    v-model="task.voluntariosInscritos"                                                       
                    prepend-inner-icon="mdi-lock-outline"
                    label="Voluntarios inscritos"                    
                    type="number"
                    required
                ></v-text-field>                            

                <v-text-field
                    v-model="task.voluntariosRequeridos"                                                       
                    prepend-inner-icon="mdi-lock-outline"
                    label="Voluntarios requeridos"                    
                    type="number"
                    required
                ></v-text-field>                
                
                <v-text-field
                    v-model="task.fechaInicio"   
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
                    v-model="task.fechaFin"   
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
                    v-model="idEstado"
                    prepend-inner-icon="mdi-cached"
                    :items="estadoItems"
                    item-title="label"
                    item-value="value"                    
                    label="Estado"
                    required
                ></v-select>

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
                    v-model="task.descripcion"
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
import Emergencia from '@/interfaces/Emergencia'
import {useAuth} from '@/store/auth'
import { getEmergencias } from '@/services/EmergenciaService'
import { updateTask, getTask } from '@/services/TareaService'
import { getEstados } from '@/services/EstadoService';
import { useRoute } from 'vue-router'

const route = useRoute();
const auth = useAuth();
const selectedDate = ref<Date|null>(null);
const selectedDate2 = ref<Date|null>(null);
const menu = ref<boolean>(false);
const menu2 = ref<boolean>(false);
const task = ref<Tarea>({
    id: undefined,
    nombre: "",
    descripcion: "",
    voluntariosRequeridos: undefined,    
    voluntariosInscritos: undefined,
    estado: undefined,
    fechaInicio: "",
    fechaFin: ""
});
const estadoItems = ref<object[]>([]);
const idEstado = ref<number|undefined>(undefined);
const idEmergencia = ref<number|undefined>(undefined);
const items = ref<object[]>([])
const idsHabilidades = ref<number[]|undefined>(undefined);
const emergencias = ref<Emergencia[]>([]);

const show = ref<boolean>(false);
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

onMounted(async () => {
    emergencias.value = await getEmergencias(auth.token || "");    
    emergencias.value.map(emergencia => 
        items.value?.push({
            value: emergencia.id,
            label: emergencia.nombre
        })
    )
    
    task.value = await getTask(auth.token || "", parseInt(`${route.params.id}`) ?? -1);
    idEmergencia.value = task.value.emergencia?.id;     
    idsHabilidades.value = task.value.habilidades ? [] : undefined;     
    task.value.habilidades?.map(habilidad => {        
        if(habilidad.id){
            console.log("Habilidad ", habilidad);
            idsHabilidades.value?.push(habilidad.id);
        }       
    })    

    const estados = await getEstados(auth.token || "");
    estados.map(estado => 
        estadoItems.value?.push({
            value: estado.id,
            label: estado.descripcion
        })
    )

    idEstado.value = task.value.estado?.id;    
    console.log(task.value);
})

const submitEditTaskForm = async () => {
    const response = await updateTask(task.value, idEmergencia.value ?? -1, idEstado.value ?? -1, auth.token || "");    
    console.log(response);
}

watch(selectedDate, (newSelectedDate) => {
    task.value.fechaInicio = newSelectedDate?.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }).split('/').join('-');

    menu.value = false;
})

watch(selectedDate2, (newSelectedDate) => {
    task.value.fechaFin = newSelectedDate?.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }).split('/').join('-');;
    menu2.value = false;
})

</script>

<style scoped>
</style>