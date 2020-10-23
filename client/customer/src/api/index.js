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
	return instance.get('b3bb4712-5e48-4dcd-8daa-9dd55573a898');
}

function fetchOrderDetail() {
	return instance.get('36ca0ef0-200e-4f19-ba7b-262301603d07');
}

export { registerUser, fetchMenus, fetchMyMenus, fetchHistory, fetchOrderDetail };
