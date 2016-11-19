const HomeComponent = Vue.component('home', {
    template: '<div>home</div>'
})

const ProviderComponent = Vue.component('provider', {
    template: '<div>provider</div>'
})

// Define the routes
const routes = [
  { path: '/home', component: HomeComponent },
  { path: '/shop', component: ShopComponent },
  { path: '/provider', component: ProviderComponent }
]

const router = new VueRouter({
  routes
})

// Boot the application
const app = new Vue({
  router,
  el : '#app',
  data: {
      message: 'Hello Vue.js !'
    }
})
