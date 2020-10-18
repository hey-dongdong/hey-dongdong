<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">회원가입</h1>
		</GreenHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<ul class="form-items">
					<li>
						<label for="userId" class="form-label">ID</label>
						<input
							id="userId"
							type="text"
							placeholder="ID를 입력하세요"
							v-model="userId"
						/>
					</li>
					<li>
						<label for="password" class="form-label">Password</label>
						<input
							id="password"
							type="password"
							placeholder="비밀번호를 입력하세요"
							v-model="password"
						/>
					</li>
					<li>
						<label for="confirmpw" class="form-label">Password 확인</label>
						<input
							id="confirmpw"
							type="password"
							placeholder="비밀번호를 다시 입력하세요"
						/>
					</li>
					<li>
						<label for="username" class="form-label">이름</label>
						<input
							id="username"
							type="text"
							placeholder="이름을 입력하세요"
							v-model="username"
						/>
					</li>
					<li>
						<label for="email" class="form-label">이메일</label>
						<input
							id="email"
							type="email"
							placeholder="이메일을 입력하세요"
							v-model="email"
						/>
					</li>
					<li>
						<label for="phone" class="form-label">휴대폰 번호</label>
						<input
							id="phone"
							type="tel"
							placeholder="휴대폰 번호를 입력하세요"
							v-model="phone"
						/>
					</li>
				</ul>
				<button type="submit" class="goldbtn">가입하기</button>
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

<style></style>
