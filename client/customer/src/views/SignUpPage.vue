<template>
	<div>
		<AppHeader>
			<h1 slot="pagename">회원가입</h1>
		</AppHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<label for="userId" class="form-label">ID</label>
				<input id="userId" type="text" v-model="id" />
				<label for="password" class="form-label">Password</label>
				<input id="password" type="password" v-model="password" />
				<label for="confirmpw" class="form-label">Password 확인</label>
				<input id="confirmpw" type="password" />
				<label for="username" class="form-label">이름</label>
				<input id="username" type="text" v-model="name" />
				<label for="email" class="form-label">이메일</label>
				<input id="email" type="email" v-model="email" />
				<label for="phone" class="form-label">휴대폰번호</label>
				<input id="phone" type="tel" v-model="phonenumber" />
				<button type="submit" class="goldbtn">가입하기</button>
			</form>
		</div>
	</div>
</template>

<script>
import AppHeader from '@/components/common/AppHeader.vue';

export default {
	components: {
		AppHeader,
	},
	data() {
		return {
			userId: '',
			password: '',
			username: '',
			email: '',
			phonenumber: '',
		};
	},
	methods: {
		submitForm() {
			console.log('폼 제출');
		},
	},
};
</script>

<style scoped></style>
