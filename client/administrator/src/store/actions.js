import { fetchOrders, fetchHistoryOrders } from '@/api/index';

export default {
	async FETCH_ORDERS({ commit }, { id, data }) {
		const response = await fetchOrders(id, data);
		commit('SET_ORDERS', response.data.payload);
		return response;
	},
	async FETCH_HISTORY_ORDERS({ commit }, { id, data }) {
		const response = await fetchHistoryOrders(id, data);
		commit('SET_HISTORY_ORDERS', response.data.payload);
		return response;
	},
};
