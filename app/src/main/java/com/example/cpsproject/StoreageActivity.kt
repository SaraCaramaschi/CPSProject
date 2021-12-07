package com.google.firebase.referencecode.storage.kotlin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsproject.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ktx.Firebase

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference

import com.google.firebase.storage.ktx.storage

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

abstract class StorageActivity : AppCompatActivity() {

    // [START storage_field_declaration]
    lateinit var storage: FirebaseStorage
    // [END storage_field_declaration]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        // [START storage_field_initialization]
        storage = Firebase.storage
        // [END storage_field_initialization]

        includesForCreateReference()
    }

    private fun includesForCreateReference() {
        val storage = Firebase.storage

        // ## Create a Reference

        // [START create_storage_reference]
        // Create a storage reference from our app
        var storageRef = storage.reference
        // [END create_storage_reference]

        // [START create_child_reference]
        // Create a child reference
        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("images")
    }
}

