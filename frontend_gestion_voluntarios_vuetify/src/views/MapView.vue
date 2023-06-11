<template>
    <div id="map"></div>
</template>

<script lang="ts" setup>
import L from 'leaflet'
import { onMounted } from 'vue'
import Usuario from '@/interfaces/Usuario'
import { getVoluntariosEmergencia } from '@/services/EmergenciaService'
import { useRoute } from 'vue-router'
let markers:[] = [];
const route = useRoute();


onMounted(async () => {
    const voluntarios:Usuario[] = await getVoluntariosEmergencia(Number(route.params.id));
    console.log(voluntarios);
    const markers = new Array(voluntarios.length);

    if(voluntarios != null) {
        let map = L.map('map').setView([-33.309154, -70.892162], 13);
        
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);
    

        for(let i = 0; i < voluntarios.length; i++){         
            let voluntario = voluntarios[i]; 
            
            if(voluntario.longit && voluntario.latit){
                markers[i] = L.marker([voluntario.latit, voluntario.longit]).addTo(map);
                markers[i].bindPopup(`${voluntario.nombre} ${voluntario.apellido}`).openPopup();
            }
        }        
    }

})

</script>

<style scoped>
#map{
    width: 400px;
    height: 400px;
}
</style>