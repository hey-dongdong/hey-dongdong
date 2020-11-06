import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		userId: '',
		token: '',
		menus: [],
		myMenus: [],
		history: [],
		historyDetail: {},
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
		historyDetail(state) {
			return state.historyDetail;
		},
	},
	mutations,
	actions,
});
