<template>
	<div class="toast-bg" v-bind:class="{ animation: show }">
		<div class="toast">
			<slot name="toast-message"></slot>
		</div>
	</div>
</template>

<script>
let timer;

export default {
	props: ['show'],
	watch: {
		show(newValue) {
			if (newValue) {
				clearTimeout(timer);
				timer = setTimeout(() => this.$emit('close'), 2000);
			}
		},
	},
};
</script>

<style></style>
