// Component Basket : display the user's basket
const BasketComponent = Vue.component('basket', {
	template: `<div><ul>
	<li v-for="p in content"><product v-bind:product="p"></product></li></ul>
	<div v-if="content.length > 0"><buy-products v-bind:products="content"></buy-products></div>
	</div>`,
	data: function() {
		return {
			content: []
		}
	},
	created: function() {
		const that = this
		eventBus.$on('product-saved', function(product) {
			that.content.push(product)
		})

		eventBus.$on('basket-cleared', function() {
			that.content = []
		})
	}
})

// Component BuyProduct : a button to buy a list of products
const BuyProductsComponent = Vue.component('buy-products', {
	props: ['products'],
	template: `<button class="btn btn-primary" v-on:click="buyProduct(products)">
		Acheter <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>`,
	methods: {
		buyProduct: function(products) {
			const ids = products.map(function(product) {
				return product.id;
			})
			const purchase = {
				"deliverer": "UPS",
				"idList": ids,
				"devise": "EUR",
				"personId": -1114086729
			}
			axios.post(config.api + config.urls.transactions.sale, purchase)
			.catch(function(error) {
				console.error(error);
			})
			eventBus.$emit('basket-cleared')
		}
	}
})
