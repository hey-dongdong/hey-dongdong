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
				<li>
					<input
						id="deviceToken"
						type="text"
						class="device-token"
						readonly
					/>
				</li>
			</ul>

			<button type="submit" class="goldbtn">LOGIN</button>
		</form>
		<div class="links">
			<button class="bottom-buttons" @click="findId">아이디 찾기</button>
			|
			<button class="bottom-buttons" @click="findPw">비밀번호 찾기</button>
			|
			<button class="bottom-buttons" @click="signUp">회원가입</button>
		</div>
		<span class="login-message">{{ logMessage }}</span>
	</div>
</template>

<script>
import { signInUser } from '@/api/auth';
import {
	saveAuthToCookie,
	saveRefreshTokenToCookie,
	saveUserToCookie,
	saveUserNameToCookie,
} from '@/utils/cookies';

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
						deviceToken: document.getElementById('deviceToken').value || localStorage.getItem('device-token'),
					},
				};
				const { data } = await signInUser(userData);
				localStorage.setItem('device-token', document.getElementById('deviceToken').value);
				this.$store.commit('SET_ACCESS_TOKEN', data.payload.accessToken);
				this.$store.commit('SET_REFRESH_TOKEN', data.payload.refreshToken);
				this.$store.commit('SET_USERID', data.header.message);
				saveAuthToCookie(data.payload.accessToken);
				saveRefreshTokenToCookie(data.payload.refreshToken);
				saveUserToCookie(data.header.message);
				saveUserNameToCookie(data.payload.userName);
				this.initForm();
				if(localStorage.getItem('store') == null) {
					localStorage.setItem('store-id', 8);
					localStorage.setItem('store', '공학관점');
				}
				this.$router.push('/main');
			} catch (error) {
				if (error.message == "Cannot read property 'token' of undefined") {
					this.logMessage = '아이디 혹은 비밀번호가 잘못되었습니다.';
				} else {
					this.logMessage = '이메일 인증이 필요합니다.';
				}
			}
		},
		initForm() {
			this.id = '';
			this.password = '';
			this.deviceToken = '';
			this.logMessage = '';
		},
		findId() {
			localStorage.setItem('device-token', document.getElementById('deviceToken').value);
			this.$router.push('/find-id');
		},
		findPw() {
			localStorage.setItem('device-token', document.getElementById('deviceToken').value);
			this.$router.push('/find-pw');
		},
		signUp() {
			localStorage.setItem('device-token', document.getElementById('deviceToken').value);
			this.$router.push('/sign-up');
		}
	},
};
</script>

<style></style>
