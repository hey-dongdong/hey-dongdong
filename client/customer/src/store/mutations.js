export default {
	SET_USERID(state, data) {
		state.userId = data;
	},
	CLEAR_USERID(state) {
		state.userId = '';
	},
	SET_TOKEN(state, token) {
		state.token = token;
	},
	CLEAR_TOKEN(state) {
		state.token = '';
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
