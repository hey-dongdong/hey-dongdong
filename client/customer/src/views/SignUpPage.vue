<template>
	<div>
		<AppHeader>
			<h1 slot="pagename">회원가입</h1>
		</AppHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<label for="userId" class="form-label">ID</label>
				<input id="userId" type="text" v-model="userId" />
				<label for="password" class="form-label">Password</label>
				<input id="password" type="password" v-model="password" />
				<label for="confirmpw" class="form-label">Password 확인</label>
				<input id="confirmpw" type="password" />
				<label for="username" class="form-label">이름</label>
				<input id="username" type="text" v-model="username" />
				<label for="email" class="form-label">이메일</label>
				<input id="email" type="email" v-model="email" />
				<label for="phone" class="form-label">휴대폰번호</label>
				<input id="phone" type="tel" v-model="phone" />
				<button type="submit" class="goldbtn">가입하기</button>
			</form>
		</div>
	</div>
</template>

<script>
import AppHeader from '@/components/common/AppHeader.vue';
import { registerUser } from '@/api/index';

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
			phone: '',
		};
	},
	methods: {
		async submitForm() {
			console.log('폼 제출');
			const userData = {
				userId: this.userId,
				password: this.password,
				username: this.username,
				email: this.email,
				phone: this.phone,
			};
			const response = await registerUser(userData);
			console.log(response);
			this.initForm();
		},
		initForm() {
			this.userId = '';
			this.password = '';
			this.username = '';
			this.email = '';
			this.phone = '';
		},
	},
};
</script>

<style scoped></style>
