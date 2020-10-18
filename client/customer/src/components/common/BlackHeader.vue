<template>
	<header class="blackheader">
		<router-link to="/sign-in">
			<ion-icon class="goback" name="arrow-back"></ion-icon>
		</router-link>
		<h1 class="pagename">
			<slot name="pagename"></slot>
		</h1>
	</header>
</template>

<script>
export default {};
</script>

<style></style>
