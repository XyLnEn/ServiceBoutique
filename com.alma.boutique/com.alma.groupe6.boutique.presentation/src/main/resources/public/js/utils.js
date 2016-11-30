// Component Loading : display a loading message
const LoadingComponent = Vue.component('loading', {
	props: ['message'],
	template: `<div>
		<h3 class="text-center">{{ message }}</h3>
		<div class="progress">
	  <div class="progress-bar progress-bar-warning progress-bar-striped active" style="width: 100%">
	    <span class="sr-only">Loading</span>
	  </div>
	</div>
</div>`
})
