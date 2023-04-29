import { API_URL } from '@/globals';
import AuthData from '@/interfaces/AuthData';
import { parseJwt } from '@/services/JwtService';
import {defineStore} from 'pinia';

const authData:AuthData = {
    token: '',
    tokenPayload: {}
}

const useAuth = defineStore('auth', {
    state: () => {
        return {
            authData
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
        async login(email:string, password:string, idRol:number){
            const uri = `${API_URL}/login`
            const rawResponse = await fetch(uri, {
                method: 'POST',
                headers: {
                    'Content-Type': 'Application/json',
                    'Accept': 'Application/json'
                },
                body: JSON.stringify({
                    'email': email,
                    'password': password,
                    'idRol': idRol
                })
            })            
            const response = await rawResponse.json();
                        
            if(response.status == 200){
                this.authData.token = response.data.token;
                this.authData.tokenPayload = parseJwt(this.authData.token);
                console.log(this.authData.tokenPayload);
            }            

            return response;
            //TO DO MANAGE RESPONSE
        }
    }
})

export default useAuth;