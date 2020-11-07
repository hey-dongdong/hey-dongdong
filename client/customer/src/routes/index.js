import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/index';

Vue.use(VueRouter);

const router = new VueRouter({
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
			path: '/logout',
			name: 'logout',
			component: () => import('@/views/login/LogoutPage.vue'),
		},
		{
			path: '/main',
			component: () => import('@/views/main/MainPage.vue'),
		},
		{
			path: '/store-info',
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
			path: '/menu/detail',
			name: 'menu-detail',
			component: () => import('@/views/menu/MenuDetailPage.vue'),
		},
		{
			path: '/my-menu',
			component: () => import('@/views/menu/MyMenuPage.vue'),
		},
		{
			path: '/my-menu/detail',
			name: 'my-menu-detail',
			component: () => import('@/views/menu/MyMenuDetailPage.vue'),
		},
		{
			path: '/history',
			name: 'history',
			component: () => import('@/views/order/HistoryPage.vue'),
			meta: { auth: true },
		},
		{
			path: '/history/detail',
			name: 'history-detail',
			component: () => import('@/views/order/HistoryDetailPage.vue'),
		},
		{
			path: '/cart',
			name: 'cart',
			component: () => import('@/views/order/CartPage.vue'),
		},
		{
			path: '/complete',
			name: 'complete',
			component: () => import('@/views/order/CompleteOrderPage.vue'),
		},
	],
});

router.beforeEach((to, from, next) => {
	if (to.meta.auth && !store.getters.isLogin) {
		console.log('인증이 필요합니다');
		next('/sign-in');
		return;
	}
	if (
		!(
			from.name == 'menu-all' ||
			from.name == 'menu-coffee' ||
			from.name == 'menu-tea' ||
			from.name == 'menu-ade' ||
			from.name == 'menu-others'
		) &&
		to.name == 'menu-detail'
	) {
		console.log('hihi');
		next('/menu/all');
		return;
	}
	next();
});

export default router;
