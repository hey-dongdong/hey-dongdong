<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">카페라떼</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuCountBox></MenuCountBox>
			<form class="form">
				<div class="option-box">
					<span class="option-name">컵 선택</span>
					<div class="radio-box">
						<input type="radio" name="cup-radio" value="0" id="cup0" checked />
						<label for="cup0">매장컵</label>
						<input type="radio" name="cup-radio" value="1" id="cup1" />
						<label for="cup1">텀블러</label>
					</div>
				</div>
				<div class="option-box">
					<span class="option-name">HOT / ICE</span>
					<div class="radio-box">
						<input type="radio" name="hot-ice-radio" value="0" id="hot" checked />
						<label for="hot">HOT</label>
						<input type="radio" name="hot-ice-radio" value="1" id="ice" />
						<label for="ice">ICE</label>
					</div>
				</div>
				<div class="option-box">
					<span class="option-name">SIZE</span>
					<div class="radio-box">
						<input type="radio" name="size-radio" value="0" id="small" checked />
						<label for="small">소</label>
						<input type="radio" name="size-radio" value="1" id="large" />
						<label for="large">대</label>
					</div>
				</div>
				<div class="option-box personal">
					<span class="option-name">퍼스널 옵션</span>
					<div class="checkoption-box">
						<div class="checkoption-check">
							<input type="checkbox" name="personal" value="shotbean" id="shotbean" />
							<label for="shotbean">샷추가-원두(아메리카노)</label>
							<span class="plus-price">+500원</span>
						</div>
						<div class="option-count">
							<button type="button" class="option-minus"></button>
							<span class="option-count-text">1</span>
							<button type="button" class="option-plus"></button>
						</div>
					</div>
					<div class="checkoption-box">
						<div class="checkoption-check">
							<input type="checkbox" name="personal" value="shotliquid" id="shotliquid" />
							<label for="shotliquid">샷추가-원액(카페라떼)</label>
							<span class="plus-price">+800원</span>
						</div>
						<div class="option-count">
							<button type="button" class="option-minus"></button>
							<span class="option-count-text">1</span>
							<button type="button" class="option-plus"></button>
						</div>
					</div>
					<div class="checkoption-check">
						<input type="checkbox" name="personal" value="milk" id="milk" />
						<label for="milk">우유 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input type="checkbox" name="personal" value="vanilla" id="vanilla" />
						<label for="vanilla">바닐라시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input type="checkbox" name="personal" value="mint" id="mint" />
						<label for="mint">민트시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input
							type="checkbox"
							name="personal"
							value="condensedmilk"
							id="condensedmilk"
						/>
						<label for="condensedmilk">연유 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input type="checkbox" name="personal" value="choco" id="choco" />
						<label for="choco">초코시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
					<div class="checkoption-check">
						<input type="checkbox" name="personal" value="caramel" id="caramel" />
						<label for="caramel">카라멜시럽 추가</label>
						<span class="plus-price">+300원</span>
					</div>
				</div>
				<button type="submit" class="greenbtn menu-detail">
					<span>1개 담기</span>
					<span class="price-result">2000원</span>
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
};
</script>

<style></style>
