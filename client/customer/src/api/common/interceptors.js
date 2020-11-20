import store from '@/store/index';
import jwt_decode from 'jwt-decode';
import { saveAuthToCookie, saveRefreshTokenToCookie, getUserFromCookie, deleteCookie } from '@/utils/cookies';
import { getRefreshToken } from '@/api/auth';

export function setInterceptors(instance) {
	instance.interceptors.request.use(
		async function(config) {
			// Do something before request is sent
			var decoded = jwt_decode(store.state.accessToken);
			let now = new Date();
			let expiry = decoded.exp - Number(now.getTime().toString().substr(0, 10));
			// console.log(expiry);

			// var dec = jwt_decode(store.state.refreshToken);
			// let expi = dec.exp - Number(now.getTime().toString().substr(0, 10));
			// console.log(expi);
			if(expiry < 30) {
				try {
					const userData = {
						header: {
							name: 'RefreshTokensRequest',
							userId: getUserFromCookie(),
						},
						payload: {
							userId: getUserFromCookie(),
							refreshToken: store.state.refreshToken,
						},
					};
					const { data } = await getRefreshToken(userData);
					store.state.accessToken = data.payload.accessToken;
					store.state.refreshToken = data.payload.refreshToken;
					saveAuthToCookie(data.payload.accessToken);
					saveRefreshTokenToCookie(data.payload.refreshToken);
				} catch (error) {
						deleteCookie('auth');
						deleteCookie('refresh_token')
						deleteCookie('user');
						deleteCookie('username');
						store.state.userId = '';
						store.getters.isLogin = false;
				}
				
			}
			config.headers.Authorization = store.state.accessToken;
			return config;
		},
		function(error) {
			// Do something with request error
			return Promise.reject(error);
		},
	);

	// Add a response interceptor
	instance.interceptors.response.use(
		function(response) {
			// Any status code that lie within the range of 2xx cause this function to trigger
			// Do something with response data
			return response;
		},
		function(error) {
			// Any status codes that falls outside the range of 2xx cause this function to trigger
			// Do something with response error	
			return Promise.reject(error);
		},
	);
	return instance;
}
