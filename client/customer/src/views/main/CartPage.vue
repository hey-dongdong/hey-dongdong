<template>
	<div>
		<header class="blackheader">
			<router-link to="/sign-in">
				<ion-icon name="arrow-back" class="header-left"></ion-icon>
			</router-link>
			<h1 class="pagename">장바구니</h1>
		</header>
		<div class="blackbg">
			<div class="cart-container">
				<div class="cart-store-location">
					<span class="cart-store">공학관점</span>
					<span class="cart-exp">주문이 완료되면 픽업하러 오세요.</span>
				</div>
				<ul class="cart-menu-list">
					<li class="ordered-menu-card cart-menu">
						<div class="ordered-menu-card-header">
							<div class="ordered-menuname">카페라떼</div>
							<button type="button" class="delete"></button>
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
					<li class="ordered-menu-card cart-menu">
						<div class="ordered-menu-card-header">
							<div class="ordered-menuname">카페라떼</div>
							<button type="button" class="delete"></button>
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
					<li class="ordered-menu-card cart-menu">
						<div class="ordered-menu-card-header">
							<div class="ordered-menuname">카페라떼</div>
							<button type="button" class="delete"></button>
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
					<li class="ordered-menu-card cart-menu">
						<div class="ordered-menu-card-header">
							<div class="ordered-menuname">카페라떼</div>
							<button type="button" class="delete"></button>
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
				<router-link to="/menu/all" class="goback-menu">+ 더 담으러 가기</router-link>
				<button type="submit" class="greenbtn fixed cart-menu">
					<span>4000원 주문하기</span>
				</button>
			</div>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style></style>
