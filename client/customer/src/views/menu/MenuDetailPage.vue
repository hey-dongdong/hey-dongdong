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
							? require('@/assets' + $route.params.imgUrl)
							: require('@/assets/cappuccino.png')
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
						<input type="radio" value="hot" id="hot" v-model="isHot" @click="changeHot" />
						<label for="hot">HOT</label>
						<input type="radio" value="ice" id="ice" v-model="isHot" @click="changeIce" />
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
								value="shotbean"
								id="shotbean"
								v-model="shotbean"
								@change="changeShotbean"
							/>
							<label for="shotbean">샷추가-원두(아메리카노)</label>
							<span class="plus-price">+500원</span>
						</div>
						<div class="option-count">
							<button
								type="button"
								class="option-minus"
								:disabled="shotbeanCount === 0 || shotbeanCount === 1"
								@click="minusShotbean"
							></button>
							<span class="option-count-text">{{ shotbeanCount }}</span>
							<button
								type="button"
								class="option-plus"
								:disabled="shotbean == false"
								@click="plusShotbean"
							></button>
						</div>
					</div>
					<div class="checkoption-box">
						<div class="checkoption-check">
							<input
								type="checkbox"
								value="shotliquid"
								id="shotliquid"
								v-model="shotliquid"
								@change="changeShotliquid"
							/>
							<label for="shotliquid">샷추가-원액(카페라떼)</label>
							<span class="plus-price">+800원</span>
						</div>
						<div class="option-count">
							<button
								type="button"
								class="option-minus"
								:disabled="shotliquidCount === 0 || shotliquidCount === 1"
								@click="minusShotliquid"
							></button>
							<span class="option-count-text">{{ shotliquidCount }}</span>
							<button
								type="button"
								class="option-plus"
								:disabled="shotliquid == false"
								@click="plusShotliquid"
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
							value="condensedmilk"
							id="condensedmilk"
							v-model="condensedmilk"
							@change="changeCondensedmilk"
						/>
						<label for="condensedmilk">연유 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							value="choco"
							id="choco"
							v-model="choco"
							@change="changeChoco"
						/>
						<label for="choco">초코시럽 추가</label>
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
				</div>
				<button type="button" class="greenbtn fixed">
					<span class="count-result">{{ count }}개 담기</span>
					<span class="price-result">{{ price }}원</span>
				</button>
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
			shotbean: false,
			shotbeanCount: 0,
			shotliquid: false,
			shotliquidCount: 0,
			milk: false,
			vanilla: false,
			mint: false,
			condensedmilk: false,
			choco: false,
			caramel: false,
		};
	},
	created() {
		this.price = this.$route.params.smallHotPrice;
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
		changeShotbean() {
			if (this.shotbean == true) {
				if (this.optionPrice != 0) {
					this.price -= this.optionPrice;
				}
				this.optionPrice += 500;
				this.price += this.optionPrice;
				this.shotbeanCount = 1;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 500 * this.shotbeanCount;
				this.price += this.optionPrice;
				this.shotbeanCount = 0;
			}
		},
		minusShotbean() {
			this.price -= this.optionPrice;
			this.optionPrice -= 500;
			this.price += this.optionPrice;
			this.shotbeanCount--;
		},
		plusShotbean() {
			this.price -= this.optionPrice;
			this.optionPrice += 500;
			this.price += this.optionPrice;
			this.shotbeanCount++;
		},
		changeShotliquid() {
			if (this.shotliquid == true) {
				if (this.optionPrice != 0) {
					this.price -= this.optionPrice;
				}
				this.optionPrice += 800;
				this.price += this.optionPrice;
				this.shotliquidCount = 1;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 800 * this.shotliquidCount;
				this.price += this.optionPrice;
				this.shotliquidCount = 0;
			}
		},
		minusShotliquid() {
			this.price -= this.optionPrice;
			this.optionPrice -= 800;
			this.price += this.optionPrice;
			this.shotliquidCount--;
		},
		plusShotliquid() {
			this.price -= this.optionPrice;
			this.optionPrice += 800;
			this.price += this.optionPrice;
			this.shotliquidCount++;
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
		changeCondensedmilk() {
			if (this.condensedmilk == true) {
				this.price -= this.optionPrice;
				this.optionPrice += 300;
				this.price += this.optionPrice;
			} else {
				this.price -= this.optionPrice;
				this.optionPrice -= 300;
				this.price += this.optionPrice;
			}
		},
		changeChoco() {
			if (this.choco == true) {
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
	},
};
</script>

<style></style>
