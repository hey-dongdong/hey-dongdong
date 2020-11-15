<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">헤이동동</h1>
		</BlackHeader>
		<div class="blackbg">
			<div class="profile">
				<ion-icon name="person" class="user-icon"></ion-icon>
				<span>{{ $store.state.userId }} 님</span>
			</div>
			<button @click="$router.push('/change-pw')" class="greenbtn modify-pw">
				비밀번호 수정
			</button>
			<button @click="openModal" class="goldbtn">LOGOUT</button>
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title">LOGOUT</span>
				<span slot="modal-content" class="modal-content">로그아웃 하시겠습니까?</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doSend" class="popup-button" type="button">취소</button>
					<button @click="logout" class="popup-button" type="button">로그아웃</button>
				</div>
			</ModalPopup>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import ModalPopup from '@/components/common/ModalPopup.vue';
import { deleteCookie } from '@/utils/cookies';
import { signOutUser } from '@/api/auth';
import { getUserFromCookie } from '@/utils/cookies';

export default {
	components: {
		BlackHeader,
		ModalPopup,
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
		async logout() {
			const userData = {
				header: {
					name: 'SignOutRequest',
					userId: getUserFromCookie(),
				},
				payload: {
				},
			};
			await signOutUser(userData);
			this.$store.commit('CLEAR_USERID');
			this.$store.commit('CLEAR_TOKEN');
			deleteCookie('auth');
			deleteCookie('user');
			deleteCookie('username');
			this.$router.push('/sign-in');
		},
	},
};
</script>

<style></style>
