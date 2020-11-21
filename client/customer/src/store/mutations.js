export default {
	SET_USERID(state, data) {
		state.userId = data;
	},
	CLEAR_USERID(state) {
		state.userId = '';
	},
	SET_ACCESS_TOKEN(state, token) {
		state.accessToken = token;
	},
	SET_REFRESH_TOKEN(state, token) {
		state.refreshToken = token;
	},
	CLEAR_ACCESS_TOKEN(state) {
		state.accessToken = '';
	},
	CLEAR_REFRESH_TOKEN(state) {
		state.refreshToken = '';
	},
	SET_MENUS(state, data) {
		state.menus = data;
	},
	SET_MY_MENUS(state, data) {
		state.myMenus = data;
	},
	SET_HISTORY(state, data) {
		state.history = data;
	},
	SET_HISTORY_DETAIL(state, data) {
		state.historyDetail = data;
	},
};
