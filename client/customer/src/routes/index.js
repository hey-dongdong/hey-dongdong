import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/index';

Vue.use(VueRouter);

const router = new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/main',
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
			path: '/sign-out',
			name: 'sign-out',
			component: () => import('@/views/login/SignOutPage.vue'),
			meta: { auth: true },
		},
		{
			path: '/main',
			component: () => import('@/views/main/MainPage.vue'),
			meta: { auth: true },
		},
		{
			path: '/store-info',
			component: () => import('@/views/main/StoreInfoPage.vue'),
		},
		{
			path: '/find-id',
			component: () => import('@/views/login/FindIdPage.vue'),
		},
		{
			path: '/find-id/success',
			name: 'find-id/success',
			component: () => import('@/views/login/FindIdSuccessPage.vue'),
		},
		{
			path: '/find-id/fail',
			component: () => import('@/views/login/FindIdFailPage.vue'),
		},
		{
			path: '/find-pw',
			component: () => import('@/views/login/FindPwPage.vue'),
		},
		{
			path: '/find-pw/success',
			name: 'find-pw/success',
			component: () => import('@/views/login/FindPwSuccessPage.vue'),
		},
		{
			path: '/find-pw/fail',
			component: () => import('@/views/login/FindPwFailPage.vue'),
		},
		{
			path: '/change-pw',
			component: () => import('@/views/login/ChangePwPage.vue'),
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
			name: 'my-menu',
			component: () => import('@/views/menu/MyMenuPage.vue'),
			meta: { auth: true },
		},
		{
			path: '/my-menu/detail',
			name: 'my-menu-detail',
			component: () => import('@/views/menu/MyMenuDetailPage.vue'),
			meta: { auth: true },
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
			meta: { auth: true },
		},
		{
			path: '/cart',
			name: 'cart',
			component: () => import('@/views/order/CartPage.vue'),
			meta: { auth: true },
		},
		{
			path: '/complete',
			name: 'complete',
			component: () => import('@/views/order/CompleteOrderPage.vue'),
			meta: { auth: true },
		},
	],
});

router.beforeEach((to, from, next) => {
	if (to.meta.auth && !store.getters.isLogin) {
		alert('로그인 해주세요');
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
		next('/menu/all');
		return;
	}
	if (from.path == '/find-pw' && to.path == '/find-id/success') {
		next('/sign-in');
		return;
	}
	if (from.path == '/cart' && to.path == '/my-menu/detail') {
		next('/my-menu');
		return;
	}
	if (from.path == '/cart' && to.path == '/history/detail') {
		next('/history');
		return;
	}
	next();
});

export default router;
