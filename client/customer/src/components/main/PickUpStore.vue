<template>
	<div class="pickup-store">
		<h2 class="pickup-location">픽업위치:</h2>
		<h2 class="storename">공학관점</h2>
		<router-link v-bind:to="`/store-info/${storeName}`">
			<img src="../../assets/location.png" alt="store map button" class="location" />
		</router-link>
	</div>
</template>

<script>
export default {};
</script>

<style></style>
