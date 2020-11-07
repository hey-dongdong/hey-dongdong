<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">비밀번호 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<FindSuccess>
				<span slot="success-explain" class="success-explain"
					>이메일로 로그인 링크를 발송했습니다.</span
				>
				<span slot="next-todo" class="next-todo">이메일로 로그인하세요.</span>
				<span slot="userid">{{ email }}</span>
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
			email: '',
		};
	},
	created() {
		this.email = this.$route.params.email;
	},
};
</script>

<style></style>
