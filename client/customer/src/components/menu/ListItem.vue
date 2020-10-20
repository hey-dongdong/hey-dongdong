<template>
	<div></div>
</template>

<script>
export default {
	computed: {
		menuItems() {
			console.log(this.$store.state.menus);
			return this.$store.state.menus;
		},
	},
};
</script>

<style></style>
