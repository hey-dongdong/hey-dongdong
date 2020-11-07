<template>
	<div class="ordered-menu-card">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ orderMenuItem.menu.menuName }}</div>
			<div class="star-checkbox">
				<input
					type="checkbox"
					value="star"
					:id="'star' + orderMenuItem.menuInOrderId"
					:checked="orderMenuItem.menuLiked"
					@change="toggleLike"
				/>
				<label :for="'star' + orderMenuItem.menuInOrderId"></label>
			</div>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="orderMenuItem.option.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="orderMenuItem.option.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="orderMenuItem.option.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li v-if="orderMenuItem.option.customOption != null">
				퍼스널 옵션 :
				<span
					class="personal-option"
					v-if="orderMenuItem.option.customOption.shotAmericano"
				>
					원두 {{ orderMenuItem.option.customOption.shotAmericano }}샷 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.shotLatte">
					원액 {{ orderMenuItem.option.customOption.shotLatte }}샷 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.milk">
					우유 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.vanilla">
					바닐라시럽 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.mint">
					민트시럽 추가
				</span>
				<span
					class="personal-option"
					v-if="orderMenuItem.option.customOption.condensedMilk"
				>
					연유 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.chocolate">
					초코시럽 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.caramel">
					카라멜시럽 추가
				</span>
				<span class="personal-option" v-if="orderMenuItem.option.customOption.soyMilk">
					두유 변경
				</span>
			</li>
		</ul>
		<div class="ordered-menu-card-footer">
			<span class="ordered-menu-price"> {{ orderMenuItem.price }}원 </span>
			<div class="ordered-menu-count">{{ orderMenuItem.count }}잔</div>
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
				id: this.orderMenuItem.menuInOrderId,
				checked: e.target.checked,
			});
		},
	},
};
</script>

<style></style>
