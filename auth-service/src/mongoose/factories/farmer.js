const Farmer = require("../farmer")

function createFarmer(name, surname, birthdate, mail, password, id_role, id_organization) {
    return new Farmer({
        name: name,
        surname: surname,
        birthdate: birthdate,
        mail: mail,
        password: password,
        id_role: id_role,
        id_organization: id_organization
    })
}

module.exports = {createFarmer}
