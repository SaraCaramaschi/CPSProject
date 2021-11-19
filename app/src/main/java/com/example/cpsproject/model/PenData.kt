package com.example.cpsproject.model

data class PenData(
    var acc_x: Double ?= null,
    var acc_y: Double ?= null,
    var acc_z: Double ?= null,
    var gyr_x: Double ?= null,
    var gyr_y: Double ?= null,
    var gyr_z: Double ?= null,
    var force: Double ?= null
)
