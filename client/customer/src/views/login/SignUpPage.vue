<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">회원가입</h1>
		</GreenHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<ul class="form-items">
					<li>
						<label for="userid" class="form-label">ID</label>
						<input
							id="userid"
							type="text"
							placeholder="ID를 입력하세요"
							v-model="userid"
							required
						/>
					</li>
					<li>
						<label for="password" class="form-label">Password</label>
						<input
							id="password"
							type="password"
							placeholder="비밀번호를 입력하세요"
							minlength="8"
							v-model="password"
							required
						/>
					</li>
					<li>
						<label for="confirmpw" class="form-label">Password 확인</label>
						<input
							id="confirmpw"
							type="password"
							class="confirmpw"
							placeholder="비밀번호를 다시 입력하세요"
							minlength="8"
							v-model="confirmpw"
							required
						/>
						<span v-if="password !== confirmpw" class="password-message"
							>패스워드가 다릅니다.</span
						>
						<span v-if="password === confirmpw" class="password-message-same"
							>패스워드가 같습니다.</span
						>
					</li>
					<li>
						<label for="username" class="form-label">이름</label>
						<input
							id="username"
							type="text"
							placeholder="이름을 입력하세요"
							v-model="username"
							required
						/>
					</li>
					<li>
						<label for="email" class="form-label">이메일</label>
						<input
							id="email"
							type="email"
							placeholder="이메일을 입력하세요"
							v-model="email"
							required
						/>
					</li>
					<li>
						<label for="phone" class="form-label">휴대폰 번호</label>
						<input
							id="phone"
							type="tel"
							placeholder="휴대폰 번호를 입력하세요"
							v-model="phone"
							required
						/>
					</li>
				</ul>
				<button type="submit" class="goldbtn">가입하기</button>
				<span class="login-message">{{ signupMessage }}</span>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import { registerUser } from '@/api/index';

export default {
	components: {
		GreenHeader,
	},
	data() {
		return {
			userid: '',
			password: '',
			username: '',
			email: '',
			phone: '',
			confirmpw: '',
			signupMessage: '',
		};
	},
	methods: {
		async submitForm() {
			try {
				const userData = {
					header: {
						name: 'SignUpRequest',
						userId: this.userid,
					},
					payload: {
						userId: this.userid,
						userName: this.username,
						password: this.password,
						phone: this.phone,
						email: this.email,
					},
				};
				const { data } = await registerUser(userData);
				console.log(data.header.message);
				this.signupMessage = `${data.header.message}님이 가입되었습니다.`;
				this.initForm();
			} catch (error) {
				this.signupMessage = '아이디/이메일이 중복되었습니다.';
			}
		},
		initForm() {
			this.userid = '';
			this.password = '';
			this.username = '';
			this.email = '';
			this.phone = '';
			this.confirmpw = '';
		},
	},
};
</script>

<style></style>
