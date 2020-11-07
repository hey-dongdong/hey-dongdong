<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">아이디 찾기</h1>
		</GreenHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<ul class="form-item">
					<li>
						<label for="username" class="form-label">이름</label>
						<input id="username" type="text" placeholder="이름을 입력하세요" />
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
				</ul>
				<button type="submit" class="goldbtn">확인</button>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import { findUserId } from '@/api/auth';

export default {
	components: {
		GreenHeader,
	},
	data() {
		return {
			email: '',
		};
	},
	methods: {
		async submitForm() {
			try {
				const userData = {
					header: {
						name: 'FindIdRequest',
						userId: 'N/A',
					},
					payload: {
						email: this.email,
					},
				};
				const { data } = await findUserId(userData);
				const param = {
					userId: data.payload.userId,
				};
				this.$router.push({
					name: 'find-id/success',
					path: '/find-id/success',
					params: param,
				});
			} catch (error) {
				this.$router.push('/find-id/fail');
			}
		},
	},
};
</script>

<style></style>
