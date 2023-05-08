<template>
    <v-container class="d-flex flex-column align-center h-screen">
        <v-sheet width="500px" class="my-5">
            <v-btn color="terciary" variant="outlined" @click="$router.push({ name: 'tasks' })">Volver</v-btn>
        </v-sheet>
        <v-card width="500px" class="pa-5">            
            <v-card-item>                                          
                <v-chip color="{{ getStateColor(task.estado?.descripcion) }}" text-color="white" class="mb-3">
                    {{ task.estado?.descripcion }}
                </v-chip>  
                <v-card-title>{{ task.nombre }}</v-card-title>                                
            </v-card-item>            
            <v-card-item>                    
                <p class="text-subtitle-2"><v-icon icon="mdi-card-text-outline" class="mr-2"></v-icon>Descripción</p>
            </v-card-item>            
            <v-divider></v-divider>
            <v-card-item>
                <div class="d-flex">                   
                    <p>
                        {{ task.descripcion }}
                    </p>            
                </div>
            </v-card-item>
                        
            <v-card-item>                    
                <p class="text-subtitle-2"><v-icon icon="mdi-calendar-clock-outline" class="mr-2"></v-icon>Duración</p>
            </v-card-item>
            <v-divider></v-divider>
            <v-card-item>
                <div class="d-flex">                    
                    <p>{{ task.fechaInicio }} </p>&nbsp;<p class="font-weight-medium" >hasta</p>&nbsp;<p> {{ task.fechaFin }} </p>
                </div>
            </v-card-item>

            <v-card-item>                    
                <p class="text-subtitle-2"><v-icon icon="mdi-account-heart-outline" class="mr-2"></v-icon>Voluntarios</p>
            </v-card-item>
            <v-divider></v-divider>
            <v-card-item>
                <div class="d-flex">                    
                    <p>{{ task.voluntariosInscritos }}/{{ task.voluntariosRequeridos }} voluntarios</p>
                </div>
            </v-card-item>                        
            <v-card-actions>
                <div class="w-100 d-flex justify-end">
                    <v-btn color="primary" @click="$router.push(`/tasks/${route.params.id}/edit`)">
                        Editar
                    </v-btn>
                    <v-btn color="red">
                        Eliminar
                    </v-btn>
                </div>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import Tarea from '@/interfaces/Tarea';
import { getTask } from '@/services/TareaService'
import { useAuth } from '@/store/auth';
import { useRoute } from 'vue-router';
import { getStateColor } from '@/services/EstadoService'
const auth = useAuth();
const route = useRoute();
const task = ref<Tarea>({});

onMounted(async () => {
    console.log();
    task.value = await getTask(auth.token || '', parseInt(`${route.params.id}`) ?? -1)
    console.log(task.value);
})
</script>

<style scoped>

</style>