//주문
import { instanceWithAuth } from './index';

async function addOrder(data) {
	try {
		return await instanceWithAuth.post('/order/add', data);
	} catch (error) {
		console.log(error);
	}
}

async function getProgress(data) {
	try {
		return await instanceWithAuth.post('/order/get-progress', data);
	} catch (error) {
		console.log(error);
	}
}

export { addOrder, getProgress };
