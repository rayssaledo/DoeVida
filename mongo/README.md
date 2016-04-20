POST http://doevida-grupoles.rhcloud.com/addUser (login, pass, name, state, city, birthDate, gender, bloodType,	lastDonation, canDonate) -- cadastra um usuário
	
POST http://doevida-grupoles.rhcloud.com/checkLogin (login, pass) -- verifica se o usuário cujo login foi passado como parâmetro foi cadastrado

POST http://doevida-grupoles.rhcloud.com/updateLastDonation (login, lastDonation) -- atualiza a data da última doação 

POST http://doevida-grupoles.rhcloud.com/addForm (login, patientName, hospitalName, city, state, bloodType, deadline) -- adiciona um formulario ao usuário informado
	
GET http://doevida-grupoles.rhcloud.com/getUser (login) -- retorna o usuario cujo login foi passado como parâmetro

GET http://doevida-grupoles.rhcloud.com/getAllUsers -- retorna todos os usuários cadastrados

GET http://doevida-grupoles.rhcloud.com/getUsersByBloodType (bloodType) -- recupera todos os usuários pelo tipo de sangue informado

