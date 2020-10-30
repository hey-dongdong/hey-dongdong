<template>
	<header class="blackheader">
		<ion-icon name="arrow-back" class="header-left" @click="goTo"></ion-icon>
		<h1 class="pagename" @click="goToMain">
			<slot name="pagename"></slot>
		</h1>
		<router-link to="/cart">
			<ion-icon name="cart" class="cart-icon"></ion-icon>
		</router-link>
	</header>
</template>

<script>
export default {
	methods: {
		goTo() {
			const name = this.$route.name;
			if (
				name === 'menu-all' ||
				name === 'menu-coffee' ||
				name === 'menu-tea' ||
				name === 'menu-ade' ||
				name === 'menu-others'
			) {
				this.$router.push('/main');
			} else {
				this.$router.go(-1);
			}
		},
		goToMain() {
			if (this.$route.name === 'logout') {
				this.$router.push('/main');
			}
		},
	},
};
</script>

<style></style>
