<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">비밀번호 찾기</h1>
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
							required
						/>
					</li>
					<li>
						<label for="username" class="form-label">이름</label>
						<input
							id="username"
							type="text"
							placeholder="이름을 입력하세요"
							v-model="userName"
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
				</ul>
				<button type="submit" class="goldbtn">확인</button>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import { findUserPw } from '@/api/auth';

export default {
	components: {
		GreenHeader,
	},
	data() {
		return {
			userId: '',
			userName: '',
			email: '',
		};
	},
	methods: {
		async submitForm() {
			try {
				const userData = {
					header: {
						name: 'FindPwRequest',
						userId: this.userId,
					},
					payload: {
						userId: this.userId,
						userName: this.userName,
						email: this.email,
					},
				};
				const { data } = await findUserPw(userData);
				const param = {
					userId: data.header.message,
					tempPassword: data.payload.tempPassword,
				};
				this.$router.push({
					name: 'find-pw/success',
					path: '/find-pw/success',
					params: param,
				});
			} catch (error) {
				this.$router.push('/find-pw/fail');
			}
		},
	},
};
</script>

<style></style>
