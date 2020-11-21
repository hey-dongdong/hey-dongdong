<template>
	<div>
		<GreenHeader>
			<h1 slot="pagename">비밀번호 수정</h1>
		</GreenHeader>
		<div class="greenbg">
			<form @submit.prevent="submitForm" class="form">
				<ul class="form-item">
					<li>
						<label for="password" class="form-label">Password</label>
						<input
							id="password"
							type="password"
							placeholder="8자 이상 & 숫자, 알파벳, 특수 문자 포함"
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
						<span v-if="!isPasswordSame" class="password-message"
							>패스워드가 다릅니다.</span
						>
						<span v-else class="password-message-same">패스워드가 같습니다.</span>
					</li>
				</ul>
				<button
					v-bind:disabled="!isPasswordValid || !isPasswordSame"
					type="submit"
					class="goldbtn"
				>
					확인
				</button>
				<span class="changepw-message">{{ changePwMessage }}</span>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import { validatePassword } from '@/utils/validation';
import { getUserFromCookie } from '@/utils/cookies';
import { changeUserPw } from '@/api/auth';

export default {
	components: {
		GreenHeader,
	},
	data() {
		return {
			password: '',
			confirmpw: '',
			changePwMessage: '',
		};
	},
	computed: {
		isPasswordValid() {
			return validatePassword(this.password);
		},
		isPasswordSame() {
			if (this.password === this.confirmpw) {
				return true;
			} else {
				return false;
			}
		},
	},
	methods: {
		async submitForm() {
			try {
				const userData = {
					header: {
						name: 'ChangePwRequest',
						userId: getUserFromCookie(),
					},
					payload: {
						userId: getUserFromCookie(),
						newPw: this.password,
					},
				};
				const { data } = await changeUserPw(userData);
				// console.log(data.header.message);
				this.changePwMessage = `${data.header.message}님의 비밀번호가 변경되었습니다.`;
				this.initForm();
			} catch (error) {
				console.log(error);
			}
		},
		initForm() {
			this.password = '';
			this.confirmpw = '';
		},
	},
};
</script>

<style></style>
