import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		orders: {},
		history: {},
	},
	getters: {
		orderItems(state) {
			return state.orders;
		},
		historyItems(state) {
			return state.history;
		},
	},
	mutations,
	actions,
});
