<template>
	<div>
		<header class="blackheader">
			<router-link to="/sign-in">
				<ion-icon name="arrow-back" class="goback"></ion-icon>
			</router-link>
			<h1 class="pagename">장바구니</h1>
		</header>
		<div class="blackbg">
			<div>
				<span>공학관점</span>
				<span>주문이 완료되면 픽업하러 오세요.</span>
			</div>
			<ul>
				<li class="ordered-menu-card cart-menu">
					<div class="ordered-menu-card-header">
						<div class="ordered-menuname">카페라떼</div>
						<button type="button"></button>
					</div>

					<ul class="ordered-item-list">
						<li>컵 선택: 텀블러</li>
						<li>HOT / ICE : ICE</li>
						<li>SIZE : 대</li>
						<li>퍼스널 옵션: 샷 추가, 연유 추가</li>
					</ul>
					<div class="ordered-menu-card-footer">
						<span class="cart-menu-price">2000원</span>
						<div class="option-count cart-menu">
							<button type="button" class="option-minus"></button>
							<span class="option-count-text">1</span>
							<button type="button" class="option-plus"></button>
						</div>
					</div>
				</li>
			</ul>
			<router-link to="/menu/all">+ 더 담으러 가기</router-link>
			<button type="submit" class="greenbtn fixed">
				<span class="price-result">4000원 주문하기</span>
			</button>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style></style>
