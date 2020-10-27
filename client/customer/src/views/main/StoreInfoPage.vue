<template>
	<div class="main">
		<BlackHeader>
			<h1 slot="pagename">픽업위치</h1>
		</BlackHeader>
		<div class="blackbg">
			<div class="map-header">
				<PickUpStore></PickUpStore>
				<button class="nearest-store-button" @click="setNearestStore">
					<span>가까운 곳</span>
					<ion-icon name="reload" class="reload-icon"></ion-icon>
				</button>
			</div>
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
	// data() {
	// 	return {
	// 		location: '',
	// 	};
	// },
	// created() {
	// 	this.location = localStorage.getItem('location');
	// },
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
					id: 0,
					store: '학생문화관점',
					location: '학생문화관 지하1층 로비',
					time: '학기 중: 월~금 08:30~19:00 | 토 09:00~14:00',
					vacation: '방학 중: 월~금 08:30~18:00 | 토 09:00~14:00',
					tel: '02-3277-3707',
					latlng: new kakao.maps.LatLng(37.562803, 126.945578),
				},
				{
					id: 1,
					store: '도서관점',
					location: '중앙도서관 지하1층 (B103-2호)',
					time: '학기 중: 월~금 08:30~22:00 | 토 09:00~19:00',
					vacation: '방학 중: 월~금 08:30~19:00 | 토 09:00~17:00',
					tel: '02-3277-4875',
					latlng: new kakao.maps.LatLng(37.562217, 126.949098),
				},
				{
					id: 2,
					store: '국제기숙사점',
					location: '대학원 기숙사 1층 G194호',
					time: '학기 중: 월~금 08:30~22:00',
					vacation: '방학 중: 월~금 09:00~17:30',
					tel: '02-3277-6010',
					latlng: new kakao.maps.LatLng(37.564608, 126.950254), //수정 필요
				},
				{
					id: 3,
					store: '교육관점',
					location: '교육관 B동 1층(158호)',
					time: '학기 중: 월~금 08:30~19:00',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4874',
					latlng: new kakao.maps.LatLng(37.565255, 126.94674),
				},
				{
					id: 4,
					store: '경영관점',
					location: '신세계경영관 지하1층(B112호)',
					time: '학기 중: 월~금 08:30~19:00',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4042',
					latlng: new kakao.maps.LatLng(37.561831, 126.942439),
				},
				{
					id: 5,
					store: '조형관점',
					location: '조형관 A동 1층(19N호)',
					time: '학기 중: 월~금 08:30~19:00',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4044',
					latlng: new kakao.maps.LatLng(37.561019, 126.947964),
				},
				{
					id: 6,
					store: '음악관점',
					location: '음악관 1층(112호)',
					time: '학기 중: 월~금 08:30~19:00',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4043',
					latlng: new kakao.maps.LatLng(37.561201, 126.949255), //경영관
				},
				{
					id: 7,
					store: '공학관점',
					location: '아산공학관 1층(110호)',
					time: '학기 중: 월~금 08:30~19:00',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4873',
					latlng: new kakao.maps.LatLng(37.566627, 126.948417), //아산공학관
				},
				{
					id: 8,
					store: '종합과학관점',
					location: '현대자동차 D동 1층(112호)',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4877',
					latlng: new kakao.maps.LatLng(37.564343, 126.947613),
				},
				{
					id: 9,
					store: '산학협력관점',
					location: '산학협력관 317호',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 (여름)08:30~17:30 | (겨울)08:30~17:00',
					tel: '02-3277-4860',
					latlng: new kakao.maps.LatLng(37.568456, 126.95094),
				},
				{
					id: 10,
					store: '동창회관점',
					location: '동창회관 1층(134호)',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4876',
					latlng: new kakao.maps.LatLng(37.5618, 126.944182),
				},
				{
					id: 11,
					store: '학관점',
					location: '학관 1층(12F호)',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4878',
					latlng: new kakao.maps.LatLng(37.563837, 126.945475),
				},
				{
					id: 12,
					store: '체육관점',
					location: '체육관 B동(B111호)',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4045',
					latlng: new kakao.maps.LatLng(37.561198, 126.947332),
				},
				{
					id: 13,
					store: '법학관점',
					location: '법학관 지하1층',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-4912',
					latlng: new kakao.maps.LatLng(37.563064, 126.949274),
				},
				{
					id: 14,
					store: '헬렌관점',
					location: '헬렌관 3층(307호)',
					time: '학기 중: 월~금 08:30~18:30',
					vacation: '방학 중: 월~금 08:30~17:00',
					tel: '02-3277-2879',
					latlng: new kakao.maps.LatLng(37.562223, 126.948389),
				},
			];

			if (navigator.geolocation) {
				// GeoLocation을 이용해서 접속 위치를 얻어옵니다
				navigator.geolocation.getCurrentPosition(function(position) {
					var lat = position.coords.latitude, // 위도
						lon = position.coords.longitude; // 경도

					var polyline = new kakao.maps.Polyline({
						path: [new kakao.maps.LatLng(lat, lon), positions[0].latlng],
					});
					var minDistance = polyline.getLength();
					var minIndex = 0;
					console.log(minDistance);
					for (let i = 1; i < positions.length; i++) {
						polyline = new kakao.maps.Polyline({
							path: [new kakao.maps.LatLng(lat, lon), positions[i].latlng],
						});
						var distance = polyline.getLength();
						if (minDistance > distance) {
							minDistance = distance;
							minIndex = i;
						}
					}
					var locPosition = positions[minIndex].latlng;
					console.log(locPosition, positions[minIndex].store);
					localStorage.setItem('nearest-store', positions[minIndex].store);
				});
			} else {
				// HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
				// eslint-disable-next-line no-unused-vars
				var locPosition = new kakao.maps.LatLng(37.564343, 126.947613);
			}

			var imageSrc = require('@/assets/marker.png'), // 마커이미지의 주소입니다
				imageSize = new kakao.maps.Size(24, 35), // 마커이미지의 크기입니다
				imageOption = { offset: new kakao.maps.Point(12, 20) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

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

				var title = document.createElement('div');
				title.className = 'map-popup-title';

				var store = document.createElement('h3');
				store.className = 'popup-name';
				store.appendChild(document.createTextNode(pos.store));
				title.appendChild(store);
				content.appendChild(title);

				var location = document.createElement('span');
				location.className = 'store-location';
				location.appendChild(document.createTextNode(pos.location));
				content.appendChild(location);

				var timeContainer = document.createElement('div');

				var time = document.createElement('p');
				time.className = 'time-text';
				time.appendChild(document.createTextNode(pos.time));
				timeContainer.appendChild(time);
				var vacation = document.createElement('p');
				vacation.className = 'time-text';
				vacation.appendChild(document.createTextNode(pos.vacation));
				timeContainer.appendChild(vacation);
				content.appendChild(timeContainer);

				var tel = document.createElement('span');
				tel.className = 'telephone';
				tel.appendChild(document.createTextNode(pos.tel));
				content.appendChild(tel);

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
					localStorage.setItem('store', pos.store);
					customOverlay.setMap(null);
					window.location.reload();
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
		},
		setNearestStore() {
			localStorage.setItem('store', localStorage.getItem('nearest-store'));
			window.location.reload();
		},
	},
};
</script>

<style></style>
