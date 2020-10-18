<template>
	<div class="find-container">
		<slot name="fail-explain"></slot>
		<span class="no-info">해당 회원 정보가 없습니다.</span>
		<router-link to="/sign-up" class="goldbtn">회원가입</router-link>
	</div>
</template>

<script>
export default {};
</script>

<style></style>
