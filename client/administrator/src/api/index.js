import axios from 'axios';

function createInstance() {
	const instance = axios.create();
	return instance;
}
const instance = createInstance();

async function fetchOrders(id, data) {
	try {
		return await instance.put(`/admin/orders/${id}`, data);
	} catch (error) {
		console.log(error);
	}
}

async function fetchHistoryOrders(id, data) {
	try {
		return await instance.put(`/admin/history/${id}`, data);
	} catch (error) {
		console.log(error);
	}
}

async function updateOrderProgress(data) {
	try {
		return await instance.put('/order/update-progress', data);
	} catch (error) {
		console.log(error);
	}
}

export { fetchOrders, fetchHistoryOrders, updateOrderProgress };
