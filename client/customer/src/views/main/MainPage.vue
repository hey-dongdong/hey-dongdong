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
					:completed-steps="30"
					:total-steps="100"
					:startColor="color"
					:stopColor="color"
				>
				</RadialProgressBar>
				<img src="../../assets/logo.png" alt="logo" class="small-logo" />
				<div class="progress-percent">
					<p class="percent">30</p>
					<p class="total-percent">/100%</p>
				</div>
				<p class="progress-explain">주문 확인 중입니다</p>
			</div>
			<!-- <img src="../../assets/barcode.png" alt="barcode" class="barcode" /> -->
			<!-- <input v-model="barcodeValue" /><br /> -->
			<VueBarcode
				v-bind:value="barcodeValue"
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
		</div>
	</div>
</template>

<script>
import MainHeader from '@/components/common/MainHeader.vue';
import PickUpStore from '@/components/main/PickUpStore.vue';
import RadialProgressBar from 'vue-radial-progress';
import VueBarcode from 'vue-barcode';

export default {
	components: {
		MainHeader,
		PickUpStore,
		VueBarcode,
		RadialProgressBar,
	},
	data: function() {
		return {
			barcodeValue: '317',
			color: '#00462A',
			images: [
				require('../../assets/ad-1.png'),
				require('../../assets/ad-2.png'),
				require('../../assets/ad-3.png'),
				require('../../assets/ad-4.png'),
			],
			selectedImage: null,
		};
	},
	computed: {
		storeName() {
			return 'eng';
		},
	},
	created() {
		this.selectedImage = this.randomItem(this.images);
	},
	methods: {
		randomItem(items) {
			return items[Math.floor(Math.random() * items.length)];
		},
	},
};
</script>

<style></style>
