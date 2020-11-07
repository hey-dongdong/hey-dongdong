//로그인, 회원 가입, 아이디 찾기, 비밀번호 찾기
import { instance } from './index';

async function registerUser(userData) {
	try {
		return await instance.post('/user/sign-up', userData);
	} catch (error) {
		console.log(error);
	}
}

async function signInUser(userData) {
	try {
		return await instance.post('/user/sign-in', userData);
	} catch (error) {
		console.log(error);
	}
}

export { registerUser, signInUser };
