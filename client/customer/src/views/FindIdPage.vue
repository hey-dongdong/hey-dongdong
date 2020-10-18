<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">아이디 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<label for="username" class="form-label">이름</label>
				<input id="username" type="text" placeholder="이름을 입력하세요" />
				<label for="email" class="form-label">이메일</label>
				<input id="email" type="email" placeholder="이메일을 입력하세요" />
				<button type="submit" class="goldbtn">확인</button>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';

export default {
	components: {
		GreenHeader,
	},
};
</script>

<style></style>
