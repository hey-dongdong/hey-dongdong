<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">아이디 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<FindSuccess>
				<span slot="success-explain" class="success-explain"
					>아이디 찾기가 완료되었습니다.</span
				>
				<span slot="next-todo" class="next-todo">회원님의 ID입니다.</span>
				<span slot="userid">{{ userId }}</span>
				<router-link slot="next-btn" to="/sign-in" class="goldbtn"
					>로그인하러 가기</router-link
				>
			</FindSuccess>
			<router-link to="/find-pw" class="greenbtn">비밀번호 찾기</router-link>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import FindSuccess from '@/components/login/FindSuccess.vue';

export default {
	components: {
		GreenHeader,
		FindSuccess,
	},
	data() {
		return {
			userId: '',
		};
	},
	created() {
		this.userId = this.$route.params.userId.substring(0, 4);
		let starCount = this.$route.params.userId.length - 4;
		for (let i = 0; i < starCount; i++) {
			this.userId += '*';
		}
	},
};
</script>

<style></style>
