<template>
	<div class="ordered-menu-card cart-menu">
		<div class="ordered-menu-card-header">
			<div class="ordered-menuname">{{ cartItem.menu.menuName }}</div>
			<button type="button" class="delete" @click="deleteItem(cartItem.id)"></button>
		</div>

		<ul class="ordered-item-list">
			<li>
				컵 선택 :
				<span v-if="cartItem.option.basicOption.isTumblr">텀블러</span>
				<span v-else>매장컵</span>
			</li>
			<li>
				HOT / ICE :
				<span v-if="cartItem.option.basicOption.isHot">HOT</span>
				<span v-else>ICE</span>
			</li>
			<li>
				SIZE :
				<span v-if="cartItem.option.basicOption.isSmall">소</span>
				<span v-else>대</span>
			</li>
			<li v-if="cartItem.option.customOption != null">
				퍼스널 옵션 :
				<span class="personal-option" v-if="cartItem.option.customOption.shotAmericano">
					원두 {{ cartItem.option.customOption.shotAmericano }}샷 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.shotLatte">
					원액 {{ cartItem.option.customOption.shotLatte }}샷 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.milk">
					우유 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.vanilla">
					바닐라시럽 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.mint">
					민트시럽 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.condensedMilk">
					연유 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.chocolate">
					초코시럽 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.caramel">
					카라멜시럽 추가
				</span>
				<span class="personal-option" v-if="cartItem.option.customOption.soyMilk">
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
			var beforePrice = this.price;
			this.price -= this.cartItem.price / this.cartItem.count;
			this.setItemAgain(this.count, beforePrice, this.price);
		},
		plusCount() {
			this.count++;
			var beforePrice = this.price;
			this.price += this.cartItem.price / this.cartItem.count;
			this.setItemAgain(this.count, beforePrice, this.price);
		},
		setItemAgain(count, beforePrice, price) {
			var value = {
				id: this.cartItem.id,
				menu: this.cartItem.menu,
				option: this.cartItem.option,
				price: price,
				count: count,
			};
			localStorage.setItem(this.cartItem.id, JSON.stringify(value));
			this.$emit('set-price', {
				// id: this.cartItem.id,
				before: beforePrice,
				price: price,
			});
		},
		deleteItem(id) {
			localStorage.removeItem(id);
			this.$router.go(0);
		},
	},
};
</script>

<style></style>
