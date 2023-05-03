<template>
<v-container class="d-flex align-center h-screen">
    <v-container class="d-flex flex-column align-center w-75">
        <v-card class="w-25 pa-5 text-center">
            <p class="text-h4 mb-5">Iniciar sesión</p>
            <v-divider></v-divider>
            <v-form @submit.prevent v-model="valid" class="my-5">    
                <v-text-field
                v-model="user.email"                                                       
                label="E-mail"
                type="email"
                required
                ></v-text-field>
                <v-text-field
                v-model="user.password"                                                       
                label="Contraseña"
                type="password"
                required
                ></v-text-field>
                <v-select
                    v-model="select"
                    :items="items"
                    :rules="[v => !!v || 'Item is required']"
                    label="Tipo de usuario"
                    required
                ></v-select>
                <v-btn
                color="primary"
                class="w-100"
                type="submit"
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
const idRol = ref<number>(-1);
const valid = ref<boolean>(false);
const select = ref<string|undefined>(undefined);
const items = ref<string[]>([
    "Coordinador",
    "Voluntario"
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


<style scoped>

</style>