import { fetchMenus, fetchMyMenus, fetchHistory, fetchOrderDetail } from '../api/index';

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
	async FETCH_HISTORY({ commit }) {
		const response = await fetchHistory();
		commit('SET_HISTORY', response.data.payload.orders);
		return response;
	},
	async FETCH_ORDER_DETAIL({ commit }) {
		const response = await fetchOrderDetail();
		commit('SET_ORDER_DETAIL', response.data.payload);
		return response;
	},
};
