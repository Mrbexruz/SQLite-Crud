package com.example.sqlitecrud

import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqlitecrud.adapter.RvAction
import com.example.sqlitecrud.adapter.RvAdapter
import com.example.sqlitecrud.databinding.ActivityMainBinding
import com.example.sqlitecrud.databinding.CustomDialogBinding
import com.example.sqlitecrud.db.MyDbHelper
import com.example.sqlitecrud.models.User

class MainActivity : AppCompatActivity(), RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var rvAdapter: RvAdapter
    lateinit var myDbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
            myDbHelper = MyDbHelper(this)

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)

            customDialogBinding.btnSave.setOnClickListener {

                val user = User(customDialogBinding.edtName.text.toString(),customDialogBinding.edtNumber.text.toString(),1)
                myDbHelper.addUsers(user)
                onResume()
            }
            dialog.setView(customDialogBinding.root)
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val list = myDbHelper.showUsers()
        rvAdapter = RvAdapter(list as ArrayList<User>, this)
        binding.rv.adapter = rvAdapter

    }

    override fun moreClick(user: User, imagerView: ImageView) {
        val popupMenu= PopupMenu(this, imagerView)
        popupMenu.inflate(R.menu.my_menu)

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_edit -> {
                    val dialog = AlertDialog.Builder(this)
                    val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
                    dialog.setView(customDialogBinding.root)

                    customDialogBinding.edtName.setText(user.name)
                    customDialogBinding.edtNumber.setText(user.number)

                    customDialogBinding.btnSave.setOnClickListener {
                        user.name = customDialogBinding.edtName.text.toString()
                        user.number = customDialogBinding.edtNumber.text.toString()
                        myDbHelper.editUser(user)
                        onResume()
                    }
                    

                    dialog.show()

                }


                R.id.menu_delete -> {
                    myDbHelper.deleteUser(user)
                    onResume()
                }
            }

            true
        }
        popupMenu.show()

    }
}


