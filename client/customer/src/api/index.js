import axios from 'axios';
import { setInterceptors } from './common/interceptors';

function createInstance() {
	const instance = axios.create({
		baseURL: 'http://13.209.112.163:8080/',
		headers: {
			'Access-Control-Allow-Origin': 'http://13.209.112.163:8080'
		}
	});
	return instance;
}
export const instance = createInstance();

function createInstanceWithAuth() {
	const instance = axios.create({
		baseURL: 'http://13.209.112.163:8080/',
		headers: {
			'Access-Control-Allow-Origin': 'http://13.209.112.163:8080'
		}
	});
	return setInterceptors(instance);
}
export const instanceWithAuth = createInstanceWithAuth();
