import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		menus: [],
		history: [],
		orderDetail: {},
	},
	getters: {
		menuItems(state) {
			return state.menus;
		},
		historyItems(state) {
			return state.history;
		},
		orderDetail(state) {
			return state.orderDetail;
		},
	},
	mutations,
	actions,
});
