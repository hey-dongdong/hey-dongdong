<template>
	<transition name="modal" appear>
		<div class="modal modal-overlay" @click.self="$emit('close')">
			<div class="modal-window">
				<div class="modal-content-box">
					<slot name="modal-title"></slot>
					<slot name="modal-content"></slot>
				</div>
				<footer class="modal-footer">
					<slot name="footer"></slot>
				</footer>
			</div>
		</div>
	</transition>
</template>

<script>
export default {};
</script>


