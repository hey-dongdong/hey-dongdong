import { fetchMenus, fetchHistory } from '../api/index';

export default {
	async FETCH_MENUS({ commit }) {
		const response = await fetchMenus();
		commit('SET_MENUS', response.data.payload.menus);
		return response;
	},
	async FETCH_HISTORY({ commit }) {
		const response = await fetchHistory();
		commit('SET_HISTORY', response.data.payload.orders);
		console.log(response.data);
		return response;
	},
};
