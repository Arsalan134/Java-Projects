function validate4() {
	var fullName = document.getElementById("fullname").value.trim();
	var bday = document.getElementById('bday').value.trim();
	var userName = document.getElementById('uname').value.trim();
	var password = document.getElementById('pass1').value;
	var password2 = document.getElementById('pass2').value;
	var sex;
	if (document.getElementById('male').checked) {
		sex = document.getElementById('male').value;
	} else if (document.getElementById('female').checked) {
		sex = document.getElementById('female').value;
	}

	var major = document.getElementById('major').value;

	if (validateFullName(fullName) && validateBDay(bday)
			&& validateUserName(userName)
			&& validatePassword(password, password2) && validateGender(sex)
			&& validateMajor(major)) {

		fullName = removeSpacesMiddle(fullName);

		alert("Fullname :\t " + fullName + "\n" + "BDay : \t\t " + bday + "\n"
				+ "Username : \t " + userName + "\n" + "Password : \t "
				+ password + "\n" + "Sex : \t\t " + sex + "\n"
				+ "Major : \t\t " + major + "\n" + "Age : \t\t " + age(bday));
	}
}

function removeSpacesMiddle(fullName) {
	var name, surname;
	name = fullName.substring(0, fullName.indexOf(" "));
	surname = fullName.substring(fullName.lastIndexOf(" "));
	return name + " " + surname;
}

function validateFullName(fullName) {

	if (contains(fullName, " ")) {
		fullName.substring(0, fullName.indexOf(" "));
		fullName.substring(fullName.indexOf(" ") + 1);
	} else {
		alert("Please enter Name and Surname");
		return false;
	}

	for (var i = 0; i < fullName.length; i++)
		if (!((fullName.charCodeAt(i) >= 65 && fullName.charCodeAt(i) <= 90)
				|| (fullName.charCodeAt(i) >= 97 && fullName.charCodeAt(i) <= 122) || fullName
				.charCodeAt(i) == 32)) {
			alert("Illegal character");
			return false;
		}
	return true;
}

function validateBDay(bday) {
	if (bday.length == 10)
		if (bday.charAt(2) == '-' && bday.charAt(5) == '-') {
			var sDay = bday.substring(0, 2);
			var sMonth = bday.substring(3, 5);
			var sYear = bday.substring(6);
			if (isNumber(sDay) && isNumber(sMonth) && isNumber(sYear)) {
				var day = Number(sDay);
				var month = Number(sMonth);
				var year = Number(sYear);

				if (validateDayMonthRelationship(day, month))
					if (day >= 1 && day <= 31 && month >= 1 && month <= 12
							&& year >= 1800
							&& validatePastDate(year + "/" + month + "/" + day)) {
						return true;
					} else
						alert("Invalid date range");
			} else
				alert("Please enter valid numbers");
		} else
			alert("Please enter birth date in DD-MM-YYYY format !");
	else
		alert("Please enter birth date in DD-MM-YYYY format !");
	return false;
}

function validatePastDate(dateString) {
	var today = new Date();
	var birthDate = new Date(dateString);
	var age = today.getFullYear() - birthDate.getFullYear();
	var m = today.getMonth() - birthDate.getMonth();
	if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate()))
		age--;
	return age >= 0;
}

function validateDayMonthRelationship(day, month) {
	if (!(month == 2 && day >= 30))
		switch (month) {
		case 2: // February
		case 4: // March
		case 6: // May
		case 9: // September
		case 11:// November
			if (day > 30) {
				alert("In " + month + " month there are only 30 days");
				return false;
			}
		}
	else {
		alert("In February maximum 29 days");
		return false;
	}
	return true;
}

function validateUserName(email) {
	var numberOfAt = 0;
	var meetDot = false;
	if (contains(email, "@")) {
		for (var i = 0; i < email.length; i++) {
			if (email.charAt(i) == '@')
				numberOfAt++;
			if (email.charAt(i) == '.' && !meetDot)
				meetDot = true;
			else if (email.charAt(i) != '.')
				meetDot = false;
			else if (email.charAt(i) == '.' && meetDot)
				return false;
		}
	} else {
		alert("Username doesn't contain \"@\" ");
		return false;
	}

	if (numberOfAt > 1) {
		alert("Use only one \"@\" ");
		return false;
	}

	if (!contains(email, ".")) {
		alert("Username doesn't contain \".\" ");
		return false;
	}

	var id, domainName, topLevelDomain;

	try {
		id = email.substring(0, email.indexOf("@"));
		domainName = email.substring(email.indexOf("@") + 1, email
				.lastIndexOf("."));
		topLevelDomain = email.substring(email.lastIndexOf(".") + 1);
	} catch (e) {
		alert("Illegal username");
		return false;
	}

	if (id.length >= 3) {
		if (domainName.length >= 3) {
			if (topLevelDomain.length >= 2) {
				if (containsLegalCharForMail(id)
						&& containsLegalCharForMail(domainName))
					return true;
			} else
				alert("Tol Level Domain is too short");
		} else
			alert("Domain is too short");
	} else
		alert("ID is too short");

	return false;
}

function contains(string, char) {
	for (var i = 0; i < string.length; i++)
		if (string.charAt(i) == char)
			return true;
	return false;
}

// 46 .
// 48 57 0-9
// 65 90 A-Z
// 95 _
// 97 122 a-z

function containsLegalCharForMail(e) {
	for (var i = 0; i < e.length; i++)
		if (!((e.charCodeAt(i) >= 48 && e.charCodeAt(i) <= 57)
				|| (e.charCodeAt(i) >= 65 && e.charCodeAt(i) >= 90)
				|| (e.charCodeAt(i) >= 97 && e.charCodeAt(i) <= 122)
				|| e.charCodeAt(i) != 46 || e.charCodeAt(i) != 95)) {
			alert("Illegal char in mail");
			return false;
		}
	return true;
}

function validatePassword(password, password2) {
	if (password.length >= 8) {
		if (password == password2) {
			if (containNonLetterSymbol(password)
					|| containUpperCaseLetter(password))
				return true;
		} else {
			alert("Confirm Password doesn't match");
			return false;
		}

	} else {
		alert("Password is too short");
		return false;
	}
	return false;
}

function containNonLetterSymbol(password) {
	for (var i = 0; i < password.length; i++)
		if ((password.charCodeAt(i) < 65 && password.charCodeAt(i) > 90)
				|| (password.charCodeAt(i) < 97 && password.charCodeAt(i) > 122)
				|| (password.charAt(i) < '0' && password.charAt(i) > '9'))
			return true;
	return false;
}

function containUpperCaseLetter(password) {
	for (var i = 0; i < password.length; i++)
		if (password.charCodeAt(i) >= 65 && password.charCodeAt(i) <= 90)
			return true;
	return false;
}

function validateGender(sex) {
	if (sex == undefined) {
		alert("You didn't choose sex");
		return false;
	} else
		return true;
}

function validateMajor(major) {
	if (major == "null") {
		alert("Please choose a major");
		return false;
	}
	return true;
}

function age(bday) {
	// Split String to components
	var sDay = bday.substring(0, 2);
	var sMonth = bday.substring(3, 5);
	var sYear = bday.substring(6);

	// Cast to int
	var day = Number(sDay);
	var month = Number(sMonth);
	var year = Number(sYear);

	var today = new Date();
	var birthDate = new Date(year + "/" + month + "/" + day);

	var age = today.getFullYear() - birthDate.getFullYear();
	var m = today.getMonth() - birthDate.getMonth();

	if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate()))
		age--;
	return age;
}

function showPassword(password) {
	return password.substring(0, 3) + printStart(password.substring(3).length);
}

function printStart(i) {
	var s = "";
	for (var j = 0; j < i; j++)
		s += "*";
	return s;
}

function isNumber(s) {
	for (var i = 0; i < s.length; i++)
		if (!(s.charCodeAt(i) >= 48 && s.charCodeAt(i) <= 57))
			return false;
	return true;
}
