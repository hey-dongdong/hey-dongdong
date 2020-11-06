import axios from 'axios';
import { setInterceptors } from './common/interceptors';

function createInstance() {
	const instance = axios.create();
	return setInterceptors(instance);
}
const instance = createInstance();

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

async function fetchMenus(data) {
	try {
		return await instance.post('/menu/get-all', data);
	} catch (error) {
		console.log(error);
	}
}

function fetchMyMenus() {
	return instance.get('bc12c78e-aa13-457e-a1d1-7f3ce671556d');
}

async function fetchHistory(data) {
	try {
		return await instance.post('/history/get-all', data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchHistoryDetail(data) {
	try {
		return await instance.post('/history/detail', data);
	} catch (error) {
		console.log(error);
	}
}

export {
	registerUser,
	signInUser,
	fetchMenus,
	fetchMyMenus,
	fetchHistory,
	fetchHistoryDetail,
};
