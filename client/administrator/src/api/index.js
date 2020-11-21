import axios from 'axios';

function createInstance() {
	const instance = axios.create({
		baseURL: 'http://13.209.112.163:8080/',
		headers: {
			'Access-Control-Allow-Origin': 'http://13.209.112.163:8080'
		}
	});
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

async function updateOrderProgress(data) {
	try {
		return await instance.post('/order/update-progress', data);
	} catch (error) {
		console.log(error);
	}
}

export { fetchOrders, fetchHistoryOrders, updateOrderProgress };
