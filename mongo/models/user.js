module.exports = function(mongodb, app, userCollection) {

	app.post("/addUser", function (req, res){
		var login = req.body.login;
		var pass = req.body.pass;
		var name = req.body.name;
		var state = req.body.state;
		var city = req.body.city;
		var birthDate= req.body.birthDate;
		var gender = req.body.gender;
		var bloodType = req.body.bloodType;
		var lastDonation = req.body.lastDonation;
		var canDonate = req.body.canDonate;
		
		var inconsistency = checkInconsistency(login, pass, name, state, city, birthDate);

		if (inconsistency) {
			res.send(inconsistency);
		} else {
			userCollection.find({
				login: login,
				pass: pass
				
			}).toArray(function(err, array){
				if (array.length > 0) {
					res.json({ "ok" : 0, "msg" : "Usuário já cadastrado" });
				} else {
					
					userCollection.insert({
						login: login,
						pass: pass,
						name: name,
						state: state,
						city: city,
						birthDate: birthDate,
						gender: gender,
						bloodType: bloodType,
						lastDonation: lastDonation,
						canDonate: canDonate
						
					}, function (err, doc) {
						if(err){
							res.json({ "ok" : 0, "msg" : err });
						} else {
							res.json({"ok": 1});	
						}
					});
				}
			});
		}
	});
	
	app.post("/checkLogin", function(req, res) {
		var login = req.body.login;
		var pass = req.body.pass;
		
		var inconsistency = checkInconsistencyLogin(login, pass);
		
		if (inconsistency) {
			res.send(inconsistency);
		} else {
			userCollection.find({
				login: login,
				pass: pass
				
			}).toArray(function(err, array){
				if(err) {
					res.json({ "ok" : 0, "msg" : err });
				} else {					
					if(array.length < 1) {						
						res.json({ "ok" : 0, "msg" : "Usuário não é cadastrado" });
					} else {
						res.json({ "ok" : 1, "msg" : "Usuário é cadastrado" });
					}
				}
				
			});
		}
		
	});
	
	app.post("/getUser", function(req, res) {
		var login = req.body.login;
		var pass = req.body.pass;
		
		var inconsistency = checkInconsistencyLogin(login, pass);
		
		if (inconsistency) {
			res.send(inconsistency);
		} else {
			userCollection.findOne({
				login: login,
				pass: pass
				
			}, function(err, doc){
				if(err){
					res.json({ "ok" : 0, "msg" : err });
				} else {
					res.json(doc);	
				}
			});
		}
		
	});
	
	
	function checkInconsistencyLogin(login, pass) {
		if (!login) {
			return '{ "ok" : 0, "msg" : "Login do usuário não informado!" }';
		}
		
		if (!pass) {
			return '{ "ok" : 0, "msg" : "Senha não informada!" }';	
		}
	}
	
	function checkInconsistency(login, pass, name, state, city, birthDate) {
		if (!login) {
			return '{ "ok" : 0, "msg" : "Login do usuário não informado!" }';
		}
		
		if (!pass) {
			return '{ "ok" : 0, "msg" : "Senha não informada!" }';			
		}

		if (pass.length < 6) {
			return '{ "ok" : 0, "msg" : "A senha deve conter pelo menos 6 caracteres!" }';
		} 
		
		if (!name) {
			return '{ "ok" : 0, "msg" : "Nome do usuário não informado!" }';
		}
		
		if (!state) {
			return '{ "ok" : 0, "msg" : "Estado não infomado!" }';
		}
			
		if (!city) {
			return '{ "ok" : 0, "msg" : "Cidade não infomada!" }';
		}
			
		if (!birthDate) {
			return '{ "ok" : 0, "msg" : "Data de nascimento não informada!" }';
		}
		return;
	}

	return this;
}