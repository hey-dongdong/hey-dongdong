function saveAuthToCookie(value) {
	document.cookie = `auth=${value}`;
}

function saveUserToCookie(value) {
	document.cookie = `user=${value}`;
}

function saveUserNameToCookie(value) {
	document.cookie = `username=${value}`;
}

function getAuthFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)auth\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

function getUserFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)user\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

function getUserNameFromCookie() {
	return document.cookie.replace(/(?:(?:^|.*;\s*)username\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

function deleteCookie(value) {
	document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

export {
	saveAuthToCookie,
	saveUserToCookie,
	saveUserNameToCookie,
	getAuthFromCookie,
	getUserFromCookie,
	getUserNameFromCookie,
	deleteCookie,
};
