// Component Supplier : display the supplier section of the website
const SupplierComponent = Vue.component('supplier', {
	template: '<supplier-catalog v-bind:products="products"></supplier-catalog>',
	data : function () {
		return {
			products : [{
				id: 1,
				name: "Provided product A",
				price: 10.0,
				description: "a simple product"
			}, {
				id: 2,
				name: "Provided product B",
				price: 20.0,
				description: "a beautiful product"
			}]
		}}
	})

	// Component SupplierCatalog : diplay all the products sell by the supplier
	const SupplierCatalogComponent = Vue.component('supplier-catalog', {
		props: ['products'],
		template: `<ul>
		<li v-for="p in products"><product v-bind:product="p"></product><order-product v-bind:id="p.id"></order-product></li>
		</ul>`
	})

	// Component OrderProduct : a button to order a product from a supplier
	const OrderProductComponent = Vue.component('order-product', {
		props: ['id'],
		template: `<button class="btn btn-primary" v-on:click="orderProduct(id)">Commander</button>`,
		methods: {
			orderProduct: function(id) {
				console.log("make a GET call to API here to order the product with id=" + id + " from the supplier ;)");
			}
		}
	})
