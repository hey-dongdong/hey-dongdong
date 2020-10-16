import axios from 'axios';

function registerUser(userData) {
	const url = 'http://localhost:3000/sign-up';
	return axios.post(url, userData);
}

export { registerUser };
