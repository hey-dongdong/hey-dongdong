module.exports = {
    devServer: {
		overlay: false,
		proxy: {
			'/user': {
				target: process.env.VUE_APP_API_URL,
				changeOrigin: true,
			},
			'/menu': {
				target: process.env.VUE_APP_API_URL,
				changeOrigin: true,
			},
			'/my-menu': {
				target: process.env.VUE_APP_API_URL,
				changeOrigin: true,
			},
			'/history': {
				target: process.env.VUE_APP_API_URL,
				changeOrigin: true,
			},
			'/order': {
				target: process.env.VUE_APP_API_URL,
				changeOrigin: true,
			},
		},
	},

    
};
