<template>
  <div>
    <b-navbar class="mb-5" variant="white" toggleable="lg">      
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse class="justify-content-center" id="nav-collapse" is-nav>        
        <b-navbar-nav>
          <router-link to="/login" class="nav-item nav-link" active-class="active" v-if="!auth.token">Iniciar Sesión</router-link>
          <router-link to="/register" class="nav-item nav-link"  active-class="active" v-if="!auth.token">Registrarse</router-link>                    
        </b-navbar-nav>
        <b-navbar-nav>
          <li class="nav-item dropdown">
          <a 
            class="nav-link dropdown-toggle" 
            href="#" 
            role="button" 
            data-bs-toggle="dropdown" 
            aria-expanded="false" 
            active-class="active"
            v-if="auth.token && auth.tokenPayload.rol?.nombre == 'COORDINADOR'"  
          >
            Tareas
          </a>
          <ul class="dropdown-menu">
            <li><router-link to="/tasks" class="nav-item nav-link" active-class="active">Administrar tareas</router-link></li>
            <li><router-link to="/task/create" class="nav-item nav-link" active-class="active">Crear tarea</router-link></li>          
          </ul>
        </li>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item  @click="auth.logout" v-if="auth.token">Cerrar sesión</b-nav-item>                    
        </b-navbar-nav>

      </b-collapse>
    </b-navbar>
    <router-view></router-view>
  </div>

</template>

<script setup lang="ts">
  import useAuth from './store/auth'; 
  const auth = useAuth();  
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

label, legend {
  text-align: start;
  width: 100%;  
  color: var(--bs-dark);
}

</style>
