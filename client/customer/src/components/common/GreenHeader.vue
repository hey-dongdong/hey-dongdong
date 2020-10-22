<template>
	<header class="greenheader">
		<ion-icon name="arrow-back" class="header-left" @click="$router.go(-1)"></ion-icon>
		<h1 class="pagename">
			<slot name="pagename"></slot>
		</h1>
	</header>
</template>

<script>
export default {};
</script>

<style></style>
