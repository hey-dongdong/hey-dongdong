function validateEmail(email) {
	const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(String(email).toLowerCase());
}

function validatePassword(str) {
	var pattern1 = /[0-9]/;
	var pattern2 = /[a-zA-Z]/;
	var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/;
	if (
		!pattern1.test(str) ||
		!pattern2.test(str) ||
		!pattern3.test(str) ||
		str.length < 8
	) {
		return false;
	} else {
		return true;
	}
}

// function validatePhone(str) {
// 	var phoneno = /^\(?([0-9]{3})\)?[-]?([0-9]{4})[-]?([0-9]{4})$/;
// 	if (phoneno.test(str)) {
// 		return true;
// 	} else {
// 		alert('message');
// 		return false;
// 	}
// }

export { validateEmail, validatePassword };
