module.exports = {
	devServer: {
		// overlay: false,
		proxy: {
			'/user': {
				target: 'http://localhost:8080',
				changeOrigin: true,
			},
		},
	},
};
