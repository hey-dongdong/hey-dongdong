<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">헤이동동</h1>
		</BlackHeader>
		<div class="blackbg">
			<div class="profile">
				<ion-icon name="person" class="user-icon"></ion-icon>
				<span>ewha123님</span>
			</div>
			<button @click="openModal" type="submit" class="goldbtn">LOGOUT</button>
			<ModalWithTwoBtn @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title">LOGOUT</span>
				<span slot="modal-content" class="modal-content">로그아웃 하시겠습니까?</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doSend" class="popup-button" type="button">취소</button>
					<button @click="logout" class="popup-button" type="button">로그아웃</button>
				</div>
			</ModalWithTwoBtn>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import ModalWithTwoBtn from '@/components/common/ModalWithTwoBtn.vue';

export default {
	components: {
		BlackHeader,
		ModalWithTwoBtn,
	},
	data() {
		return {
			modal: false,
		};
	},
	methods: {
		openModal() {
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		doSend() {
			this.closeModal();
		},
		logout() {
			this.$router.push('/sign-in');
		},
	},
};
</script>

<style></style>
