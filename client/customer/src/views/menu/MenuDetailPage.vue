<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">카페라떼</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuCountBox>
				<img
					slot="menu-img"
					:src="
						$route.params.imgUrl
							? require('@/assets/menu' + $route.params.imgUrl)
							: require('@/assets/drink.png')
					"
					alt="메뉴이미지"
					class="menu-count-box-img"
				/>
				<span slot="menuname" class="menu-count-box-menuname">
					{{ $route.params.menuName }}
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
			<form class="form menu-detail">
				<div class="option-box">
					<span class="option-name">컵 선택</span>
					<div class="radio-box">
						<input type="radio" value="cup" id="cup0" v-model="isTumblr" />
						<label for="cup0">매장컵</label>
						<input type="radio" value="tumblr" id="cup1" v-model="isTumblr" />
						<label for="cup1">텀블러</label>
					</div>
				</div>
				<div class="option-box">
					<span class="option-name">HOT / ICE</span>
					<div class="radio-box">
						<input
							type="radio"
							value="hot"
							id="hot"
							v-model="isHot"
							@click="changeHot"
							:disabled="isOnlyIce == true"
						/>
						<label for="hot">HOT</label>
						<input
							type="radio"
							value="ice"
							id="ice"
							v-model="isHot"
							@click="changeIce"
							:disabled="isOnlyHot == true"
						/>
						<label for="ice">ICE</label>
					</div>
				</div>
				<div class="option-box">
					<span class="option-name">SIZE</span>
					<div class="radio-box">
						<input
							type="radio"
							value="small"
							id="small"
							v-model="isSmall"
							@click="changeSmall"
						/>
						<label for="small">소</label>
						<input
							type="radio"
							value="large"
							id="large"
							v-model="isSmall"
							@click="changeLarge"
						/>
						<label for="large">대</label>
					</div>
				</div>
				<div class="option-box personal">
					<span class="option-name">퍼스널 옵션</span>
					<div class="checkoption-box">
						<div class="checkoption-check">
							<input
								type="checkbox"
								value="shotAmericano"
								id="shotAmericano"
								v-model="shotAmericano"
								@change="changeShotAmericano"
							/>
							<label for="shotAmericano">샷추가-원두(아메리카노)</label>
							<span class="plus-price">+500원</span>
						</div>
						<div class="option-count">
							<button
								type="button"
								class="option-minus"
								:disabled="shotAmericanoCount === 0 || shotAmericanoCount === 1"
								@click="minusShotAmericano"
							></button>
							<span class="option-count-text">{{ shotAmericanoCount }}</span>
							<button
								type="button"
								class="option-plus"
								:disabled="shotAmericano == false"
								@click="plusShotAmericano"
							></button>
						</div>
					</div>
					<div class="checkoption-box">
						<div class="checkoption-check">
							<input
								type="checkbox"
								value="shotLatte"
								id="shotLatte"
								v-model="shotLatte"
								@change="changeShotLatte"
							/>
							<label for="shotLatte">샷추가-원액(카페라떼)</label>
							<span class="plus-price">+800원</span>
						</div>
						<div class="option-count">
							<button
								type="button"
								class="option-minus"
								:disabled="shotLatteCount === 0 || shotLatteCount === 1"
								@click="minusShotLatte"
							></button>
							<span class="option-count-text">{{ shotLatteCount }}</span>
							<button
								type="button"
								class="option-plus"
								:disabled="shotLatte == false"
								@click="plusShotLatte"
							></button>
						</div>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="milk"
							id="milk"
							v-model="milk"
							@change="changeMilk"
						/>
						<label for="milk">우유 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="vanilla"
							id="vanilla"
							v-model="vanilla"
							@change="changeVanilla"
						/>
						<label for="vanilla">바닐라시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="mint"
							id="mint"
							v-model="mint"
							@change="changeMint"
						/>
						<label for="mint">민트시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="condensedMilk"
							id="condensedMilk"
							v-model="condensedMilk"
							@change="changeCondensedMilk"
						/>
						<label for="condensedMilk">연유 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="chocolate"
							id="chocolate"
							v-model="chocolate"
							@change="changeChocolate"
						/>
						<label for="chocolate">초코시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="caramel"
							id="caramel"
							v-model="caramel"
							@change="changeCaramel"
						/>
						<label for="caramel">카라멜시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="soyMilk"
							id="soyMilk"
							v-model="soyMilk"
							@change="changeSoyMilk"
						/>
						<label for="soyMilk">두유 변경</label>
						<span class="plus-price">+0원</span>
					</div>
				</div>
				<div class="button-box">
					<button type="button" class="greenbtn fixed" @click="addToCart">
						<span class="count-result">{{ count }}개 담기</span>
						<span class="price-result">{{ price }}원</span>
					</button>
				</div>
			</form>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuCountBox from '@/components/menu/MenuCountBox.vue';

export default {
	components: {
		BlackHeader,
		MenuCountBox,
	},
	data() {
		return {
			count: 1,
			price: 0,
			isTumblr: 'cup',
			isHot: 'hot',
			isSmall: 'small',
			optionPrice: 0,
			shotAmericano: false,
			shotAmericanoCount: 0,
			shotLatte: false,
			shotLatteCount: 0,
			milk: false,
			vanilla: false,
			mint: false,
			condensedMilk: false,
			chocolate: false,
			caramel: false,
			soyMilk: false,
			isOnlyHot: false,
			isOnlyIce: false,
		};
	},
	created() {
		this.price = this.$route.params.smallHotPrice || this.$route.params.smallIcePrice;
		this.isOnlyHot =
			this.$route.params.smallIcePrice == null &&
			this.$route.params.largeIcePrice == null;
		this.isOnlyIce =
			this.$route.params.smallHotPrice == null &&
			this.$route.params.largeHotPrice == null;
		if (this.isOnlyHot) this.isHot = 'hot';
		if (this.isOnlyIce) this.isHot = 'ice';
	},
	methods: {
		minus() {
			this.count--;
			if (this.isSmall === 'small' && this.isHot === 'hot') {
				this.price -= this.$route.params.smallHotPrice;
			} else if (this.isSmall === 'small' && this.isHot === 'ice') {
				this.price -= this.$route.params.smallIcePrice;
			} else if (this.isSmall === 'large' && this.isHot === 'hot') {
				this.price -= this.$route.params.largeHotPrice;
			} else if (this.isSmall === 'large' && this.isHot === 'ice') {
				this.price -= this.$route.params.largeIcePrice;
			}
		},
		plus() {
			this.count++;
			this.price += this.price;
		},
		changeHot() {
			if (this.isSmall === 'small') {
				this.price = this.count * this.$route.params.smallHotPrice + this.optionPrice;
			} else {
				this.price = this.count * this.$route.params.largeHotPrice + this.optionPrice;
			}
		},
		changeIce() {
			if (this.isSmall === 'small') {
				this.price = this.count * this.$route.params.smallIcePrice + this.optionPrice;
			} else {
				this.price = this.count * this.$route.params.largeIcePrice + this.optionPrice;
			}
		},
		changeSmall() {
			if (this.isHot === 'hot') {
				this.price = this.count * this.$route.params.smallHotPrice + this.optionPrice;
			} else {
				this.price = this.count * this.$route.params.smallIcePrice + this.optionPrice;
			}
		},
		changeLarge() {
			if (this.isHot === 'hot') {
				this.price = this.count * this.$route.params.largeHotPrice + this.optionPrice;
			} else {
				this.price = this.count * this.$route.params.largeIcePrice + this.optionPrice;
			}
		},
		changeShotAmericano() {
			if (this.shotAmericano == true) {
				if (this.optionPrice != 0) {
					this.price -= this.optionPrice;
				}
				this.optionPrice += 500;
				this.price += this.optionPrice;
				this.shotAmericanoCount = 1;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 500 * this.shotAmericanoCount;
				this.price += this.optionPrice;
				this.shotAmericanoCount = 0;
			}
		},
		minusShotAmericano() {
			this.price -= this.optionPrice;
			this.optionPrice -= 500;
			this.price += this.optionPrice;
			this.shotAmericanoCount--;
		},
		plusShotAmericano() {
			this.price -= this.optionPrice;
			this.optionPrice += 500;
			this.price += this.optionPrice;
			this.shotAmericanoCount++;
		},
		changeShotLatte() {
			if (this.shotLatte == true) {
				if (this.optionPrice != 0) {
					this.price -= this.optionPrice;
				}
				this.optionPrice += 800;
				this.price += this.optionPrice;
				this.shotLatteCount = 1;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 800 * this.shotLatteCount;
				this.price += this.optionPrice;
				this.shotLatteCount = 0;
			}
		},
		minusShotLatte() {
			this.price -= this.optionPrice;
			this.optionPrice -= 800;
			this.price += this.optionPrice;
			this.shotLatteCount--;
		},
		plusShotLatte() {
			this.price -= this.optionPrice;
			this.optionPrice += 800;
			this.price += this.optionPrice;
			this.shotLatteCount++;
		},
		changeMilk() {
			if (this.milk == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeVanilla() {
			if (this.vanilla == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeMint() {
			if (this.mint == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeCondensedMilk() {
			if (this.condensedMilk == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeChocolate() {
			if (this.chocolate == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeCaramel() {
			if (this.caramel == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeSoyMilk() {
			if (this.caramel == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 0;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 0;
				this.price += this.optionPrice;
			}
		},
		addToCart() {
			let maxIndex = 0;
			if (localStorage.length > 0) {
				for (let i = 0; i < localStorage.length; i++) {
					if (Number(maxIndex) < Number(localStorage.key(i))) {
						maxIndex = localStorage.key(i);
					}
				}
			}
			maxIndex = Number(maxIndex) + 1;
			var customOption = null;
			if (
				this.shotAmericano == true ||
				this.shotLatte == true ||
				this.milk == true ||
				this.vanilla == true ||
				this.mint == true ||
				this.condensedMilk == true ||
				this.chocolate == true ||
				this.caramel == true ||
				this.soyMilk == true
			) {
				customOption = {};
			}
			if (this.shotAmericano == true) {
				customOption.shotAmericano = this.shotAmericanoCount;
			}
			if (this.shotLatte == true) {
				customOption.shotLatte = this.shotLatteCount;
			}
			if (this.milk == true) {
				customOption.milk = this.milk;
			}
			if (this.vanilla == true) {
				customOption.vanilla = this.vanilla;
			}
			if (this.mint == true) {
				customOption.mint = this.mint;
			}
			if (this.condensedMilk == true) {
				customOption.condensedMilk = this.condensedMilk;
			}
			if (this.chocolate == true) {
				customOption.chocolate = this.chocolate;
			}
			if (this.caramel == true) {
				customOption.caramel = this.caramel;
			}
			if (this.soyMilk == true) {
				customOption.soyMilk = this.soyMilk;
			}
			var value = {
				id: maxIndex,
				menu: {
					menuId: this.$route.params.menuId,
					menuName: this.$route.params.menuName,
				},
				option: {
					basicOption: {
						isTumblr: this.isTumblr == 'tumblr' ? true : false,
						isHot: this.isHot == 'hot' ? true : false,
						isSmall: this.isSmall == 'small' ? true : false,
					},
					customOption: customOption,
				},
				price: this.price,
				count: this.count,
			};

			localStorage.setItem(maxIndex, JSON.stringify(value));

			this.$router.push('/menu/all');
		},
	},
};
</script>

<style></style>
