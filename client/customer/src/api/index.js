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

async function fetchMyMenus(data) {
	try {
		return await instance.post('/my-menu/get-all', data);
	} catch (error) {
		console.log(error);
	}
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

async function addMyMenu(data) {
	try {
		return await instance.post('/my-menu/add', data);
	} catch (error) {
		console.log(error);
	}
}

async function removeMyMenu(data) {
	try {
		return await instance.post('/my-menu/remove', data);
	} catch (error) {
		console.log(error);
	}
}

async function addOrder(data) {
	try {
		return await instance.post('/order/add', data);
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
	addMyMenu,
	removeMyMenu,
	addOrder,
};
