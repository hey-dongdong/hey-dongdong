<template>
	<header class="appheader">
		<router-link to="/sign-in">
			<img src="../../assets/goback.png" alt="go back" class="goback" />
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
