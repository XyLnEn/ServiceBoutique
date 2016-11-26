// Expose an event bus to all components in order to share data bewteen them
const eventBus = new Vue()

// Configuration variable
const config = {
	"api": "http://localhost:4567",
	"urls": {
		"products": {
			"browse": "/products/all",
			"read": "/products/"
		},
		"transactions": {
			"all": "/transactions/all",
			"read": "/transactions/",
			"sale": "/transaction/new/sale",
			"resupply": "/transactions/new/resupply"
		}
	}
}
