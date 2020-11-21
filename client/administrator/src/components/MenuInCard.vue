<template>
	<div class="menu">
		<div class="menu-header">
			<span class="menu-name">{{ menuItem.menu.menuName }}</span>
			<span class="count">{{ menuItem.count }}</span>
		</div>
		<ul class="option-list">
			<li v-if="menuItem.option.basicOption.isTumblr">텀블러</li>
			<li v-else>매장컵</li>
			<li v-if="menuItem.option.basicOption.isHot">HOT</li>
			<li v-else>ICE</li>
			<li v-if="menuItem.option.basicOption.isSmall">소</li>
			<li v-else>대</li>
			<li v-if="menuItem.option.customOption != null">
				<span class="personal-option" v-if="menuItem.option.customOption.shotAmericano">
					원두 샷 추가 ({{ menuItem.option.customOption.shotAmericano }})
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.shotLatte">
					원액 샷 추가 ({{ menuItem.option.customOption.shotLatte }})
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.milk">
					우유 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.vanilla">
					바닐라시럽 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.mint">
					민트시럽 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.condensedMilk">
					연유 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.chocolate">
					초코시럽 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.caramel">
					카라멜시럽 추가
				</span>
				<span class="personal-option" v-if="menuItem.option.customOption.soyMilk">
					두유 변경
				</span>
			</li>
		</ul>
	</div>
</template>

<script>
export default {
	name: 'menu-item',
	props: {
		menuItem: Object,
	},
};
</script>

<style></style>
