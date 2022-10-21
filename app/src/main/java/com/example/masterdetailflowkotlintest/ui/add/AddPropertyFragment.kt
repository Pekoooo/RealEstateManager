package com.example.masterdetailflowkotlintest.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.masterdetailflowkotlintest.R
import com.example.masterdetailflowkotlintest.databinding.FragmentAddPropertyBinding
import com.example.masterdetailflowkotlintest.model.pojo.Photo
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.ui.detail.PropertyDetailFragment
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.Constants.ARG_NO_ITEM_ID
import com.example.masterdetailflowkotlintest.utils.DefineScreenSize.Companion.isTablet
import com.example.masterdetailflowkotlintest.utils.DeviceSize
import com.example.masterdetailflowkotlintest.utils.UriPathHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.hasPermissions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddPropertyFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: AddPropertyViewModel by viewModels()
    private val housingType: MutableList<String> = ArrayList()
    private lateinit var currentPhotoPath: String
    private lateinit var deviceSize: DeviceSize
    private var _binding: FragmentAddPropertyBinding? = null
    private val binding: FragmentAddPropertyBinding get() = _binding!!
    private var allPropertyPictures: MutableList<Photo> = mutableListOf()
    private var currentProperty: Property? = null
    private var uriImageSelected: Uri? = null
    private val args: AddPropertyFragmentArgs by navArgs()
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
        private const val REQUEST_CODE_PERMISSIONS_CAMERA = 10
        private const val REQUEST_CODE_PERMISSIONS_STORAGE = 20
        private const val RC_CHOOSE_PHOTO = 30
        private const val REQUEST_IMAGE_CAPTURE = 1

        fun newInstance(id: Int) = AddPropertyFragment().apply {
            arguments = Bundle().apply {
                putInt("item_id", id)
            }
        }
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

        deviceSize = when(isTablet(requireContext())){
            true -> DeviceSize.TABLET
            false -> DeviceSize.PHONE
        }

        populateHousingTypeList()
        setUpSpinner()
        createToolbar()

        when (areArgsForUpdate()) {

            true -> {
                (activity as MainActivity).supportActionBar?.title = "Update Property"

                val propertyId: Int? = if (isTablet(requireContext())){
                    arguments?.getInt("item_id")
                } else {
                    args.navigationArgument
                }

                propertyId?.let { retrieveData(it) }

            }
            false -> (activity as MainActivity).supportActionBar?.title = "New Property"
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

                    capturePhoto()

                } else {

                    requestCameraPermission()

                }

            }

            storageButton.setOnClickListener {

                if (hasPermissions(requireContext(), *storagePerms)) {

                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, RC_CHOOSE_PHOTO)

                } else {
                    requestStoragePermission()
                }
                builder.dismiss()
            }

        }


    }


    private fun capturePhoto() {
        val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File = createImageFile()
        uriImageSelected = FileProvider.getUriForFile(
            requireContext(),
            "com.example.masterdetailflowkotlintest.fileprovider",
            photoFile
        )
        cameraInt.putExtra(MediaStore.EXTRA_OUTPUT, uriImageSelected)
        startActivityForResult(cameraInt, REQUEST_IMAGE_CAPTURE)

    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with CAMERA
            currentPhotoPath = absolutePath
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleResponse(requestCode, resultCode, data)
    }

    private fun handleResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {

                Log.d(TAG, "handleResponse: $currentPhotoPath")

                val currentPhoto = Photo(
                    currentPhotoPath,
                    false
                )
                allPropertyPictures.add(currentPhoto)

                setRecyclerView(binding.recyclerView)

                openDialog(currentPhoto)

            }
        } else if (requestCode == RC_CHOOSE_PHOTO) {

            uriImageSelected = data!!.data
            val uriPathHelper = UriPathHelper()
            val path = uriImageSelected?.let { uriPathHelper.getPath(requireContext(), it) }

            Log.d(TAG, "handleResponse: $path")

            val currentPhoto = Photo(
                path!!,
                false
            )
            allPropertyPictures.add(currentPhoto)

            setRecyclerView(binding.recyclerView)

            openDialog(currentPhoto)

        }
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = AddPropertyAdapter(allPropertyPictures) { photo, _ ->

            openDialog(photo)

        }
    }

    private fun openDialog(photo: Photo?) {

        val builder = AlertDialog.Builder(context)
            .create()
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.info_picture_dialog, null)

        Glide
            .with(requireContext())
            .load(photo?.path)
            .override(1200, 1200)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(dialogLayout.findViewById(R.id.image_view_custom_dialog_edit_picture))

        val saveButton = dialogLayout.findViewById<Button>(R.id.save_button)
        val deleteButton = dialogLayout.findViewById<Button>(R.id.delete_button)
        val descriptionTextView =
            dialogLayout.findViewById<EditText>(R.id.description_picture_edit_text)
        val switch = dialogLayout.findViewById<SwitchCompat>(R.id.make_main_picture_switch)

        builder.setView(dialogLayout)
        builder.show()

        if (currentProperty?.mainPicture == photo?.path) switch.toggle()


        if (photo?.description != null) (descriptionTextView as TextView).text =
            photo.description.toString()


        saveButton.setOnClickListener {
            if (currentProperty == null) currentProperty = getPropertyInfo()

            if (switch.isChecked) changeMainPhoto(photo)

            photo?.description = descriptionTextView.text.toString()
            updateDescription(photo)
            setRecyclerView(binding.recyclerView)
            builder.dismiss()
        }

        deleteButton.setOnClickListener {

            currentProperty?.pictureList?.remove(photo)
            setRecyclerView(binding.recyclerView)
            builder.dismiss()

        }

    }

    private fun changeMainPhoto(currentPhoto: Photo?) {
        currentProperty?.mainPicture = currentPhoto?.path.toString()
    }


    private fun updateDescription(currentPhoto: Photo?) {
        currentProperty?.pictureList!!.filter { it.path == currentPhoto?.path }.forEach {
            it.description = currentPhoto?.description
        }
    }

    private fun areArgsForUpdate(): Boolean =
        when(deviceSize){
            DeviceSize.TABLET -> arguments?.getInt("item_id") != ARG_NO_ITEM_ID
            DeviceSize.PHONE -> args.navigationArgument != ARG_NO_ITEM_ID
        }
        /*args.navigationArgument != ARG_NO_ITEM_ID || arguments?.getInt("item_id") != ARG_NO_ITEM_ID*/

    private fun retrieveData(id: Int) {
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

        setRecyclerView(binding.recyclerView)

        displayPoi(property)

    }

    private fun displayPoi(property: Property) {

        if (property.poiList.contains(resources.getString(R.string.school))) {
            binding.checkbox?.checkboxNearbySchool?.isChecked = true
        }

        if (property.poiList.contains(resources.getString(R.string.playground))) {
            binding.checkbox?.checkboxNearbyPlayground?.isChecked = true
        }

        if (property.poiList.contains(resources.getString(R.string.shops))) {
            binding.checkbox?.checkboxNearbyShop?.isChecked = true
        }

        if (property.poiList.contains(resources.getString(R.string.train))) {
            binding.checkbox?.checkboxNearbyTrain?.isChecked = true
        }

        if (property.poiList.contains(resources.getString(R.string.daycare))) {
            binding.checkbox?.checkboxNearbyDaycare?.isChecked = true
        }

        if (property.poiList.contains(resources.getString(R.string.parking))) {
            binding.checkbox?.checkboxNearbyParking?.isChecked = true
        }

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

                        if (areArgsForUpdate()) {

                            when(args.navigationArgument){

                                -1 -> {
                                    Toast.makeText(context, "Property Updated", Toast.LENGTH_LONG).show()
                                    val id = arguments?.getInt("item_id")
                                    id?.let { viewModel.updateProperty(getPropertyInfo().copy(id = id)) }

                                    requireActivity().supportFragmentManager.commit {

                                        replace(
                                            R.id.item_detail_frame_layout,
                                            PropertyDetailFragment.newInstance(currentProperty!!.id)
                                        )
                                    }
                                }

                                else -> {
                                    Toast.makeText(context, "Property Updated", Toast.LENGTH_LONG).show()
                                    viewModel.updateProperty(getPropertyInfo().copy(id = args.navigationArgument))
                                    findNavController().navigateUp()
                                }
                            }

                        } else {
                            when(deviceSize){
                                DeviceSize.TABLET -> {
                                    Toast.makeText(context, "New property saved", Toast.LENGTH_LONG).show()
                                    viewModel.createProperty(getPropertyInfo())
                                }

                                DeviceSize.PHONE -> {
                                    Toast.makeText(context, "New property saved", Toast.LENGTH_LONG).show()
                                    viewModel.createProperty(getPropertyInfo())
                                    findNavController().navigateUp()
                                }
                            }

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
                binding.priceEditText.text.toString() != "" &&
                currentProperty?.mainPicture != "" &&
                currentProperty?.pictureList?.size != 0

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
        currentProperty?.mainPicture.toString(),
        allPropertyPictures,
        getPoi(),
    )

    private fun getPoi(): MutableList<String> {

        val poiList = mutableListOf<String>()

        if (binding.checkbox?.checkboxNearbySchool?.isChecked == true) {
            poiList.add(resources.getString(R.string.school))
        }

        if (binding.checkbox?.checkboxNearbyPlayground?.isChecked == true) {
            poiList.add(resources.getString(R.string.playground))
        }

        if (binding.checkbox?.checkboxNearbyShop?.isChecked == true) {
            poiList.add(resources.getString(R.string.shop))
        }

        if (binding.checkbox?.checkboxNearbyTrain?.isChecked == true) {
            poiList.add(resources.getString(R.string.train))
        }

        if (binding.checkbox?.checkboxNearbyDaycare?.isChecked == true) {
            poiList.add(resources.getString(R.string.daycare))
        }

        if (binding.checkbox?.checkboxNearbyParking?.isChecked == true) {
            poiList.add(resources.getString(R.string.parking))
        }

        return poiList

    }

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

            //findNavController().navigate(R.id.cameraSurfaceProviderFragment)

        } else {
            TODO("Open storage")

        }
    }

    private fun requestCameraPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_camera_and_storage),
            REQUEST_CODE_PERMISSIONS_CAMERA,
            *cameraPerms
        )
    }

    private fun requestStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_camera_and_storage),
            REQUEST_CODE_PERMISSIONS_STORAGE,
            *storagePerms
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
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