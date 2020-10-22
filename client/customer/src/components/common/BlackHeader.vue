<template>
	<header class="blackheader">
		<ion-icon name="arrow-back" class="header-left" @click="$router.go(-1)"></ion-icon>
		<h1 class="pagename">
			<slot name="pagename"></slot>
		</h1>
		<router-link to="/cart">
			<ion-icon name="cart" class="cart"></ion-icon>
		</router-link>
	</header>
</template>

<script>
export default {};
</script>

<style></style>
