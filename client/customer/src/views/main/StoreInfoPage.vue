<template>
	<div class="main">
		<BlackHeader>
			<h1 slot="pagename">픽업위치</h1>
		</BlackHeader>
		<div class="blackbg">
			<PickUpStore></PickUpStore>
			<div id="map"></div>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import PickUpStore from '@/components/main/PickUpStore.vue';

export default {
	components: {
		BlackHeader,
		PickUpStore,
	},
	mounted() {
		if (window.kakao && window.kakao.maps) {
			this.initMap();
		} else {
			const script = document.createElement('script');
			/* global kakao */
			script.onload = () => kakao.maps.load(this.initMap);
			script.src =
				'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=1282f256b672b7706109dff188651f57';
			document.head.appendChild(script);
		}
	},
	methods: {
		initMap() {
			var position = new kakao.maps.LatLng(37.566627, 126.948417);
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div
				mapOption = {
					center: position, // 지도의 중심좌표
					level: 2, // 지도의 확대 레벨
				};

			var map = new kakao.maps.Map(mapContainer, mapOption);

			var imageSrc =
					'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png', // 마커이미지의 주소입니다
				imageSize = new kakao.maps.Size(24, 35), // 마커이미지의 크기입니다
				imageOption = { offset: new kakao.maps.Point(12, 12) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

			// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				position: position,
				image: markerImage, // 마커이미지 설정
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);

			// 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
			var content =
				'<div class="overlaybox">' +
				'	<div class="map-popup-title">' +
				'		<h3 class="popup-name">공학관점</h3>' +
				'		<div class="congestion">' +
				'			<span class="congestion-text">바쁨</span>' +
				'		</div>' +
				'	</div>' +
				'	<span class="store-location">아산공학관 1층(110호)</span>' +
				'	<div>' +
				'		<span class="when">학기 중:</span>' +
				'		<span class="time-text">평일 08:30~19:00</span>' +
				'		<span class="bar">|</span>' +
				'		<span class="time-text">주말 08:30~19:00</span>' +
				'	</div>' +
				'	<div>' +
				'		<span class="when">방학 중:</span>' +
				'		<span class="time-text">평일 08:30~19:00</span>' +
				'		<span class="bar">|</span>' +
				'		<span class="time-text">주말 08:30~19:00</span>' +
				'	</div>' +
				'	<span class="telephone">02-3277-4873</span>' +
				'	<div class="popup-buttons">' +
				'	  <button class="popup-button" type="button">취소</button>' +
				'		<button class="popup-button" type="button">선택</button>' +
				'	</div>' +
				'</div>';

			// 커스텀 오버레이가 표시될 위치입니다

			// 커스텀 오버레이를 생성합니다
			var customOverlay = new kakao.maps.CustomOverlay({
				map: map,
				position: position,
				content: content,
				yAnchor: 1,
			});

			customOverlay.setMap(map);
		},
	},
};
</script>

<style></style>
