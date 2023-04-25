import { API_URL } from '@/globals';
import {defineStore} from 'pinia';

const useAuth = defineStore('auth', {
    state: () => {
        return {
            token: null,
            rol: "",        
        }
    },
    actions: {
        async register(nombre:string, apellido:string, email:string, password:string, idInstitucion:number, voluntario:boolean, coordinador:boolean){
            const uri = `${API_URL}/register`
            const rawResponse = await fetch(uri, {
                method: 'POST',
                headers: {
                    'Content-Type': 'Application/json',
                    'Accept': 'Application/json'
                },
                body: JSON.stringify({
                    'nombre': nombre,
                    'apellido': apellido,
                    'email': email,
                    'password': password,
                    'idInstitucion': idInstitucion,
                    'voluntario': voluntario,
                    'coordinador': coordinador
                })
            })
            const response = await rawResponse.json();            
            return response;

        },
        async login(email:string, password:string){
            const uri = `${API_URL}/usuarios/login`
            const rawResponse = await fetch(uri, {
                method: 'POST',
                headers: {
                    'Content-Type': 'Application/json',
                    'Accept': 'Application/json'
                },
                body: JSON.stringify({
                    'email': email,
                    'password': password
                })
            })
            const response = await rawResponse.json();
            
            if(response.status == 200){
                this.token = response.data.token;
            }

            return response;
            //TO DO MANAGE RESPONSE
        }
    }
})

export default useAuth;