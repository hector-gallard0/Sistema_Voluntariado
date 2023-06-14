<template>
<v-container class="d-flex align-center h-screen"> 
    <v-container class="d-flex flex-column align-center w-75">
        <v-alert v-if="error" class="my-7" type="error" :text="errorMessage"></v-alert>
        <v-card width="500px" class="pa-5 text-center">
            <p class="text-h4 mb-5">Iniciar sesión</p>
            <v-divider></v-divider>
            <v-form @submit.prevent="submitLoginForm" v-model="valid" class="my-5">    
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

                <v-select
                    v-model="idRol"
                    prepend-inner-icon="mdi-account-wrench-outline"
                    :items="items"
                    item-title="label"
                    item-value="value"
                    :rules="[v => !!v || 'Seleccione un tipo de usuario']"
                    label="Tipo de usuario"
                    required
                ></v-select>
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

<script setup lang="ts">
import { ref } from 'vue'
import Usuario from "@/interfaces/Usuario"
import { useAuth } from '@/store/auth';
import router from '@/router';

const store = useAuth();
const valid = ref<boolean>(false);
const idRol = ref<number|undefined>(undefined);
const items = ref<object[]>([
    {value: 2, label: "Voluntario"},
    {value: 1, label: "Coordinador"},
]);
const user = ref<Usuario>({
    email: "",
    password: ""
});
const error = ref<boolean>(false);
const success = ref<boolean>(false);
const messages = ref<object>({});
const errorMessage = ref<string>("");

const submitLoginForm = async () => {
    const response = await store.login(user.value.email ?? '', user.value.password ?? '', idRol.value ?? -1);
    console.log(response);
    if(response.status == 200){
        success.value = true;
        error.value = false;
        messages.value = response.messages || "Inicio de sesión exitoso.";    
        router.push({name: "tasks"});
    }else{
        // alert("No pudo iniciar sesión");
        error.value = true;
        success.value = false;
        errorMessage.value = response.errorMessage || "Hubo un error al iniciar sesión, intente nuevamente.";            
    }
}
</script>


<style scoped>

</style>