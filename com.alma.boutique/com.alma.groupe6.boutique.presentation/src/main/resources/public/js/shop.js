// Component Shop : display the shop
const ShopComponent = Vue.component('shop', {
	template: `<div class="row">
		<div class="col-md-8">
			<catalog v-bind:products="products"></catalog>
		</div>

		<div class="col-md-4 well">
			<h2><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Mon panier</h2>
			<basket id="shop" url="${config.api + config.urls.transaction.sale}"></basket>
		</div>
	</div>`,
	data : function () {
		return {
			products : []
		}
	},
	methods: {
		loadProducts: function () {
			var that = this;
			axios.get(config.api + config.urls.products.all)
			.then(function (response) {
				that.products = response.data;
			})
		}
	},
	created: function () {
		var that = this;
		// when a sell is done, refresh the list of products
		eventBus.$on('basket-cleared-shop', function () {
			that.products = [];
			// wait 5s before reloading, because latency
			setTimeout(function () {
				that.loadProducts();
			}, 5000);
		})

		// when a product is added to the basket, remove it from the catalog
		eventBus.$on('product-saved-shop', function (product) {
			var index = that.products.indexOf(product);
			that.products.splice(index, 1);
		})

		// when a product is removed from the basket, add it to the catalog
		eventBus.$on('product-removed-shop', function (product) {
			that.products.push(product);
		})
	},
	mounted: function () {
		this.loadProducts();
	}
})

// Component Catalog : diplay all the products sell by the shop
const CatalogComponent = Vue.component('catalog', {
	props: ['products'],
	template: `<div class="panel panel-warning">
		<div class="panel-heading"><h3 class="panel-title">Catalogue des produits</h3></div>
		<div class="panel-body">
			<loading v-show='products.length == 0' message="Chargement du catalogue"></loading>
			<ul>
			<li v-for="p in products"><product v-bind:product="p"></product><save-product v-bind:product="p" basketId="shop"></save-product></li>
			</ul>
		</div>
	</div>`
})

// Component Product : display a simple product
const ProductComponent = Vue.component('product', {
	props: ['product'],
	template: `<div class="panel panel-success">
	<div class="panel-heading"><h4 class="panel-title">Produit <strong>{{ product.name }}</strong></h4></div>
	<div class="panel-body">
		Prix : {{ product.price.value }}, devise : {{ product.price.currency }}, description : {{ product.description }}
	</div>`
})

// Component SaveProduct : save a product in the basket
const SaveProductComponent = Vue.component('save-product', {
	props: ['product', 'basketId'],
	template: `<button class="btn btn-primary" v-on:click="saveProduct(product)">
		Ajouter au panier <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
	</button>`,
	methods: {
		saveProduct: function (product) {
			eventBus.$emit('product-saved-' + this.basketId, product)
		}
	}
})
