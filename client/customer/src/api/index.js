import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchMenus() {
	return instance.get('0071f215-d5da-4606-89fd-9957dd901916');
}

function fetchMyMenus() {
	return instance.get('bc12c78e-aa13-457e-a1d1-7f3ce671556d');
}

function fetchHistory() {
	return instance.get('1e81f233-e4c2-47cf-8c65-586d4062a86a');
}

function fetchOrderDetail() {
	return instance.get('57b656c4-9918-4e98-959a-78eb5bb352a0');
}

export { registerUser, fetchMenus, fetchMyMenus, fetchHistory, fetchOrderDetail };
