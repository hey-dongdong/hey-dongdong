import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		menus: [],
		history: [],
	},
	getters: {
		menuItems(state) {
			return state.menus;
		},
		historyItems(state) {
			return state.history;
		},
	},
	mutations,
	actions,
});
