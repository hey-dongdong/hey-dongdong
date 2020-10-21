<template>
	<div class="main">
		<MainHeader></MainHeader>
		<div class="blackbg">
			<PickUpStore></PickUpStore>
			<div class="progress-container">
				<img src="../../assets/progress50.png" alt="progress" class="progress" />
				<img src="../../assets/logo.png" alt="logo" class="small-logo" />
				<div class="progress-percent">
					<p class="percent">50</p>
					<p class="total-percent">/100%</p>
				</div>
				<p class="progress-explain">주문 확인 중입니다</p>
			</div>
			<img src="../../assets/barcode.png" alt="barcode" class="barcode" />
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
			<img src="../../assets/ad-1.png" alt="ad" class="ad" />
			<img src="../../assets/ad-2.png" alt="ad" class="ad" />
			<img src="../../assets/ad-3.png" alt="ad" class="ad" />
			<img src="../../assets/ad-4.png" alt="ad" class="ad" />
		</div>
	</div>
</template>

<script>
import MainHeader from '@/components/common/MainHeader.vue';
import PickUpStore from '@/components/main/PickUpStore.vue';

export default {
	components: {
		MainHeader,
		PickUpStore,
	},
	computed: {
		storeName() {
			return 'eng';
		},
	},
};
</script>

<style></style>
