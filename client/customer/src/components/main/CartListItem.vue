<template>
	<div class="ordered-menu-card cart-menu">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ cartItem.menuInfo.menuName }}</div>
			<button type="button" class="delete" @click="deleteItem(cartItem.id)"></button>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="cartItem.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="cartItem.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="cartItem.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li>
				퍼스널 옵션 :
				<span
					class="personal-option"
					v-if="cartItem.customOption.shotAmericanoCount != 0"
				>
					원두 {{ cartItem.customOption.shotAmericanoCount }}샷 추가
				</span>
				<span class="personal-option" v-if="cartItem.customOption.shotLatteCount != 0">
					원액 {{ cartItem.customOption.shotLatteCount }}샷 추가
				</span>
				<span class="personal-option" v-if="cartItem.customOption.milk">
					우유 추가
				</span>
				<span class="personal-option" v-if="cartItem.customOption.soyMilk">
					두유 변경
				</span>
			</li>
		</ul>

		<div class="ordered-menu-card-footer">
			<span class="cart-menu-price">{{ price }}원</span>
			<div class="option-count cart-menu">
				<button
					type="button"
					class="option-minus"
					:disabled="this.count === 1"
					@click="minusCount"
				></button>
				<span class="option-count-text">{{ count }}</span>
				<button
					type="button"
					class="option-plus"
					@click="plusCount"
					:key="$route.fullPath"
				></button>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'cart-item',
	props: {
		cartItem: Object,
	},
	data() {
		return {
			count: 1,
			price: 0,
		};
	},
	created() {
		this.count = this.cartItem.count;
		this.price = this.cartItem.price;
	},
	methods: {
		minusCount() {
			this.count--;
			this.price -= this.cartItem.price / this.cartItem.count;
			this.setItemAgain(this.count, this.price);
		},
		plusCount() {
			this.count++;
			this.price += this.cartItem.price / this.cartItem.count;
			this.setItemAgain(this.count, this.price);
		},
		setItemAgain(count, price) {
			var value = {
				id: this.cartItem.id,
				menuInfo: this.cartItem.menuInfo,
				price: price,
				count: count,
				basicOption: this.cartItem.basicOption,
				customOption: this.cartItem.customOption,
			};
			localStorage.setItem(this.cartItem.id, JSON.stringify(value));
		},
		deleteItem(id) {
			localStorage.removeItem(id);
			this.$router.go(0);
		},
	},
};
</script>

<style></style>
