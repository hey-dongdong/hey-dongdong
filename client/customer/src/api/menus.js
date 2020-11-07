//전체 메뉴, 나만의 메뉴, 히스토리
import { instance, instanceWithAuth } from './index';

async function fetchMenus(data) {
	try {
		return await instance.post('/menu/get-all', data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchMyMenus(data) {
	try {
		return await instanceWithAuth.post('/my-menu/get-all', data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchHistory(data) {
	try {
		return await instanceWithAuth.post('/history/get-all', data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchHistoryDetail(data) {
	try {
		return await instanceWithAuth.post('/history/detail', data);
	} catch (error) {
		console.log(error);
	}
}

async function addMyMenu(data) {
	try {
		return await instanceWithAuth.post('/my-menu/add', data);
	} catch (error) {
		console.log(error);
	}
}

async function removeMyMenu(data) {
	try {
		return await instanceWithAuth.post('/my-menu/remove', data);
	} catch (error) {
		console.log(error);
	}
}

export {
	fetchMenus,
	fetchMyMenus,
	fetchHistory,
	fetchHistoryDetail,
	addMyMenu,
	removeMyMenu,
};
