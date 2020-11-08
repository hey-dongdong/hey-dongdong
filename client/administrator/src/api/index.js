import axios from 'axios';

function createInstance() {
	const instance = axios.create();
	return instance;
}
const instance = createInstance();

async function fetchOrders(id, data) {
	try {
		return await instance.post(`/admin/orders/${id}`, data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchHistoryOrders(id, data) {
	try {
		return await instance.post(`/admin/history/${id}`, data);
	} catch (error) {
		console.log(error);
	}
}

export { fetchOrders, fetchHistoryOrders };
