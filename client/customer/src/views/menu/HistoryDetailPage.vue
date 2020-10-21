<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<OrderDetail></OrderDetail>
			<OrderItems></OrderItems>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import OrderDetail from '@/components/menu/OrderDetail.vue';
import OrderItems from '@/components/menu/OrderItems.vue';

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
	},
};
</script>

<style></style>
