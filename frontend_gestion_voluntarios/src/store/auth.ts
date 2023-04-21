import {defineStore} from 'pinia';

const useAuth = defineStore('auth', {
    state: () => {
        return {
            token: null,
            rol: "",
            baseURL: "http://localhost:8080/api/v1"
        }
    },
    actions: {
        async register(nombre:string, apellido:string, email:string, password:string, idInstitucion:number, voluntario:boolean, coordinador:boolean){
            const uri = `${this.baseURL}/usuarios/register`
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
            console.log(response);
            //TO DO MANAGE RESPONSE

        },
        async login(email:string, password:string){
            const uri = `${this.baseURL}/usuarios/login`
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
            console.log(response);
            //TO DO MANAGE RESPONSE
        }
    }
})

export default useAuth;