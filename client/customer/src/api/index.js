import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchMenus() {
	return instance.get('c37308d5-3ecf-475a-98eb-bfe8a027397c');
}

export { registerUser, fetchMenus };
