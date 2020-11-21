<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">비밀번호 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<FindSuccess>
				<span slot="success-explain" class="success-explain"
					>임시 비밀번호가 발급되었습니다.</span
				>
				<span slot="next-todo" class="next-todo">로그인 후 비밀번호를 수정하세요.</span>
				<span slot="userid">{{ tempPassword }}</span>
				<router-link slot="next-btn" to="/sign-in" class="goldbtn">확인</router-link>
			</FindSuccess>
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
			tempPassword: '',
		};
	},
	created() {
		this.tempPassword = this.$route.params.tempPw;
	},
};
</script>

<style></style>
