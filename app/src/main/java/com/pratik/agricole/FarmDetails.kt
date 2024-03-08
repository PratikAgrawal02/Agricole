package com.pratik.agricole

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso

class FarmDetails : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var farmname : TextView
    lateinit var farmsize : TextView
    lateinit var plant_date : TextView
    lateinit var gdd : TextView
    lateinit var farmimage : ImageView
    lateinit var erzz : TextView
    lateinit var setup : TextView
    lateinit var fileUri : Uri

    var farmnumber : String? = null
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_details)
        hook()
        farmnumber = intent.getStringExtra("farmnumber").toString()

        ref = database.reference.child("users").child(auth.uid.toString()).child("farms").child(
            farmnumber!!
        )
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(it: DataSnapshot) {
                farmname.text = it.child("farmname").value.toString()
                farmsize.text = it.child("farmsize").value.toString()
                Picasso.get().load(it.child("farmimage").value.toString()).into(farmimage)
                if (it.child("plantdate").exists()){
                    plant_date.text = it.child("plantdate").value.toString()
                }
                if (it.child("gdd").exists()){
                    gdd.text = it.child("gdd").value.toString()
                }
                if (it.child("erz").exists()){
                    erzz.text = it.child("erz").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        farmimage.setOnClickListener {
            setfarmimage()
        }
        setup.setOnClickListener {
            showCustomAlertDialog()

        }

    }

    private fun setfarmimage() {
        Dexter.withContext(applicationContext)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select jpg Files"),
                        101
                    )
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    Toast.makeText(this@FarmDetails,"Permission denied", Toast.LENGTH_SHORT).show()}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                    Toast.makeText(this@FarmDetails,"Permission taking", Toast.LENGTH_SHORT).show()
                }
            }).check()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            assert(data != null)
            fileUri = data?.data!!
            farmimage.setImageURI(fileUri)
            uploadimage(fileUri)
        }
    }
    private fun uploadimage(fileUri: Uri) {
        val pd = ProgressDialog(this)
        pd.setTitle("Uploading Image")
        pd.show()

        val reference: StorageReference = FirebaseStorage.getInstance().reference.child("${auth.currentUser!!.uid}/${farmnumber}.jpg")

        reference.putFile(fileUri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> reference.downloadUrl.addOnSuccessListener { uri: Uri ->

            ref.child("farmimage").setValue(uri.toString())
            pd.dismiss()
            Toast.makeText(baseContext, "File Uploaded", Toast.LENGTH_SHORT).show()

        }
        }
            .addOnProgressListener { taskSnapshot: UploadTask.TaskSnapshot ->
                val percent =
                    (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
                pd.setMessage("Uploaded :" + percent.toInt() + "%")
            }
    }

    private fun showCustomAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Details")

        // Set up the layout for the dialog
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        // Add EditText fields to the layout
        val editText1 = EditText(this)
        editText1.hint = "Planting Date"
        layout.addView(editText1)

        val editText2 = EditText(this)
        editText2.hint = "Accumlated GDD"
        layout.addView(editText2)

        val editText3 = EditText(this)
        editText3.hint = "Effective root zone"
        layout.addView(editText3)

        builder.setView(layout)

        // Set up the submit button
        builder.setPositiveButton("Submit") { dialog, which ->
            // Retrieve text from EditText fields
            val pd = editText1.text.toString()
            val gdd = editText2.text.toString()
            val erz = editText3.text.toString()
            if (pd.isNotEmpty())ref.child("plantdate").setValue(pd)
            if (gdd.isNotEmpty())ref.child("gdd").setValue(gdd)
            if (erz.isNotEmpty())ref.child("erz").setValue(erz)

        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun hook() {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database
        farmname = findViewById(R.id.farmname)
        farmsize = findViewById(R.id.farmsize)
        farmimage = findViewById(R.id.farmimage)
        plant_date = findViewById(R.id.plant_date)
        gdd = findViewById(R.id.gdd)
        erzz = findViewById(R.id.erz)
        setup = findViewById(R.id.setupdata)

    }
}