import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchMenus() {
	return instance.get('62f57cc6-f16f-411d-b799-c6b926ac053e');
}

function fetchMyMenus() {
	return instance.get('4cb512b3-9a01-4212-a40c-6293f3f374a1');
}

function fetchHistory() {
	return instance.get('b3bb4712-5e48-4dcd-8daa-9dd55573a898');
}

function fetchOrderDetail() {
	return instance.get('36ca0ef0-200e-4f19-ba7b-262301603d07');
}

export { registerUser, fetchMenus, fetchMyMenus, fetchHistory, fetchOrderDetail };
