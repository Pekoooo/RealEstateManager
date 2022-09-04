package com.example.masterdetailflowkotlintest.ui.addProperty

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentAddPropertyBinding
import com.example.masterdetailflowkotlintest.model.Photo
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.hasPermissions
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: AddPropertyViewModel by viewModels()
    private val housingType: MutableList<String> = ArrayList()
    private var _binding: FragmentAddPropertyBinding? = null
    private val binding: FragmentAddPropertyBinding get() = _binding!!
    private var currentId: Int? = null
    private var allPropertyPictures: MutableList<Photo> = mutableListOf()
    private var currentProperty: Property? = null

    private val cameraPerms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val storagePerms = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    companion object {
        const val TAG = "MyAddPropertyFragment"
        const val ARG_ITEM_ID = "item_id"
        private const val REQUEST_CODE_PERMISSIONS_CAMERA = 10
        private const val REQUEST_CODE_PERMISSIONS_STORAGE = 20
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.show()


        populateHousingTypeList()
        setUpSpinner()
        createToolbar()


        if (argsHaveId()) {
            (activity as MainActivity).supportActionBar?.title = "Update Property"
            currentId = requireArguments().getInt(ARG_ITEM_ID)
            retrieveData(currentId!!)

        } else {
            (activity as MainActivity).supportActionBar?.title = "New Property"
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Property>("property")
            ?.observe(viewLifecycleOwner) {
                Log.d(TAG, "livedata : ${it.pictureList.size}")
                displayData(it)
            }



        binding.addPictureButton.setOnClickListener {

            val builder = AlertDialog.Builder(context)
                .create()

            val customView = layoutInflater.inflate(R.layout.add_picture_dialog, null)
            val cameraButton = customView.findViewById<Button>(R.id.button_take_picture)
            val storageButton = customView.findViewById<Button>(R.id.button_open_internal_storage)

            builder.setView(customView)

            builder.show()

            cameraButton.setOnClickListener {

                if (hasPermissions(requireContext(), *cameraPerms)) {

                    val action = if (currentProperty == null) {
                        AddPropertyFragmentDirections.actionAddPropertyFragmentToCameraSurfaceProviderFragment(
                            getPropertyInfo()
                        )
                    } else {
                        AddPropertyFragmentDirections.actionAddPropertyFragmentToCameraSurfaceProviderFragment(
                            currentProperty
                        )
                    }

                    findNavController().navigate(action)

                } else if (arguments == null) {

                    findNavController().navigate(R.id.cameraSurfaceProviderFragment)


                } else {
                    requestCameraPermission()
                }
                builder.dismiss()
            }
            storageButton.setOnClickListener {

                if (hasPermissions(requireContext(), *storagePerms)) {
                    TODO("Open storage")
                } else {
                    requestStoragePermission()
                }
                builder.dismiss()
            }
        }
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        Log.d(TAG, "setRecyclerView: ${allPropertyPictures.size} ")
        recyclerView.adapter = AddPropertyAdapter(allPropertyPictures) {

            val currentPhoto = it

            val builder = AlertDialog.Builder(context)
                .create()
            val inflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.info_picture_dialog, null)

            Glide
                .with(requireContext())
                .load(it?.path)
                .override(1200,1200)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(dialogLayout.findViewById(R.id.image_view_custom_dialog_edit_picture))

            val saveButton = dialogLayout.findViewById<Button>(R.id.save_button)
            val deleteButton = dialogLayout.findViewById<Button>(R.id.delete_button)
            val descriptionTextView = dialogLayout.findViewById<EditText>(R.id.description_picture_edit_text)
            val switch = dialogLayout.findViewById<SwitchCompat>(R.id.make_main_picture_switch)

            builder.setView(dialogLayout)
            builder.show()

            if(currentPhoto!!.isMain){
                switch.toggle()
            }

            saveButton.setOnClickListener {

                if(switch.isChecked) {
                    currentPhoto.isMain = true
                    changeMainPhoto(currentPhoto)
                }

                currentPhoto.description = descriptionTextView.toString()
                updatePicture(currentPhoto)
            }

            deleteButton.setOnClickListener {

                currentProperty?.pictureList?.remove(currentPhoto)

            }

        }

    }

    private fun changeMainPhoto(currentPhoto: Photo) {
        Log.d(TAG, "changeMainPhoto: ${currentProperty?.pictureList?.size}")
        for (photo in currentProperty?.pictureList!!) {
            if(photo.isMain){
                photo.isMain = false
            }
        }
    }

    private fun updatePicture(currentPhoto: Photo?) {

        val list = currentProperty?.pictureList!!

        for (photo in list) {

            if (photo.path == currentPhoto?.path) {
                list.remove(photo)
                list.add(currentPhoto)
                break
            }

        }

    }




    private fun argsHaveId(): Boolean =
        arguments?.containsKey(ARG_ITEM_ID) == true && findNavController().currentBackStackEntry?.savedStateHandle?.contains(
            "property"
        ) == false

    private fun argsHaveIdAndKey(): Boolean =
        arguments?.containsKey(ARG_ITEM_ID) == true && findNavController().currentBackStackEntry?.savedStateHandle?.contains(
            "property"
        ) == true

    private fun retrieveData(id: Int) {
        Log.d(TAG, "retrieveData: is called")
        lifecycle.coroutineScope.launch {
            viewModel.getPropertyById(id).collect {
                displayData(it)
                currentProperty = it

            }
        }
    }

    private fun displayData(property: Property) {

        (binding.spinnerEditText as TextView).text = property.type // does not work
        (binding.agentNameEditText as TextView).text = property.agent
        (binding.propertyDescriptionEditText as TextView).text = property.description
        (binding.surfaceEditText as TextView).text = property.surface
        (binding.addressEditText as TextView).text = property.address
        (binding.roomsEditText as TextView).text = property.rooms
        (binding.cityEditText as TextView).text = property.city
        (binding.bedroomEditText as TextView).text = property.bedrooms
        (binding.postalCodeEditText as TextView).text = property.postalCode
        (binding.bathroomEditText as TextView).text = property.bathrooms
        (binding.countryEditText as TextView).text = property.country
        (binding.neighborhoodEditText as TextView).text = property.neighborhood
        (binding.priceEditText as TextView).text = property.price
        allPropertyPictures = property.pictureList

        currentProperty = property

        setRecyclerView(binding.recyclerView)

        //Todo : Add POIs
    }

    private fun createToolbar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_add_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {

                R.id.save -> {
                    if (allFieldsAreFilled()) {
                        if (argsHaveIdAndKey() || argsHaveId()) {
                            Toast.makeText(context, "Property Updated", Toast.LENGTH_LONG).show()

                            viewModel.updateProperty(getPropertyInfo().copy(id = currentId!!))

                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(context, "New property saved", Toast.LENGTH_LONG).show()
                            viewModel.createProperty(getPropertyInfo())
                            findNavController().navigateUp()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Make sure all fields are filled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner)
    }

    private fun allFieldsAreFilled(): Boolean =
        binding.agentNameEditText.text.toString() != "" &&
                binding.propertyDescriptionEditText.text.toString() != "" &&
                binding.surfaceEditText.text.toString() != "" &&
                binding.addressEditText.text.toString() != "" &&
                binding.roomsEditText.text.toString() != "" &&
                binding.cityEditText.text.toString() != "" &&
                binding.bedroomEditText.text.toString() != "" &&
                binding.postalCodeEditText.text.toString() != "" &&
                binding.bathroomEditText.text.toString() != "" &&
                binding.countryEditText.text.toString() != "" &&
                binding.neighborhoodEditText.text.toString() != "" &&
                binding.priceEditText.text.toString() != ""


    private fun getPropertyInfo() = Property(
        0,
        binding.surfaceEditText.text.toString(),
        binding.spinner.selectedItem.toString(),
        binding.addressEditText.text.toString(),
        binding.cityEditText.text.toString(),
        binding.neighborhoodEditText.text.toString(),
        binding.postalCodeEditText.text.toString(),
        binding.countryEditText.text.toString(),
        binding.priceEditText.text.toString(),
        binding.bathroomEditText.text.toString(),
        binding.bedroomEditText.text.toString(),
        Calendar.getInstance().time.toString(),
        binding.roomsEditText.text.toString(),
        binding.propertyDescriptionEditText.text.toString(),
        binding.agentNameEditText.text.toString(),
        "main picture url",
        allPropertyPictures


    )

    private fun populateHousingTypeList() {
        housingType.add("House")
        housingType.add("Penthouse")
        housingType.add("Flat")
        housingType.add("Mansion")
    }

    private fun setUpSpinner() {
        val spinner = binding.spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            housingType
        )

        spinner.adapter = adapter

        binding.spinnerEditText.setOnClickListener {
            binding.spinner.performClick()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                (binding.spinnerEditText as TextView).text = binding.spinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //TODO : Not working
        if (requestCode == REQUEST_CODE_PERMISSIONS_CAMERA) {

            findNavController().navigate(R.id.cameraSurfaceProviderFragment)

        } else {
            TODO("Open storage")

        }
    }

    private fun requestCameraPermission() {
        Log.d(TAG, "requestCameraPermission: is called")
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_camera_and_storage),
            REQUEST_CODE_PERMISSIONS_CAMERA,
            *cameraPerms
        )
    }

    private fun requestStoragePermission() {
        Log.d(TAG, "requestStoragePermission: is called")
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_camera_and_storage),
            REQUEST_CODE_PERMISSIONS_STORAGE,
            *storagePerms

        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied: is called")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            if (perms[0] == Manifest.permission.CAMERA) {
                requestCameraPermission()
            } else {
                requestStoragePermission()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}