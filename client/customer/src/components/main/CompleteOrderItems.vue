<template>
	<div class="ordered-menu-card">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ completeOrderMenuItem.menuInfo.menuName }}</div>
			<div class="star-checkbox">
				<input
					type="checkbox"
					value="star"
					:id="'star' + completeOrderMenuItem.id"
					:checked="completeOrderMenuItem.menuLiked"
					@change="toggleLike"
				/>
				<label :for="'star' + completeOrderMenuItem.id"></label>
			</div>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="completeOrderMenuItem.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="completeOrderMenuItem.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="completeOrderMenuItem.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li>
				퍼스널 옵션 :
				<span
					class="personal-option"
					v-if="completeOrderMenuItem.customOption.shotAmericanoCount != 0"
				>
					{{ completeOrderMenuItem.customOption.shotAmericanoCount }}샷 추가
				</span>
				<span class="personal-option" v-if="completeOrderMenuItem.customOption.milk">
					우유 추가
				</span>
				<span class="personal-option" v-if="completeOrderMenuItem.customOption.soyMilk">
					두유 변경
				</span>
			</li>
		</ul>
		<div class="ordered-menu-card-footer">
			<span class="ordered-menu-price"> {{ completeOrderMenuItem.price }}원 </span>
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
			});
		},
	},
};
</script>

<style></style>
