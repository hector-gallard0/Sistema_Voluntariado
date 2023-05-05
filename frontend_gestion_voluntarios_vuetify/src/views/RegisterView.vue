<template>
<v-container class="d-flex align-center h-screen">
    <v-container class="d-flex flex-column align-center w-75">
        <v-card width="500px" class="pa-5 text-center">
            <p class="text-h4 mb-5">Registrarse</p>
            <v-divider></v-divider>
            <v-form @submit.prevent="submitRegisterForm" class="my-5">    
                <v-text-field
                    v-model="user.nombre"                                                                       
                    prepend-inner-icon="mdi-account-outline"
                    label="Nombre"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="user.apellido"        
                    prepend-inner-icon="mdi-label-outline"                                                               
                    label="Apellido"                
                    required
                ></v-text-field>
                <v-text-field
                    v-model="user.email"                                                                       
                    prepend-inner-icon="mdi-email-outline"
                    label="E-mail"
                    type="email"                
                    required
                ></v-text-field>
                <v-text-field
                    v-model="user.password"    
                    prepend-inner-icon="mdi-lock-outline"                                                                   
                    label="Contraseña"    
                    type="password"            
                    required
                ></v-text-field>                
                
                <v-container class="text-left">
                    <v-label>                    
                        <v-icon
                        icon="mdi-account-wrench-outline"
                        ></v-icon>
                        Tipo de usuario
                    </v-label>
                    <div class="d-flex">
                        <v-checkbox                    
                        v-model="voluntario"
                        label="Voluntario"
                        color="primary"                        
                        ></v-checkbox>
                        <v-checkbox
                        v-model="coordinador"
                        label="Coordinador"
                        color="primary"
                        ></v-checkbox>
                    </div>                
                </v-container>                        
                <v-select
                    v-model="idInstitucion"
                    v-if="coordinador"
                    prepend-inner-icon="mdi-account-wrench-outline"
                    :items="items"
                    item-title="label"
                    item-value="value"                    
                    label="Institución"
                    required
                ></v-select>
                <v-btn
                    color="primary"
                    class="w-100"
                    type="submit"
                    size="large"
                >
                    ENVIAR
                </v-btn>
            </v-form>
        </v-card>
    </v-container>
</v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import Usuario from "@/interfaces/Usuario"
import Institucion from '@/interfaces/Institucion';
import { getInstituciones } from '@/services/InstitucionService';
import { useAuth } from '@/store/auth';
import ErrorSVG from '@/components/ErrorSVG.vue'
import SuccessSVG from '@/components/SuccessSVG.vue'
import router from '@/router';
import { Console } from 'console';

const store = useAuth();
const idInstitucion = ref<number|undefined>(undefined);
const items = ref<object[]>([]);
const user = ref<Usuario>({
    nombre: "",
    apellido: "",
    email: "",
    password: "",
})
const voluntario = ref<boolean>(false);
const coordinador = ref<boolean>(false);
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

onMounted(async () => {
    const instituciones:Institucion[] = await getInstituciones();

    instituciones.map(institucion => 
        items.value.push({
            value: institucion.id, 
            label: institucion.nombre
        })
    )    

    console.log(items);
})

const submitRegisterForm = async () => {
    const response = await store.register(user.value.nombre ?? '', user.value.apellido ?? '', user.value.email ?? '', user.value.password ?? '', idInstitucion.value ?? -1, voluntario.value ?? false, coordinador.value ?? false);
    console.log(response);
    if(response.status == 200 ){
        success.value = true;
        error.value = false;
        messages.value = response.messages || "Usuario registrado con éxito.";
        router.push({name: "login"});
    }else{
        error.value = true;
        success.value = false;
        messages.value = response.messages || "Hubo un error al registrar al usuario, intente nuevamente.";            
    }
}
</script>