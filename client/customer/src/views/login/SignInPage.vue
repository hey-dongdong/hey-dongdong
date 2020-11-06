<template>
	<div class="greenbg">
		<img src="../../assets/logo.png" alt="logo" class="logo" />
		<form @submit.prevent="submitForm" class="form">
			<ul class="form-items">
				<li>
					<label for="id" class="form-label">ID</label>
					<input id="id" type="text" v-model="id" placeholder="아이디를 입력하세요" />
				</li>
				<li>
					<label for="password" class="form-label">Password</label>
					<input
						id="password"
						type="password"
						v-model="password"
						placeholder="비밀번호를 입력하세요"
					/>
				</li>
			</ul>

			<button type="submit" class="goldbtn">LOGIN</button>
		</form>
		<div class="links">
			<router-link to="/user/find-info/id">아이디 찾기</router-link>
			|
			<router-link to="/user/find-info/pw">비밀번호 찾기</router-link>
			|
			<router-link to="/sign-up">회원가입</router-link>
		</div>
		<span class="login-message">{{ logMessage }}</span>
	</div>
</template>

<script>
import { signInUser } from '@/api/index';
import { saveAuthToCookie, saveUserToCookie } from '@/utils/cookies';

export default {
	data() {
		return {
			id: '',
			password: '',
			logMessage: '',
		};
	},
	methods: {
		async submitForm() {
			try {
				const userData = {
					header: {
						name: 'SignInRequest',
						userId: this.id,
					},
					payload: {
						userId: this.id,
						password: this.password,
					},
				};
				const { data } = await signInUser(userData);
				console.log(data.payload.token);
				this.$store.commit('SET_TOKEN', data.payload.token);
				this.$store.commit('SET_USERID', data.header.message);
				saveAuthToCookie(data.payload.token);
				saveUserToCookie(data.header.message);
				this.initForm();
				this.$router.push('/main');
			} catch (error) {
				this.logMessage = '아이디 혹은 비밀번호가 잘못되었습니다.';
			}
		},
		initForm() {
			this.id = '';
			this.password = '';
			this.logMessage = '';
		},
	},
};
</script>

<style></style>
