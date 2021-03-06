//로그인, 회원 가입, 아이디 찾기, 비밀번호 찾기
import { instance, instanceWithAuth } from './index';

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

async function signOutUser(userData) {
	try {
		return await instanceWithAuth.post('/user/sign-out', userData);
	} catch (error) {
		console.log(error);
	}
}

async function findUserId(userData) {
	try {
		return await instance.post('/user/find-info/id', userData);
	} catch (error) {
		console.log(error);
	}
}

async function findUserPw(userData) {
	try {
		return await instance.post('/user/find-info/pw', userData);
	} catch (error) {
		console.log(error);
	}
}

async function changeUserPw(userData) {
	try {
		return await instanceWithAuth.post('/user/change-pw', userData);
	} catch (error) {
		console.log(error);
	}
}

async function checkNoShowCount(userData) {
	try {
		return await instanceWithAuth.post('/user/no-show-count', userData);
	} catch (error) {
		console.log(error);
	}
}

async function getRefreshToken(userData) {
	try {
		return await instance.post('/user/refresh-tokens', userData);
	} catch (error) {
		console.log(error);
	}
}

export {
	registerUser,
	signInUser,
	signOutUser,
	findUserId,
	findUserPw,
	changeUserPw,
	checkNoShowCount,
	getRefreshToken,
};
