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
							? require('@/assets/menu' + $route.params.menuInOrder.menu.imgUrl)
							: require('@/assets/drink.png')
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
					매장 :
					<span>{{ $route.params.store.storeName }}</span>
				</li>
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
				<li v-if="$route.params.menuInOrder.option.customOption != null">
					퍼스널 옵션 :
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.shotAmericano"
					>
						원두 {{ $route.params.menuInOrder.option.customOption.shotAmericano }}샷 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.shotLatte"
					>
						원액 {{ $route.params.menuInOrder.option.customOption.shotLatte }}샷 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.milk"
					>
						우유 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.vanilla"
					>
						바닐라시럽 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.mint"
					>
						민트시럽 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.condensedMilk"
					>
						연유 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.chocolate"
					>
						초코시럽 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.caramel"
					>
						카라멜시럽 추가
					</span>
					<span
						class="personal-option"
						v-if="$route.params.menuInOrder.option.customOption.soyMilk"
					>
						두유 변경
					</span>
				</li>
			</ul>
			<div class="greenbtn-small-set">
				<button type="button" class="greenbtn-small" @click="openModal()">
					이대로 주문하기
				</button>
				<button type="button" class="greenbtn-small" @click="addToCart">
					장바구니에 담기
				</button>
			</div>
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title mymenu">이대로 주문하기</span>
				<span slot="modal-content" class="modal-content">
					{{ $route.params.store.storeName }}에 <br />
					음료 {{ count }}잔을 주문하시겠습니까?
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doSend" class="popup-button" type="button">취소</button>
					<button @click="orderMyMenu" class="popup-button" type="button">
						주문하기
					</button>
				</div>
			</ModalPopup>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuCountBox from '@/components/menu/MenuCountBox.vue';
import ModalPopup from '@/components/common/ModalPopup.vue';
import { getUserFromCookie } from '@/utils/cookies';
import { addOrder } from '@/api/order';
import WebSocketCustomer from "@/common/WebSocketCustomer";

export default {
	name: 'MyMenuItemDetail',
	components: {
		BlackHeader,
		MenuCountBox,
		ModalPopup,
	},
	data() {
		return {
			modal: false,
			count: 1,
			price: 0,
			isSuccess: false,
		};
	},
	created() {
		try {
			this.price = this.$route.params.menuInOrder.price;
		} catch (error) {
			this.$router.push('/main');
		}
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
		openModal() {
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		doSend() {
			this.closeModal();
		},
		async orderMyMenu() {
			let now = new Date();
			let year = now.getFullYear();
			let month = now.getMonth();
			let date = now.getDate();
			let hours = now.getHours();
			let minutes = now.getMinutes();
			let seconds = now.getSeconds();
			var time = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
			var menus = [];
			var menu = {
				menu: {
					menuId: this.$route.params.menuInOrder.menu.menuId,
					menuName: this.$route.params.menuInOrder.menu.menuName,
				},
				option: this.$route.params.menuInOrder.option,
				price: this.price,
				count: this.count,
			};
			menus.push(menu);
			const data = {
				header: {
					name: 'AddNewOrderRequest',
					userId: getUserFromCookie(),
				},
				payload: {
					orderInfo: {
						orderAt: time,
						progress: 'WAITING',
						totalCount: this.count,
						totalPrice: this.price,
						isNoShow: false,
						store: {
							storeId: this.$route.params.store.storeId,
						},
						user: {
							userId: getUserFromCookie(),
						},
					},
					menus: menus,
				},
			};
			const response = await addOrder(data);
			console.log(response);
			this.$router.push({
				name: 'complete',
				path: '/complete',
				params: response.data.payload,
			});
		},
		addToCart() {
			let index = 0;
			var isSame = false;
			var menu = {
				menuId: this.$route.params.menuInOrder.menu.menuId,
				menuName: this.$route.params.menuInOrder.menu.menuName,
			};
			var option = this.$route.params.menuInOrder.option;
			var price = 0;
			var count = 0;
			if (localStorage.length > 0) {
				for (let i = 0; i < localStorage.length; i++) {
					if (Number(index) < Number(localStorage.key(i))) {
						index = localStorage.key(i);
						var cartItem = JSON.parse(localStorage.getItem(localStorage.key(i)));
						if (
							JSON.stringify(cartItem.menu) == JSON.stringify(menu) &&
							JSON.stringify(cartItem.option) == JSON.stringify(option)
						) {
							isSame = true;
							price = cartItem.price;
							count = cartItem.count;
							break;
						}
					}
				}
			}
			if (isSame == true) {
				var value = {
					id: index,
					menu: menu,
					option: option,
					price: this.price + price,
					count: this.count + count,
				};
			} else {
				index = Number(index) + 1;
				value = {
					id: index,
					menu: menu,
					option: option,
					price: this.price,
					count: this.count,
				};
			}
			localStorage.setItem(index, JSON.stringify(value));

			this.isSuccess = true;
			this.$router.push({
				name: 'my-menu',
				path: '/my-menu',
				params: {
					isSuccess: this.isSuccess,
				},
			});
		},
	},
};
</script>

<style></style>
