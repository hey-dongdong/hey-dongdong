<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">나만의 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<p class="mymenu-text">
				주문했던 메뉴를 나만의 메뉴로 저장하고<br />더 간편하게 음료를 주문해보세요.
			</p>
			<MyMenuListItem v-for="item in myMenuItems" :key="item.myMenuId" :myMenuItem="item">
				<button
					slot="delete-button"
					type="button"
					class="delete mymenu"
					@click="openModal(item.myMenuId, item.menuInOrder.menu.menuName)"
				></button>
			</MyMenuListItem>
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title mymenu">나만의 메뉴 삭제</span>
				<span slot="modal-content" class="modal-content">
					{{ deleteMyMenuName }}
					을/를<br />나만의 메뉴에서 삭제하시겠습니까?
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doSend" class="popup-button" type="button">취소</button>
					<button @click="deleteMyMenu" class="popup-button" type="button">
						삭제
					</button>
				</div>
			</ModalPopup>
			<ToastPopup v-bind:show="isSuccess" @close="closeToast">
				<span slot="toast-message">장바구니에 메뉴를 추가했습니다.</span>
			</ToastPopup>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MyMenuListItem from '@/components/menu/MyMenuListItem.vue';
import ModalPopup from '@/components/common/ModalPopup.vue';
import { mapGetters } from 'vuex';
import { getUserFromCookie } from '@/utils/cookies';
import { removeMyMenu } from '@/api/menus';
import ToastPopup from '@/components/common/ToastPopup.vue';

export default {
	components: {
		BlackHeader,
		MyMenuListItem,
		ModalPopup,
		ToastPopup,
	},
	data() {
		return {
			modal: false,
			deleteMyMenuId: '',
			deleteMyMenuName: '',
			isSuccess: this.$route.params.isSuccess,
		};
	},
	computed: {
		...mapGetters(['myMenuItems']),
	},
	created() {
		const data = {
			header: {
				name: 'GetMyMenusRequest',
				userId: getUserFromCookie(),
			},
			payload: {},
		};
		this.$store.dispatch('FETCH_MY_MENUS', data);
		if (this.isSuccess == true) {
			this.isSuccess = false;
			let timer;
			clearTimeout(timer);
			timer = setTimeout(() => (this.isSuccess = true), 1);
		}
	},
	methods: {
		openModal(id, menuName) {
			this.deleteMyMenuId = id;
			this.deleteMyMenuName = menuName;
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		doSend() {
			this.closeModal();
		},
		async deleteMyMenu() {
			// console.log(this.deleteMyMenuId);
			const data = {
				header: {
					name: 'RemoveMyMenuRequest',
					userId: getUserFromCookie(),
				},
				payload: {
					myMenuId: this.deleteMyMenuId,
				},
			};
			await removeMyMenu(data);
			this.closeModal();
			this.$store.dispatch('FETCH_MY_MENUS', {
				header: {
					name: 'GetMyMenusRequest',
					userId: getUserFromCookie(),
				},
				payload: {},
			});
		},
		closeToast() {
			this.isSuccess = false;
		},
	},
};
</script>

<style></style>
