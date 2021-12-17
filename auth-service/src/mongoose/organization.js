const mongoose = require('mongoose')
const Schema = mongoose.Schema

let OrganizationSchema = new Schema({
    name: {
        type: String,
        require: true,
        unique: true
    },
    greenhouses: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Greenhouse'
    }]
})

module.exports = mongoose.model("Organization", OrganizationSchema)
