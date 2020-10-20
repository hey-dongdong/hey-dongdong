import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchAllMenu() {
	return instance.get('40eabb4e-b7a4-4830-8ec7-9d8e7abc3b90');
}

export { registerUser, fetchAllMenu };
