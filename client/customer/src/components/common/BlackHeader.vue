<template>
	<header class="blackheader">
		<router-link to="/sign-in">
			<ion-icon name="arrow-back" class="header-left"></ion-icon>
		</router-link>
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
