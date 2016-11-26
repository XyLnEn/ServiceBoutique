// Component Shop : display the shop
const ShopComponent = Vue.component('shop', {
	template: '<catalog v-bind:products="products"></catalog>',
	data : function () {
		return {
			products : []
		}
	},
	mounted: function() {
		var that = this;
		axios.get(config.api + config.urls.products.browse)
		.then(function(response) {
			that.products = response.data;
		})
	}
})

// Component Catalog : diplay all the products sell by the shop
const CatalogComponent = Vue.component('catalog', {
	props: ['products'],
	template: `<ul>
	<li v-for="p in products"><product v-bind:product="p"></product><save-product v-bind:product="p"></save-product></li>
	</ul>`
})

// Component Product : display a simple product
const ProductComponent = Vue.component('product', {
	props: ['product'],
	template: `<p>Nom : {{ product.name }}, prix : {{ product.price.value }}, devise : {{ product.price.currency }}, description : {{ product.description }}</p>`
})

// Component SaveProduct : save a product in the basket
const SaveProductComponent = Vue.component('save-product', {
	props: ['product'],
	template: `<button class="btn btn-primary" v-on:click="saveProduct(product)">
		Ajouter au panier <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
	</button>`,
	methods: {
		saveProduct: function(product) {
			eventBus.$emit('product-saved', product)
		}
	}
})
