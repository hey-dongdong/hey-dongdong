import { fetchMenus, fetchMyMenus, fetchHistory, fetchHistoryDetail } from '../api/index';

export default {
	async FETCH_MENUS({ commit }) {
		const response = await fetchMenus();
		commit('SET_MENUS', response.data.payload.menus);
		return response;
	},
	async FETCH_MY_MENUS({ commit }) {
		const response = await fetchMyMenus();
		commit('SET_MY_MENUS', response.data.payload.menus);
		return response;
	},
	async FETCH_HISTORY({ commit }, data) {
		const response = await fetchHistory(data);
		commit('SET_HISTORY', response.data.payload.orders);
		return response;
	},
	async FETCH_HISTORY_DETAIL({ commit }, data) {
		const response = await fetchHistoryDetail(data);
		commit('SET_HISTORY_DETAIL', response.data.payload);
		return response;
	},
};
