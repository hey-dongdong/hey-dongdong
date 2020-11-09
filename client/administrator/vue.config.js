module.exports = {
	devServer: {
		overlay: false,
		proxy: {
			'/admin': {
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
