// Component Supplier : display the supplier section of the website
const SupplierComponent = Vue.component('supplier', {
	template: `<div class="row">
		<div class="col-md-8">
			<supplier-catalog v-bind:products="products"></supplier-catalog>
		</div>

		<div class="col-md-4 well">
			<h2><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Mes commandes</h2>
			<basket id="supplier" url="${config.api + config.urls.transaction.resupply}"></basket>
		</div>
	</div>`,
	methods: {
		loadCatalog: function () {
			var that = this;
			console.log(config.api + config.urls.supplier.all);
			axios.get(config.api + config.urls.supplier.all)
			.then(function (response) {
				that.products = response.data;
			})
		}
	},
	data : function () {
		return {
			products : []
		}
	},
	created: function () {
		var that = this;
		// when a sell is done, refresh the list of products
		eventBus.$on('basket-cleared-supplier', function () {
			that.products = [];
			// wait 5s before reloading, because latency
			setTimeout(function () {
				that.loadCatalog();
			}, 5000);
		})

		// when a product is added to the basket, remove it from the catalog
		eventBus.$on('product-saved-supplier', function (product) {
			var index = that.products.indexOf(product);
			that.products.splice(index, 1);
		})

		// when a product is removed from the basket, add it to the catalog
		eventBus.$on('product-removed-supplier', function (product) {
			that.products.push(product);
		})
	},
	mounted: function () {
		this.loadCatalog();
	}
})

// Component SupplierCatalog : diplay all the products sell by the supplier
const SupplierCatalogComponent = Vue.component('supplier-catalog', {
	props: ['products'],
	template: `<div class="panel panel-warning">
		<div class="panel-heading"><h3 class="panel-title">Catalogue des fournisseurs</h3></div>
		<div class="panel-body">
			<loading v-show='products.length == 0'  message="Chargement des fournisseurs"></loading>
			<ul>
				<li v-for="p in products"><product v-bind:product="p"></product>
				<save-product v-bind:product="p" basketId="supplier"></save-product></li>
				</ul>
		</div>
	</div>`
})
