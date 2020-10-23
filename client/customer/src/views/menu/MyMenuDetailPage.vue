<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">나만의 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuCountBox>
				<img
					slot="menu-img"
					:src="
						$route.params.menuInOrder.menu.imgUrl
							? require('@/assets' + $route.params.menuInOrder.menu.imgUrl)
							: require('@/assets/cappuccino.png')
					"
					alt="메뉴이미지"
					class="menu-count-box-img"
				/>
				<span slot="menuname" class="menu-count-box-menuname">
					{{ $route.params.menuInOrder.menu.menuName }}
				</span>
				<span slot="price" class="menu-count-box-price"> {{ price }}원 </span>
				<div slot="menu-count" class="menu-count">
					<button
						type="button"
						class="minus"
						:disabled="count === 1"
						@click="minus"
					></button>
					<span class="menu-count-text">{{ count }}</span>
					<button type="button" class="plus" @click="plus"></button>
				</div>
			</MenuCountBox>
			<ul class="order-detail-list my-menu">
				<li>
					컵 선택 :
					<span v-if="$route.params.menuInOrder.option.basicOption.isTumblr">텀블러</span>
					<span v-else>매장컵</span>
				</li>
				<li>
					HOT / ICE :
					<span v-if="$route.params.menuInOrder.option.basicOption.isHot">HOT</span>
					<span v-else>ICE</span>
				</li>
				<li>
					SIZE :
					<span v-if="$route.params.menuInOrder.option.basicOption.isSmall">소</span>
					<span v-else>대</span>
				</li>
				<li>
					퍼스널 옵션 :
					<span v-if="$route.params.menuInOrder.option.customOption.shotAmericano != 0">
						{{ $route.params.menuInOrder.option.customOption.shotAmericano }}샷 추가,
					</span>
					<span v-if="$route.params.menuInOrder.option.customOption.soyMilk"
						>두유 변경</span
					>
				</li>
			</ul>
			<div class="greenbtn-small-set">
				<button type="button" class="greenbtn-small">이대로 주문하기</button>
				<button type="button" class="greenbtn-small">장바구니에 담기</button>
			</div>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuCountBox from '@/components/menu/MenuCountBox.vue';

export default {
	name: 'MyMenuItemDetail',
	components: {
		BlackHeader,
		MenuCountBox,
	},
	data() {
		return {
			count: 1,
			price: 0,
		};
	},
	created() {
		this.price = this.$route.params.menuInOrder.price;
	},
	methods: {
		minus() {
			this.count--;
			this.price -= this.$route.params.menuInOrder.price;
		},
		plus() {
			this.count++;
			this.price += this.$route.params.menuInOrder.price;
		},
	},
};
</script>

<style></style>
