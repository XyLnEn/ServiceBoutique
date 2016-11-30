// Expose an event bus to all components in order to share data bewteen them
const eventBus = new Vue()

// Configuration variable
const config = {
	"api": "http://localhost:4567",
	"urls": {
		"products": {
			"all": "/products/all",
			"read": "/products/"
		},
		"supplier": {
			"all": "/supplier/catalog/all"
		},
		"transaction": {
			"all": "/transaction/all",
			"read": "/transaction/",
			"sale": "/transaction/new/sale",
			"resupply": "/transaction/new/resupply"
		},
		"thirdparty": {
			"all": "/person/all",
			"read": "/person/"
		},
		"order": {
			"all": "/order/all",
			"read": "/order/"
		}
	}
}
