<template>
	<div class="ordered-menu-card">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ completeOrderMenuItem.menuId }}</div>
			<div class="star-checkbox">
				<input
					type="checkbox"
					value="star"
					:id="'star' + completeOrderMenuItem.menuId"
					:checked="completeOrderMenuItem.menuLiked"
					@change="toggleLike"
				/>
				<label :for="'star' + completeOrderMenuItem.id"></label>
			</div>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="completeOrderMenuItem.option.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="completeOrderMenuItem.option.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="completeOrderMenuItem.option.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li v-if="completeOrderMenuItem.option.customOption != null">
				퍼스널 옵션 :
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.shotAmericano"
				>
					원두 {{ completeOrderMenuItem.option.customOption.shotAmericano }}샷 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.shotLatte"
				>
					원액 {{ completeOrderMenuItem.option.customOption.shotLatte }}샷 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.milk"
				>
					우유 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.vanilla"
				>
					바닐라시럽 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.mint"
				>
					민트시럽 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.condensedMilk"
				>
					연유 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.chocolate"
				>
					초코시럽 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.caramel"
				>
					카라멜시럽 추가
				</span>
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.option.customOption.soyMilk"
				>
					두유 변경
				</span>
			</li>
		</ul>
		<div class="ordered-menu-card-footer">
			<span class="ordered-menu-price">
				{{ completeOrderMenuItem.price * completeOrderMenuItem.count }}원
			</span>
			<div class="ordered-menu-count">{{ completeOrderMenuItem.count }}잔</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'complete-ordered-item',
	props: {
		completeOrderMenuItem: Object,
	},
	methods: {
		toggleLike(e) {
			this.$emit('toggle-like', {
				id: this.completeOrderMenuItem.id,
				checked: e.target.checked,
				myMenuId: this.completeOrderMenuItem.myMenuId || '',
			});
		},
	},
};
</script>

<style></style>
