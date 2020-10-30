<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">전체 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuListHeader></MenuListHeader>
			<div class="menu-card-list">
				<MenuListItem
					v-for="item in menuItems"
					:key="item.menuId"
					:menuItem="item"
				></MenuListItem>
			</div>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuListHeader from '@/components/menu/MenuListHeader.vue';
import MenuListItem from '@/components/menu/MenuListItem.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
		MenuListHeader,
		MenuListItem,
	},
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		this.$store.dispatch('FETCH_MENUS');
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/menu/detail');
		},
	},
};
</script>

<style></style>
