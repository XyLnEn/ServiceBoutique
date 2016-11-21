const HomeComponent = Vue.component('home', {
	template: '<div>home</div>'
})

// Define the routes
const routes = [
	{ path: '/home', component: HomeComponent },
	{ path: '/shop', component: ShopComponent },
	{ path: '/supplier', component: SupplierComponent }
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
