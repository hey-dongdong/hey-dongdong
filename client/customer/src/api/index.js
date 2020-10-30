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
	return instance.get('ded297bb-bb09-4846-9cfc-66b606379edb');
}

export { registerUser, fetchMenus, fetchMyMenus, fetchHistory, fetchOrderDetail };
