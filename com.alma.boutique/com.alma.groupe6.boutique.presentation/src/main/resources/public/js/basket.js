// Component Basket : display the user's basket
const BasketComponent = Vue.component('basket', {
	template: `<ul>
	<li v-for="p in content"><product v-bind:product="p"></product><buy-product v-bind:id="p.id"></buy-product></li>
	</ul>`,
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

// Component BuyProduct : a button to buy a product
const BuyProductComponent = Vue.component('buy-product', {
	props: ['id'],
	template: `<button class="btn btn-primary" v-on:click="buyProduct(id)">
		Acheter <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>`,
	methods: {
		buyProduct: function(id) {
			console.log("make a GET call to API here to buy product with id = " + id + " ;)");
			eventBus.$emit('basket-cleared')
		}
	}
})
