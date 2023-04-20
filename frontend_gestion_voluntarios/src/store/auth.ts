import {defineStore} from 'pinia';

const useAuth = defineStore('auth', {
    state: () => {
        return {
            token: null
        }
    },
    actions: {
        async register(nombre, apellido, email, password, idInstitucion, voluntario, coordinador){

        },
        async login(email, password){

        }
    }
})