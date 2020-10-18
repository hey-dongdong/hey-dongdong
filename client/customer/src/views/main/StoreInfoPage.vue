<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">픽업위치</h1>
		</BlackHeader>
		<div class="blackheader">
			<PickUpStore></PickUpStore>
		</div>
		<div id="map" style="width:100%; height:100vh;"></div>
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
			var container = document.getElementById('map');
			var options = {
				center: new kakao.maps.LatLng(37.566627, 126.948417),
				level: 3,
			};

			var map2 = new kakao.maps.Map(container, options);

			var imageSrc = '/src/assets/marker.png',
				imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
				imageOption = { offset: new kakao.maps.Point(27, 69) };

			var markerImage = new kakao.maps.MarkerImage(
					imageSrc,
					imageSize,
					imageOption,
				),
				markerPosition = new kakao.maps.LatLng(37.566627, 126.948417);
			var marker = new kakao.maps.Marker({
				position: markerPosition,
				image: markerImage,
			});
			marker.setMap(map2);
		},
	},
};
</script>

<style></style>
