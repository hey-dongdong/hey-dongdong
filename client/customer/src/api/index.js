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

function fetchHistory() {
	return instance.get('618e1731-89b1-45af-bb6e-fd611b5531d7');
}

export { registerUser, fetchMenus, fetchHistory };
