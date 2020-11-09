import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/main',
		},
		{
			path: '/main',
			name: 'main',
			component: () => import('@/views/MainPage.vue'),
		},
		{
			path: '/history',
			name: 'history',
			component: () => import('@/views/HistoryPage.vue'),
		},
		{
			path: '/no-show',
			name: 'no-show',
			component: () => import('@/views/NoShowPage.vue'),
		},
	],
});
