import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchMenus() {
	return instance.get('f27410e0-1892-453b-bfa9-a478269927ea');
}

function fetchHistory() {
	return instance.get('b3bb4712-5e48-4dcd-8daa-9dd55573a898');
}

function fetchOrderDetail() {
	return instance.get('36ca0ef0-200e-4f19-ba7b-262301603d07');
}

export { registerUser, fetchMenus, fetchHistory, fetchOrderDetail };
