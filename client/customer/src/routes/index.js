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
	],
});
