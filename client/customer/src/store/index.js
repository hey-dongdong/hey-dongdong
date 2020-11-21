import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';
import { getAuthFromCookie, getRefreshTokenFromCookie, getUserFromCookie } from '@/utils/cookies';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		userId: getUserFromCookie() || '',
		accessToken: getAuthFromCookie() || '',
		refreshToken: getRefreshTokenFromCookie() || '',
		menus: [],
		myMenus: [],
		history: [],
		historyDetail: {},
	},
	getters: {
		isLogin(state) {
			return state.userId !== '';
		},
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
