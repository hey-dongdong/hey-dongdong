import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		username: '',
		menus: [],
		myMenus: [],
		history: [],
		orderDetail: {},
	},
	getters: {
		menuItems(state) {
			return state.menus;
		},
		myMenuItems(state) {
			return state.myMenus;
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
