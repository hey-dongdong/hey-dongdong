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
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div
				mapOption = {
					center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
					level: 3, // 지도의 확대 레벨
				};

			var map = new kakao.maps.Map(mapContainer, mapOption);

			var positions = [
				{
					latlng: new kakao.maps.LatLng(37.566627, 126.948417),
				},
				{
					latlng: new kakao.maps.LatLng(37.562803, 126.945578),
				},
				{
					latlng: new kakao.maps.LatLng(37.563837, 126.945475),
				},
				{
					latlng: new kakao.maps.LatLng(37.564343, 126.947613),
				},
			];

			var imageSrc = require('@/assets/marker.png'), // 마커이미지의 주소입니다
				imageSize = new kakao.maps.Size(24, 35), // 마커이미지의 크기입니다
				imageOption = { offset: new kakao.maps.Point(12, 12) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

			// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

			// 마커를 생성합니다
			positions.forEach(function(pos) {
				// 마커를 생성합니다
				// eslint-disable-next-line no-unused-vars
				var marker = new kakao.maps.Marker({
					map: map, // 마커를 표시할 지도
					position: pos.latlng, // 마커의 위치
					image: markerImage,
				});

				var customOverlay = new kakao.maps.CustomOverlay({
					position: pos.latlng,
					xAnchor: 0.5,
					yAnchor: 1.05,
				});

				var content = document.createElement('div');
				content.className = 'overlaybox';

				var buttonContainer = document.createElement('div');
				buttonContainer.className = 'popup-buttons';

				var closeBtn = document.createElement('button');
				closeBtn.className = 'popup-button';
				closeBtn.appendChild(document.createTextNode('취소'));
				closeBtn.onclick = function() {
					customOverlay.setMap(null);
				};

				var selectBtn = document.createElement('button');
				selectBtn.className = 'popup-button';
				selectBtn.appendChild(document.createTextNode('선택'));
				selectBtn.onclick = function() {
					customOverlay.setMap(null);
				};

				buttonContainer.appendChild(closeBtn);
				buttonContainer.appendChild(selectBtn);
				content.appendChild(buttonContainer);

				kakao.maps.event.addListener(marker, 'click', function() {
					customOverlay.setMap(map);
				});

				customOverlay.setContent(content);
				// customOverlay.setMap(map);
			});

			/* 예제 */

			// var mapContainer = document.getElementById('map'), // 지도의 중심좌표
			// 	mapOption = {
			// 		center: new kakao.maps.LatLng(33.451475, 126.570528), // 지도의 중심좌표
			// 		level: 3, // 지도의 확대 레벨
			// 	};

			// var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

			// // 지도에 마커를 표시합니다
			// var marker = new kakao.maps.Marker({
			// 	map: map,
			// 	position: new kakao.maps.LatLng(33.450701, 126.570667),
			// });

			// marker.setMap(map);

			// // 커스텀 오버레이에 표시할 컨텐츠 입니다
			// // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
			// // 별도의 이벤트 메소드를 제공하지 않습니다
			// var customOverlay = new kakao.maps.CustomOverlay({
			// 	position: new kakao.maps.LatLng(33.451475, 126.570528),
			// 	xAnchor: 0.46,
			// 	yAnchor: 0.76,
			// });

			// var content = document.createElement('div');
			// content.className = 'overlaybox';

			// var buttonContainer = document.createElement('div');
			// buttonContainer.className = 'popup-buttons';

			// var closeBtn = document.createElement('button');
			// closeBtn.className = 'popup-button';
			// closeBtn.appendChild(document.createTextNode('취소'));
			// closeBtn.onclick = function() {
			// 	customOverlay.setMap(null);
			// };

			// var selectBtn = document.createElement('button');
			// selectBtn.className = 'popup-button';
			// selectBtn.appendChild(document.createTextNode('선택'));
			// selectBtn.onclick = function() {
			// 	customOverlay.setMap(null);
			// };

			// buttonContainer.appendChild(closeBtn);
			// buttonContainer.appendChild(selectBtn);
			// content.appendChild(buttonContainer);

			// kakao.maps.event.addListener(marker, 'click', function() {
			// 	customOverlay.setMap(map);
			// });

			// customOverlay.setContent(content);
			// customOverlay.setMap(map);
		},
	},
};
</script>

<style scoped>
.wrap {
	position: absolute;
	left: 0;
	bottom: 40px;
	width: 288px;
	height: 132px;
	margin-left: -144px;
	text-align: left;
	overflow: hidden;
	font-size: 12px;
	font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
	line-height: 1.5;
}
.wrap * {
	padding: 0;
	margin: 0;
}
.wrap .info {
	width: 286px;
	height: 120px;
	border-radius: 5px;
	border-bottom: 2px solid #ccc;
	border-right: 1px solid #ccc;
	overflow: hidden;
	background: #fff;
}
.wrap .info:nth-child(1) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}
.info .title {
	padding: 5px 0 0 10px;
	height: 30px;
	background: #eee;
	border-bottom: 1px solid #ddd;
	font-size: 18px;
	font-weight: bold;
}
.info .close {
	position: absolute;
	top: 10px;
	right: 10px;
	color: #888;
	width: 17px;
	height: 17px;
	background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}
.info .close:hover {
	cursor: pointer;
}
.info .body {
	position: relative;
	overflow: hidden;
}
.info .desc {
	position: relative;
	margin: 13px 0 0 90px;
	height: 75px;
}
.desc .ellipsis {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.desc .jibun {
	font-size: 11px;
	color: #888;
	margin-top: -2px;
}
.info .img {
	position: absolute;
	top: 6px;
	left: 5px;
	width: 73px;
	height: 71px;
	border: 1px solid #ddd;
	color: #888;
	overflow: hidden;
}
.info:after {
	content: '';
	position: absolute;
	margin-left: -12px;
	left: 50%;
	bottom: 0;
	width: 22px;
	height: 12px;
	background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png');
}
.info .link {
	color: #5085bb;
}
</style>
