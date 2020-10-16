import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
	mutations: {
		setId(state, id) {
			state.id = id;
		},
		clearId(state) {
			state.id = '';
		},
		setToken(state, token) {
			state.token = token;
		},
		clearToken(state) {
			state.token = '';
		},
	},
});
