import axios from 'axios';

const instance = axios.create({
	baseURL: process.env.VUE_APP_API_URL,
});

function registerUser(userData) {
	return instance.post('sign-up', userData);
}

function fetchMenus() {
	return instance.get('4eecd9aa-e601-48b2-a353-dc9f331ba06c');
}

export { registerUser, fetchMenus };
