<template>
    <div>
        <h1 class="mb-3">Iniciar sesión</h1>
        <div class="d-flex flex-column">
            <div class="d-flex justify-content-center align-items-center flex-column">
                <b-form class="w-25" @submit.prevent="submitLoginForm">
                    <b-form-group label="Email:" label-for="email">
                        <b-form-input
                        id="email"
                        v-model="user.email"
                        type="email"
                        placeholder="Ingresa tu email"
                        required
                        ></b-form-input>
                </b-form-group>
                    
                <b-form-group label="Contraseña:" label-for="password">
                    <b-form-input
                    id="password"
                    v-model="user.password"
                    type="password"
                    placeholder="Ingresa tu contraseña"
                    required
                    ></b-form-input>
                </b-form-group>
                                                
                <b-form-group required  label="Tipo de usuario">
                    <b-form-select v-model="idRol" :options="options">                        
                    </b-form-select>
                </b-form-group>
                
                <b-button type="submit" variant="primary" class="w-100 py-2 mt-3">Ingresar</b-button>            
                    
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
import { ref } from 'vue'
import Usuario from "@/interfaces/Usuario"
import useAuth from '@/store/auth';
import ErrorSVG from '@/components/ErrorSVG.vue'
import SuccessSVG from '@/components/SuccessSVG.vue'
import router from '@/router';

const store = useAuth();
const idRol = ref<number>(-1);
const options = ref<object[]>([
    {value: -1, text: "Selecciona el tipo de usuario"},
    {value: 1, text: "Coordinador"},
    {value: 2, text: "Voluntario"}
]);
const user = ref<Usuario>({
    email: "",
    password: ""
});
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});

const submitLoginForm = async () => {
    const response = await store.login(user.value.email ?? '', user.value.password ?? '', idRol.value ?? '');
    console.log(response);
    if(response.status == 200 ){
        success.value = true;
        error.value = false;
        messages.value = response.messages || "Usuario registrado con éxito.";
        router.push({name: "tasks"});
    }else{
        error.value = true;
        success.value = false;
        messages.value = response.messages || "Hubo un error al registrar al usuario, intente nuevamente.";            
    }
}
</script>
