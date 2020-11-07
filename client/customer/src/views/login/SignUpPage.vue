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
						<span v-if="!isPasswordSame" class="password-message"
							>패스워드가 다릅니다.</span
						>
						<span v-else class="password-message-same">패스워드가 같습니다.</span>
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
							@keyup="getMask(this)"
							v-model="phone"
							required
						/>
					</li>
				</ul>
				<button
					v-bind:disabled="
						!isEmailValid ||
							!isPasswordValid ||
							!isPasswordSame ||
							!userid ||
							!username ||
							!phone
					"
					type="submit"
					class="goldbtn"
				>
					가입하기
				</button>
				<span class="login-message">{{ signupMessage }}</span>
			</form>
		</div>
	</div>
</template>

<script>
import GreenHeader from '@/components/common/GreenHeader.vue';
import { registerUser } from '@/api/auth';
import { validateEmail, validatePassword } from '@/utils/validation';

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
	computed: {
		isEmailValid() {
			return validateEmail(this.email);
		},
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
		// isPhoneValid() {
		// 	return validatePhone(this.phone);
		// },
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
		getMask(phoneNumber) {
			if (!phoneNumber) return phoneNumber;
			phoneNumber = phoneNumber.replace(/[^0-9]/g, '');

			let res = '';
			if (phoneNumber.length < 3) {
				res = phoneNumber;
			} else {
				if (phoneNumber.substr(0, 2) == '02') {
					if (phoneNumber.length <= 5) {
						//02-123-5678
						res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3);
					} else if (phoneNumber.length > 5 && phoneNumber.length <= 9) {
						//02-123-5678
						res =
							phoneNumber.substr(0, 2) +
							'-' +
							phoneNumber.substr(2, 3) +
							'-' +
							phoneNumber.substr(5);
					} else if (phoneNumber.length > 9) {
						//02-1234-5678
						res =
							phoneNumber.substr(0, 2) +
							'-' +
							phoneNumber.substr(2, 4) +
							'-' +
							phoneNumber.substr(6);
					}
				} else {
					if (phoneNumber.length < 8) {
						res = phoneNumber;
					} else if (phoneNumber.length == 8) {
						res = phoneNumber.substr(0, 4) + '-' + phoneNumber.substr(4);
					} else if (phoneNumber.length == 9) {
						res =
							phoneNumber.substr(0, 3) +
							'-' +
							phoneNumber.substr(3, 3) +
							'-' +
							phoneNumber.substr(6);
					} else if (phoneNumber.length == 10) {
						res =
							phoneNumber.substr(0, 3) +
							'-' +
							phoneNumber.substr(3, 3) +
							'-' +
							phoneNumber.substr(6);
					} else if (phoneNumber.length > 10) {
						//010-1234-5678
						res =
							phoneNumber.substr(0, 3) +
							'-' +
							phoneNumber.substr(3, 4) +
							'-' +
							phoneNumber.substr(7);
					}
				}
			}

			return res;
		},
	},
};
</script>

<style></style>
