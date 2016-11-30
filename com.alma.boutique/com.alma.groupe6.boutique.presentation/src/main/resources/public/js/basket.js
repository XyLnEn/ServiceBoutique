// Component Basket : display the user's basket
const BasketComponent = Vue.component('basket', {
	props: ['id', 'url'],
	template: `<div><ul>
	<li v-for="p in content"><product v-bind:product="p"></product>
	<remove-product v-bind:product="p" v-bind:basketId="id"></remove-product></li></ul>
	<div v-if="content.length > 0"><buy-products v-bind:products="content" v-bind:basketId="id" v-bind:url="url"></buy-products></div>
	</div>`,
	data: function () {
		return {
			content: []
		}
	},
	created: function () {
		const that = this
		eventBus.$on('product-saved-' + that.id, function (product) {
			that.content.push(product)
		})

		eventBus.$on('basket-cleared-' + that.id, function () {
			that.content = []
		})

		eventBus.$on('product-removed-' + that.id, function (product) {
			var index = that.content.indexOf(product);
			that.content.splice(index, 1);
		})
	}
})

// Component BuyProduct : a button to buy a list of products
const BuyProductsComponent = Vue.component('buy-products', {
	props: ['products', 'basketId', 'url'],
	template: `<button class="btn btn-primary" v-on:click="buyProduct(products)">
		Acheter <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
		</button>`,
	methods: {
		buyProduct: function (products) {
			const ids = products.map(function(product) {
				return product.id;
			})
			const purchase = {
				"deliverer": "UPS",
				"idList": ids,
				"devise": "EUR",
				"personId": -1114086729,
				"cardNumber": 666
			}
			console.log(this.url);
			axios.post(this.url, purchase)
			.catch(function (error) {
				console.error(error);
			})
			eventBus.$emit('basket-cleared-' + this.basketId)
		}
	}
})

const RemoveProductComponent = Vue.component('remove-product', {
	props: ['product', 'basketId'],
	template: `<button class="btn btn-danger" v-on:click="removeProduct(product)">
		Retirer du panier
		</button>`,
	methods: {
		removeProduct: function (product) {
			eventBus.$emit('product-removed-' + this.basketId, product);
		}
	}
})
