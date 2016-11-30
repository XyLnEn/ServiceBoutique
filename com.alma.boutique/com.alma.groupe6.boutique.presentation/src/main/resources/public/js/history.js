// Component History : display the transaction's history
const HistoryComponent = Vue.component('history', {
	template: `<div class="panel panel-warning">
	<div class="panel-heading">
		<h3 class="panel-title">Transactions</h3>
	</div>
	<div class="panel-body">
	<loading v-show='content.length == 0' message="Chargement des transactions"></loading>
	<ol><li v-for="t in content"><order v-bind:id="t.orderId"></order>
	<person v-bind:id="t.partyId"></person></li></ol>
	</div>`,
	data: function () {
		return {
			content: []
		}
	},
	mounted: function () {
		const that = this;
		axios.get(config.api + config.urls.transaction.all)
		.then(function (response) {
			that.content = response.data;
		});
	}
})

// Component Order: display data about an order
const OrderComponent = Vue.component('order', {
	props: ['id'],
	template: `<div>
	<ul><li v-for="p in order.products"><product v-bind:product="p"></product></li></ul>
	<p>status : {{ order.orderStatus }}, service de livraison : {{ order.deliverer }}</p></div>`,
	data: function () {
			return {
				order: {}
			}
	},
	mounted: function () {
		const that = this;
		axios.get(config.api + config.urls.order.read + that.id)
		.then(function (response) {
			that.order = response.data;
		});
	}
})

// Component Person : display data about a person
const PersonComponent = Vue.component('person', {
	props: ['id'],
	template: `<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Client <strong>{{ person.name }}</strong></h4>
	</div>
	<div class="panel-body">Adresse : {{ person.info.address }} Téléphone : {{ person.info.telNumber }}</div>`,
	data: function () {
		return {
			person: {
				name: "",
				info: {
					address: "",
					telNumber: ""
				}
			}
		}
	},
	mounted: function () {
		const that = this;
		// fetch the complete details about a client
		axios.get(config.api + config.urls.thirdparty.read + that.id)
		.then(function (response) {
			that.person = response.data;
		});
	}
})
