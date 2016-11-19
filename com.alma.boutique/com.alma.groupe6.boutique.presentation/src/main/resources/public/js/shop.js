// Component Shop : display the shop
const ShopComponent = Vue.component('shop', {
		template: '<catalog></catalog>'
})

// Component Catalog : diplay all the products sold by the shop
const CatalogComponent = Vue.component('catalog', {
	template: `<ul>
		<li v-for="p in products"><product v-bind:product="p"></product></li>
	</ul>`,
	data : function () {
		return {
			products : [{
				id: 1,
				name: "Awesome product",
				price: 10.0,
				description: "a simple product"
			}, {
				id: 2,
				name: "Beautiful product",
				price: 20.0,
				description: "a beautiful product"
			}]
		}}
	})

// Component Product : display a simple product
const ProductComponent = Vue.component('product', {
		props: ['product'],
		template: `<p>Nom : {{ product.name }}, prix : {{ product.price }}, description : {{ product.description }}
		<buy-product v-bind:product-id="product.id"></buy-product></p>`
})

// Component BuyProduct : a button to buy a product
const BuyProductComponent = Vue.component('buy-product', {
	props: ['product-id'],
	template: `<button class="btn btn-primary" v-on:click="orderProduct">Acheter</button>`,
	methods: {
		orderProduct: function () {
			console.log("make a GET call to API here ;)");
		}
	}
})
