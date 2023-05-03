import { API_URL } from "@/globals"
import Tarea from "@/interfaces/Tarea";

const createTask = async ({nombre, descripcion, voluntariosRequeridos, fechaInicio, fechaFin}:Tarea, idEmergencia:number, token:string) => {
    const response = await fetch(`${API_URL}/tareas`,{
        method: 'POST',
        headers: {
            'Accept': 'Application/json',
            'Content-type': 'Application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            nombre,
            descripcion,
            voluntariosRequeridos,
            fechaInicio,
            fechaFin,
            idEmergencia
        })
    })
    const rawResponse = await response.json();
    return rawResponse;
}

const getTasks = async (token:string) => {
    const response = await fetch(`${API_URL}/tareas`,{
        method: 'GET',
        headers: {
            'Accept': 'Application/json',        
            'Authorization': `Bearer ${token}`
        }        
    })
    const rawResponse = await response.json();
    return rawResponse;
}

export { createTask, getTasks }