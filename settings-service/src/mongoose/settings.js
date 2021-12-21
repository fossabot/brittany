const mongoose = require('mongoose')
const Schema = mongoose.Schema

let SettingsSchema = new Schema({
    id_environment: {
        type: Schema.Types.ObjectId,
        require: true
    },
    created: {
        type: Date,
        require: true
    },
    expires: {
        type: Date,
        require: true
    },
    active: {
        type: Boolean,
        require: true
    },
    data: {
        temperature: {
            max: Number,
            min: Number
        },
        humidity: {
            max: Number,
            min: Number
        },
        light: {
            fromH: Number,
            fromM: Number,
            toH: Number,
            toM: Number
        }
    }
})

module.exports = mongoose.model("Settings", SettingsSchema)
