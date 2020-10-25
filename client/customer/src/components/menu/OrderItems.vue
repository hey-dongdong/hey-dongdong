<template>
	<div class="ordered-menu-card">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ orderMenuItem.menuInfo.menu.menuName }}</div>
			<div class="star-checkbox">
				<input
					type="checkbox"
					value="star"
					:id="'star' + orderMenuItem.menuInfo.menuInOrderId"
					:checked="orderMenuItem.menuLiked"
					@change="toggleLike"
				/>
				<label :for="'star' + orderMenuItem.menuInfo.menuInOrderId"></label>
			</div>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="orderMenuItem.menuInfo.option.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="orderMenuItem.menuInfo.option.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="orderMenuItem.menuInfo.option.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li v-if="orderMenuItem.menuInfo.option.customOption != null">
				퍼스널 옵션 :
				<span v-if="orderMenuItem.menuInfo.option.customOption.shotAmericano"></span>
				<span v-else>대</span>
			</li>
		</ul>
		<div class="ordered-menu-card-footer">
			<span class="ordered-menu-price"> {{ orderMenuItem.menuInfo.price }}원 </span>
			<div class="ordered-menu-count">2잔</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'ordered-menu',
	props: {
		orderMenuItem: Object,
	},
	methods: {
		toggleLike(e) {
			this.$emit('toggle-like', {
				id: this.orderMenuItem.menuInfo.menuInOrderId,
				checked: e.target.checked,
			});
		},
	},
};
</script>

<style></style>
