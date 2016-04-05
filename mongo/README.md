POST http://doevida-grupoles.rhcloud.com/addUser
	login,
	pass,
	name,
	state,
	city,
	birthDate,
	gender,
	bloodType,
	lastDonation,
	canDonate;
	
POST http://doevida-grupoles.rhcloud.com/checkLogin
	login,
	pass;
	
POST http://doevida-grupoles.rhcloud.com/getUser
	login,
	pass;