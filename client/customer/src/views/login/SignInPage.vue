<template>
	<div class="greenbg">
		<img src="../../assets/logo.png" alt="logo" class="logo" />
		<form @submit.prevent="submitForm" class="form">
			<ul class="form-items">
				<li>
					<label for="id" class="form-label">ID</label>
					<input id="id" type="text" placeholder="아이디를 입력하세요" />
				</li>
				<li>
					<label for="password" class="form-label">Password</label>
					<input
						id="password"
						type="password"
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
	</div>
</template>

<script>
export default {
	data() {
		return {
			id: '',
			password: '',
		};
	},
	methods: {
		// async submitForm() {
		// 	try {
		// 		const userData = {
		// 			id: this.id,
		// 			password: this.password,
		// 		};
		// 		//await this.$store.dispatch('LOGIN', userData);
		// 		this.$router.push('/user/no-show');
		// 	} catch (error) {
		// 		console.log(error.response.data);
		// 	}
		// },
	},
};
</script>

<style></style>
