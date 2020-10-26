<template>
	<div class="pickup-store">
		<h2 class="pickup-location">픽업위치:</h2>
		<h2 class="storename">{{ location }}</h2>
		<router-link to="/store-info">
			<img src="../../assets/location.png" alt="store map button" class="location" />
		</router-link>
	</div>
</template>

<script>
export default {
	// props: ['location'],
	data() {
		return {
			location: '',
		};
	},
	created() {
		this.location = localStorage.getItem('location');
	},
};
</script>

<style></style>
