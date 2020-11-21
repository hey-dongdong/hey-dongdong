<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">아이디 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<FindFail>
				<span slot="fail-explain" class="fail-explain">아이디 찾기에 실패했습니다.</span>
			</FindFail>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import FindFail from '@/components/login/FindFail.vue';

export default {
	components: {
		GreenHeader,
		FindFail,
	},
};
</script>

<style></style>
