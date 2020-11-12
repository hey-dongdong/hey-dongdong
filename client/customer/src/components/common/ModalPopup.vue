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

<style lang="stylus" scoped>
// 오버레이 트랜지션
.modal-enter-active, .modal-leave-active {
  transition: opacity 0.4s;

  // 오버레이에 포함되어 있는 모달 윈도의 트랜지션
  .modal-window {
    transition: opacity 0.4s, transform 0.4s;
  }
}

// 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐
.modal-leave-active {
  transition: opacity 0.6s ease 0.4s;
}

.modal-enter, .modal-leave-to {
  opacity: 0;

  .modal-window {
    opacity: 0;
    transform: translateY(-20px);
  }
}
</style>
