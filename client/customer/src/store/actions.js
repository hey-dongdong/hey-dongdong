import { fetchMenus } from '../api/index';

export default {
	async FETCH_MENUS({ commit }) {
		const response = await fetchMenus();
		commit('SET_MENUS', response.data.payload.menus);
		return response;
	},
};
