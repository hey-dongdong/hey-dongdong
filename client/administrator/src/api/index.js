import axios from 'axios';

function createInstance() {
	const instance = axios.create();
	return instance;
}
const instance = createInstance();

async function fetchHistoryMenus(id, data) {
	try {
		return await instance.post(`/admin/history/${id}`, data);
	} catch (error) {
		console.log(error);
	}
}

export { fetchHistoryMenus };
