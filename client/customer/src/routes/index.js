import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/sign-in',
		},
		{
			path: '/sign-in',
			component: () => import('@/views/SignInPage.vue'),
		},
		{
			path: '/sign-up',
			component: () => import('@/views/SignUpPage.vue'),
		},
		{
			path: '/user/no-show',
			component: () => import('@/views/MainPage.vue'),
		},
		{
			path: '/store-info/:storeName',
			component: () => import('@/views/StoreInfoPage.vue'),
		},
		{
			path: '/user/find-info/id',
			component: () => import('@/views/FindIdPage.vue'),
		},
		{
			path: '/user/find-info/id/:id',
			component: () => import('@/views/FindIdSuccessPage.vue'),
		},
		{
			path: '/user/find-info/pw',
			component: () => import('@/views/FindPwPage.vue'),
		},
		{
			path: '/user/find-info/pw/:email',
			component: () => import('@/views/FindPwSuccessPage.vue'),
		},
	],
});
