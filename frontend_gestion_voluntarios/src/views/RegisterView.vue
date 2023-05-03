    <template>
    <div>
        <h1 class="mb-3">Registrarse</h1>
        <div class="d-flex flex-column">
            <div class="d-flex justify-content-center align-items-center flex-column">
                <b-form class="w-25" @submit.prevent="submitRegisterForm">
                    <b-form-group label="Nombre:" label-for="nombre">
                        <b-form-input
                        id="nombre"
                        v-model="user.nombre"
                        placeholder="Ingresa tu nombre"
                        required
                        ></b-form-input>
                    </b-form-group>
                    
                    <b-form-group label="Apellido:" label-for="apellido">
                        <b-form-input
                        id="apellido"
                        v-model="user.apellido"
                        placeholder="Ingresa tu apellido"
                        required
                    ></b-form-input>
                </b-form-group>
            
                <b-form-group label="Email:" label-for="email">
                    <b-form-input
                    id="email"
                    v-model="user.email"
                    type="email"
                    placeholder="Ingresa tu correo electrónico"
                    required
                    ></b-form-input>
                </b-form-group>
                
                <b-form-group label="Contraseña:" label-for="password">
                    <b-form-input
                        id="password"
                        v-model="user.password"
                        type="password"
                        placeholder="Ingresa una contraseña"
                        required
                    ></b-form-input>
                </b-form-group>
                    
                
                <b-form-group label="Tipo de usuario:"  >
                    <b-form-checkbox v-model="voluntario">Voluntario</b-form-checkbox>
                    <b-form-checkbox v-model="coordinador">Coordinador</b-form-checkbox>
                </b-form-group>
                
                <b-form-group v-if="coordinador" required>
                    <b-form-select v-model="idInstitucion" :options="options" label="Selecciona tu institución">                        
                    </b-form-select>
                </b-form-group>
                
                <b-button type="submit" variant="primary" class="w-100 py-2 mt-3">Registrarse</b-button>            
                    
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
                        <div class="d-flex justify-content-center alert alert-danger w-50 py-3 my-4" role="success">
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
import { ref, onMounted } from 'vue'
import Usuario from "@/interfaces/Usuario"
import Institucion from '@/interfaces/Institucion';
import { getInstituciones } from '@/services/InstitucionService';
import useAuth from '@/store/auth';
import ErrorSVG from '@/components/ErrorSVG.vue'
import SuccessSVG from '@/components/SuccessSVG.vue'
import router from '@/router';

const store = useAuth();
const idInstitucion = ref<number>(-1);
const options = ref<object[]>([
    {value: -1, text: "Selecciona una institución"}
]);
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
        options.value.push({
            value: institucion.id, 
            text: institucion.nombre
        })
    )
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
