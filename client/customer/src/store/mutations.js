export default {
	SET_USERNAME(state, data) {
		state.username = data;
	},
	CLEAR_USERNAME(state) {
		state.username = '';
	},
	SET_TOKEN(state, token) {
		state.token = token;
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
	SET_ORDER_DETAIL(state, data) {
		state.orderDetail = data;
	},
};
