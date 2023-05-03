import { API_URL } from "@/globals"
import Emergencia from '@/interfaces/Emergencia'

const getEmergencias = async (token:string) => {
    if(token.length == 0) return [];
    const rawResponse = await fetch(`${API_URL}/emergencias`, {
      method: 'GET',
      headers: {
        'Accept': 'Application/json',
        'Authorization': `Bearer ${token}`
      }
    });
    const emergencias:Emergencia[] = await rawResponse.json();    
    return emergencias;
}

export { getEmergencias };
