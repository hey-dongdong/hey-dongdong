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
			component: () => import('@/views/login/SignInPage.vue'),
		},
		{
			path: '/sign-up',
			component: () => import('@/views/login/SignUpPage.vue'),
		},
		{
			path: '/main',
			component: () => import('@/views/main/MainPage.vue'),
		},
		{
			path: '/store-info/:storeName',
			component: () => import('@/views/main/StoreInfoPage.vue'),
		},
		{
			path: '/user/find-info/id',
			component: () => import('@/views/login/FindIdPage.vue'),
		},
		{
			path: '/user/find-info/id/:id',
			component: () => import('@/views/login/FindIdSuccessPage.vue'),
		},
		{
			path: '/user/find-info/id-fail',
			component: () => import('@/views/login/FindIdFailPage.vue'),
		},
		{
			path: '/user/find-info/pw',
			component: () => import('@/views/login/FindPwPage.vue'),
		},
		{
			path: '/user/find-info/pw/:email',
			component: () => import('@/views/login/FindPwSuccessPage.vue'),
		},
		{
			path: '/user/find-info/pw-fail',
			component: () => import('@/views/login/FindPwFailPage.vue'),
		},
		{
			path: '/menu/all',
			name: 'menu-all',
			component: () => import('@/views/menu/AllMenuPage.vue'),
		},
		{
			path: '/menu/coffee',
			name: 'menu-coffee',
			component: () => import('@/views/menu/CoffeeMenuPage.vue'),
		},
		{
			path: '/menu/tea',
			name: 'menu-tea',
			component: () => import('@/views/menu/TeaMenuPage.vue'),
		},
		{
			path: '/menu/ade',
			name: 'menu-ade',
			component: () => import('@/views/menu/AdeMenuPage.vue'),
		},
		{
			path: '/menu/others',
			name: 'menu-others',
			component: () => import('@/views/menu/OthersMenuPage.vue'),
		},
		{
			path: '/menu/:id',
			component: () => import('@/views/menu/MenuDetailPage.vue'),
		},
	],
});
