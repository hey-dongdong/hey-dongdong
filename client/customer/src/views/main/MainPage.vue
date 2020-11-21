<template>
	<div class="main">
		<MainHeader></MainHeader>
		<div class="blackbg">
			<PickUpStore></PickUpStore>
			<div class="progress-container">
				<!-- <img src="../../assets/progress50.png" alt="progress" class="progress" /> -->
				<RadialProgressBar
					class="progress"
					:diameter="200"
					:completed-steps="this.percent"
					:total-steps="100"
					:startColor="color"
					:stopColor="color"
				>
				</RadialProgressBar>
				<img src="../../assets/logo.png" alt="logo" class="small-logo" />
				<div class="progress-percent">
					<p class="percent">{{ percent }}</p>
					<p class="total-percent">/100%</p>
				</div>
				<p class="progress-explain">{{ progressExplain }}</p>
			</div>
			<!-- <img src="../../assets/barcode.png" alt="barcode" class="barcode" /> -->
			<!-- <input v-model="barcodeValue" /><br /> -->
			<VueBarcode
				v-if="orderId != ''"
				v-bind:value="orderId"
				class="barcode-box"
				height="50"
				format="CODE39"
			></VueBarcode>
			<ul class="linkboxes">
				<li class="page-link">
					<router-link class="link-name" to="/menu/all">전체<br />메뉴</router-link>
				</li>
				<li class="page-link">
					<router-link class="link-name" to="/my-menu">나만의<br />메뉴</router-link>
				</li>
				<li class="page-link">
					<router-link class="link-name" to="/history">히스토리</router-link>
				</li>
			</ul>
			<img :src="selectedImage" class="ad" />
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title red"
					>NO SHOW ({{ noShowCount }}회)</span
				>
				<span slot="modal-content" class="modal-content">
					제조 완료된 음료를 수령하지 않았습니다.<br />
					{{ noShowMessage1 }}<br />
					{{ noShowMessage2 }}
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="confirm" class="popup-button red" type="button">확인</button>
				</div>
			</ModalPopup>
		</div>
	</div>
</template>

<script>
import MainHeader from '@/components/common/MainHeader.vue';
import PickUpStore from '@/components/main/PickUpStore.vue';
import RadialProgressBar from 'vue-radial-progress';
import VueBarcode from 'vue-barcode';
import { getUserFromCookie, getOrderIdFromCookie, deleteCookie } from '@/utils/cookies';
import { getProgress } from '@/api/order';
import { checkNoShowCount } from '@/api/auth';
import ModalPopup from '@/components/common/ModalPopup.vue';

export default {
	components: {
		MainHeader,
		PickUpStore,
		VueBarcode,
		RadialProgressBar,
		ModalPopup,
	},
	data: function() {
		return {
			color: '#00462A',
			images: [
				require('../../assets/ad-1.png'),
				require('../../assets/ad-2.png'),
				require('../../assets/ad-3.png'),
				require('../../assets/ad-4.png'),
			],
			selectedImage: null,
			modal: false,
			noShowCount: 0,
			noShowMessage1: '',
			noShowMessage2: '',
			orderId: getOrderIdFromCookie() || '',
			percent: 0,
			progressExplain: '주문한 음료가 없습니다',
		};
	},
	computed: {
		storeName() {
			return 'eng';
		},
	},
	async created() {
		this.selectedImage = this.randomItem(this.images);
		const userData = {
			header: {
				name: 'GetNoShowCountRequest',
				userId: getUserFromCookie(),
			},
			payload: {},
		};
		
		const { data } = await checkNoShowCount(userData);

		if (data.payload.noShowCount === '1') {
			this.noShowCount = 1;
			this.noShowMessage1 = '한 달 간 헤이동동 이용이 차단되며,';
			this.noShowMessage2 = '추가 노쇼 시 3개월 간 차단됩니다.';
			this.openModal();
		}
		else if (data.payload.noShowCount === '2') {
			this.noShowCount = 2;
			this.noShowMessage1 = '노쇼 누적 2회로, 헤이동동 이용이 차단되었습니다.';
			this.noShowMessage2 = '3개월 후 이용이 가능합니다.';
			this.openModal();
		}
		else {
			try {
				const orderData = {
				header: {
					name: "GetOrderProgressRequest",
					userId: getUserFromCookie(),
				},
				payload: {
					orderId: this.orderId,
				},
			};
			const { data } = await getProgress(orderData);
			switch(data.payload.progress) {
				case 'WAITING':
					this.percent = 30;
					this.progressExplain = "주문 확인 중입니다";
					break;
				case 'DECLINED':
					this.percent = 0;
					this.progressExplain = "주문이 거절되었습니다";
					break;
				case 'MAKING':
					this.percent = 60;
					this.progressExplain = "음료 제조 중입니다";
					break;
				case 'READY':
					this.percent = 100;
					this.progressExplain = "음료를 찾아가세요"
					break;
				case 'DONE':
					this.percent = 0;
					this.progressExplain = "주문한 음료가 없습니다"
					this.orderId = '';
					break;
				case 'NOSHOW':
					this.percent = 0;
					this.progressExplain = "제조 완료된 음료를 찾아가지 않았습니다"
					break;
				}
			} catch (error) {
					this.progressExplain = "주문한 음료가 없습니다"
			}
			
		}
		
	},
	methods: {
		randomItem(items) {
			return items[Math.floor(Math.random() * items.length)];
		},
		openModal() {
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		confirm() {
			this.closeModal();
			this.$store.commit('CLEAR_USERID');
			this.$store.commit('CLEAR_TOKEN');
			deleteCookie('auth');
			deleteCookie('user');
			deleteCookie('username');
			this.$router.push('sign-in');
		},
	},
};
</script>

<style></style>
